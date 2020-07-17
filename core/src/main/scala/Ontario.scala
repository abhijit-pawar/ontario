package com.ontario

import com.ontario.pipeline.Pipeline

object Ontario
{

  def main(args: Array[String]): Unit = {
    System.out.println("Hello Ontario")

    val pipeline = new Pipeline()
    pipeline.execute()
  }
}