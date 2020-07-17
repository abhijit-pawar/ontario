package com.ontario.model


import cats.kernel.Semigroup
import com.ontario.Model.LogModel.LogMessage
import org.apache.spark
import org.apache.spark.sql.{Dataset, Encoder, Encoders, SparkSession}

object DataFrameOperations {

  implicit def dataFrameSemigroup[A:Encoder]: Semigroup[Dataset[A]] = new Semigroup[Dataset[A]] {
    override def combine(x: Dataset[A], y: Dataset[A]): Dataset[A] = x.union(y)
  }

  implicit val errorEncoder = Encoders.product[LogMessage]

  val baseLogDataFrame = (spark:SparkSession) => {
    spark.emptyDataset[LogMessage]
  }

}
