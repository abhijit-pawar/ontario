package Model

import Model.LogModel.{DatasetWithLog, LogMessage}
import cats.kernel.Semigroup
import org.apache.spark.sql.{Dataset, Encoder, Encoders}

object DataFrameOperations {

  implicit def dataFrameSemigroup[A:Encoder]: Semigroup[Dataset[A]] = new Semigroup[Dataset[A]] {
    override def combine(x: Dataset[A], y: Dataset[A]): Dataset[A] = x.union(y)
  }

  implicit val errorEncoder = Encoders.product[LogMessage]

}
