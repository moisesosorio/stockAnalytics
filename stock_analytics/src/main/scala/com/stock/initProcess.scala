package com.stock

import org.apache.spark.sql.SparkSession
import com.stock.process.executeProcess
import com.typesafe.config.ConfigFactory

protected trait stockAnalyticsJobTrait {
  def main(args: Array[String]): Unit = {

    //variable para guardar el archivo reference
    val reference = args(0).toString
    val globalConfig = ConfigFactory.load(reference)

    //variables de ejecucion en cluster
    val spark = SparkSession
      .builder()
      .appName("stocAnalytics")
      .config("spark.sql.shuffle.partitions", "4")
      .master("local[*]")
      .getOrCreate()

    executeProcess.defExecuteProcess(spark, globalConfig)

  }
}

object stockAnalyticsJob extends  stockAnalyticsJobTrait
