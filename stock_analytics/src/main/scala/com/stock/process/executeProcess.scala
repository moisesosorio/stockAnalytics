package com.stock.process

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.stock.channels.{spotifyMethods, youtubeMethods}
import com.typesafe.config.Config
import com.stock.parameters.{paramSpotify => prSpo}
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegression
import co.theasi.plotly._

object executeProcess {

  var testConfig : Config = _
  var spotifyConfig : Config = _
  var yahooConfig: Config = _
  var youtubeConfig: Config = _

  var testState : String = ""
  var spotifyState : String = ""
  var yahooState : String = ""
  var youtubeState : String = ""

  var dfTestData: DataFrame = _

  def defGetParams(inputConfig: Config): Unit = {
    testConfig = inputConfig.getConfig("analytics.input.test")
    //spotifyConfig = inputConfig.getConfig("analytics.input.spotify")
    youtubeConfig = inputConfig.getConfig("analytics.input.youtube")

    testState = testConfig.getString("state")
    //spotifyState = spotifyConfig.getString("state")
    youtubeState = youtubeConfig.getString("state")

    spotifyState = prSpo.state

  }

  def executeProcessSpotify(spark: SparkSession): Unit = {
    val spotifyClass = new spotifyMethods
    spotifyClass.spotifyProcess(spotifyConfig)

  }

  def executeProcessYoutube(spark: SparkSession): Unit ={
    val youtubeClass = new youtubeMethods
    youtubeClass.youtubeProcess(youtubeConfig)

  }


  def defExecuteProcess(spark: SparkSession, inputConfig: Config): Unit = {

    defGetParams(inputConfig)

    if (spotifyState == "active"){
      executeProcessSpotify(spark)
    }
    if (youtubeState == "active"){
      executeProcessYoutube(spark)
    }


  }

  def alicorp(spark: SparkSession, inputConfig: Config): Unit = {

    val input = inputConfig.getConfig("analytics.alicorp")
    var df1 = readInput(input.getString("path2"), spark)
        df1 = cleanData(df1)

    //setting up the features
    //features = is an array of data points of all the features to be used for prediction
    //labels = the output label for each data point
    val feature_columns = df1.drop("medv").columns

    val assembler = new VectorAssembler()
      .setInputCols(feature_columns)
      .setOutputCol("features")
    val output = assembler.transform(df1)

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

    import util.Random
    val xs = (0 until 100)
    val ys_original = df.select(col("medv")).collect().map{ row => row.toString()}
    val ys_prediction = df.select(col("prediction")).collect().map{ row => row.toString()}
    //graph in plottly
    val plot1 = Plot().withScatter(xs, ys_original)
    val plot2 = Plot().withScatter(xs, ys_prediction)

    implicit val server = new writer.Server {
      val credentials = writer.Credentials("moises43", "bHIOuA9dXWbY5HMvmXAG")
      val url = "https://api.plot.ly/v2/"
    }
    draw(plot1, "basic-scatter1", writer.FileOptions(overwrite=true))
    draw(plot2, "basic-scatter2", writer.FileOptions(overwrite=true))





  }
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
