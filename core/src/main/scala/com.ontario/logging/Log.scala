package com.ontario.logging

import com.ontario.Model.LogModel.LogMessage

object Log {

  private var logMessage:Vector[LogMessage] = Vector[LogMessage]()

  def addLogToList(log: LogMessage): Vector[LogMessage] = {
    logMessage = logMessage :+ log
    logMessage
  }


  def getLog:Vector[LogMessage] = {
    logMessage
  }
}
