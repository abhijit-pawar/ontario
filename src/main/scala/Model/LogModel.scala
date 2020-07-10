package Model

import java.text.SimpleDateFormat
import java.util.Date

import cats.data.Writer
import org.apache.spark.sql.{Dataset, Row}


object LogModel {

  val dateFormat = "yyyy-MM-dd HH:mm:ss.SSS"
  val sdf = new SimpleDateFormat(dateFormat)

  case class LogMessage(timstamp:String = sdf.format(new Date()), stage:String, log:String, severity:String)

  object logMessage {
    def apply(row:Row): LogMessage = new LogMessage(
      row.getAs[String]("timestamp"),
      row.getAs[String]("stage"),
      row.getAs[String]("log"),
      row.getAs[String]("severity")
    )
  }

  type DatasetWithLog[A] = Writer[Dataset[LogMessage],A]
}
