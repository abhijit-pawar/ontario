package com.ontario.yelp

import cats.data.Writer
import com.ontario.Model.LogModel.{DatasetWithLog, LogMessage}
import com.ontario.logging.Log
import com.ontario.model.DataStage
import org.apache.spark.sql._

class YelpReader(implicit val ss: SparkSession) extends BaseStage with DataStage[DataFrame] {

  override def apply(data:DataFrame): DatasetWithLog[DataFrame] = executeStage(data)

  override def stage:String = getClass.getSimpleName


def executeStage(data: DataFrame) : DatasetWithLog[DataFrame] = {

  import ss.implicits._

  val logDF = Log.getLog.toDF.map(row=>LogMessage(row))
  Writer(logDF,data)
}
}
