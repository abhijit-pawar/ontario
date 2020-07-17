package com.ontario.pipeline


import cats.data.Writer
import com.ontario.Model.LogModel.LogMessage
import com.ontario.logging.Log
import com.ontario.model.DataFrameOperations
import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import com.ontario.yelp._

class Pipeline {


  def log(implicit stage:String, log:String, severity:String): Unit = {
    Log.addLogToList(LogMessage(stage= stage,log = log, severity = severity))
    println(log)
  }

  def execute(): Unit = {

    val sparkConf = new SparkConf().set("spark.app.name","Ontario Data Management for Yelp")

    implicit val sparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
    implicit val sc = sparkSession.sparkContext
    implicit val sqlContext = sparkSession.sqlContext

    val schema = StructType(StructField("LogMessage",StringType,true) :: Nil)

    val logRDD = sc.emptyRDD[Row]

    val sourceDF = sparkSession.createDataFrame(logRDD,schema)

    import com.ontario.model.DataFrameOperations._

    val initialDF = Writer(DataFrameOperations.baseLogDataFrame(sparkSession),sourceDF)

    val pipelineStage = List(new YelpReader())

    val output = pipelineStage.foldLeft(initialDF) {
      case (dfWithLog, stage) =>
        for {
          df <- dfWithLog
          result <- stage(df)
        } yield result
    }


  val (log, result) = output.run

  sparkSession.stop()
  }

}
