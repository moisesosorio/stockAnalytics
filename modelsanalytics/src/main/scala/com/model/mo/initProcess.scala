package com.model.mo

import org.apache.spark.sql.SparkSession
import com.model.mo.process.executeProcess
import com.typesafe.config.ConfigFactory

protected trait modelsAnalyticsJobTrait {
  def main(args: Array[String]): Unit = {

    //variable para guardar el archivo reference
    val reference = args(0).toString
    val globalConfig = ConfigFactory.load(reference)

    //variables de ejecucion en cluster
    val spark = SparkSession
      .builder()
      .appName("analytics")
      .config("spark.sql.shuffle.partitions", "4")
      .master("local[*]")
      .getOrCreate()

    executeProcess.process(spark, globalConfig)

  }
}

object modelsAnalyticsJob extends  modelsAnalyticsJobTrait