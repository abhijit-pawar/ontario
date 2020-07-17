package com.ontario.model

import com.ontario.Model.LogModel.DatasetWithLog
import org.apache.spark.sql.Dataset

trait DataStage[T <: Dataset[_]] extends Serializable {
  def apply(data:T): DatasetWithLog[T]
  def stage:String

}
