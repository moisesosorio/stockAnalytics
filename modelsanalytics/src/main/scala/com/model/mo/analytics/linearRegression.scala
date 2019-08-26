package com.model.mo.analytics

import co.theasi.plotly._
import com.typesafe.config.Config
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

class linearRegression {

  def lr_housing(spark: SparkSession, inputConfig: Config, df_input: DataFrame): Unit = {

    val feature_columns = df_input.drop("medv").columns

    val assembler = new VectorAssembler()
      .setInputCols(feature_columns)
      .setOutputCol("features")
    val output = assembler.transform(df_input)

    //train / test split
    //train 70% of data
    //test 30% of data
    val dataSplits = output.randomSplit(Array(0.7, 0.3))
    val training = dataSplits(0)
    val test = dataSplits(1)

    //training the machine learning algorithm
    val algo = new LinearRegression()
      .setFeaturesCol("features")
      .setLabelCol("medv")

    val model = algo.fit(training)

    //evaluating model performance
    val evaluation_sumary = model.evaluate(test)
    println(evaluation_sumary.meanAbsoluteError)
    println(evaluation_sumary.rootMeanSquaredError)
    println(evaluation_sumary.r2)

    //predicting values
    val prediction = model.transform(test)

    val df = prediction.select(col("medv"),
      col("features"),
      col("prediction"))
    val xs = (0 until 100)
    val ys_original = df.select(col("medv")).collect().map { row => row.toString() }
    val ys_prediction = df.select(col("prediction")).collect().map { row => row.toString() }
    //graph in plottly
    val plot1 = Plot().withScatter(xs, ys_original)
    val plot2 = Plot().withScatter(xs, ys_prediction)

    implicit val server = new writer.Server {
      val credentials = writer.Credentials("moises43", "f9PxxdYdHd3jsd1OlGzx")
      val url = "https://api.plot.ly/v2/"
    }

    draw(plot1, "basic-scatter1", writer.FileOptions(overwrite = true))
    draw(plot2, "basic-scatter2", writer.FileOptions(overwrite = true))

  }
}
