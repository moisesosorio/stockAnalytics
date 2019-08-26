package com.model.mo.utils
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object utiles {

  def readInput(input : String, spark : SparkSession): DataFrame = {
    val df = spark.read.format("csv")
      .option("header", "true")
      .option("delimiter", ",")
      .option("inferSchema", "true")
      .load(input)
    df
  }
  def cleanData(df : DataFrame) : DataFrame = {
    var dfout = df

    val stringCols = dfout.dtypes.map(
      f => {
        if (f._2 == "StringType") f._1 else ' '
      }).filter(_ != ' ').toList

    for {c <- stringCols}{
      val colName = c.toString
      dfout = dfout.withColumn(colName, trim(col(colName)))
    }
    dfout
  }
}
