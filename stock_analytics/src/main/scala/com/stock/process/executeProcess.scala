package com.stock.process

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.stock.spotify.spotifyMethods
import com.typesafe.config.Config

object executeProcess {

  var testConfig : Config = _
  var spotifyConfig : Config = _
  var yahooConfig: Config = _
  var youtubeConfig: Config = _

  var testState : String = ""
  var spotifyState : String = ""
  var yahooState : String = ""
  var youtube : String = ""

  var dfTestData: DataFrame = _

  def defGetParams(inputConfig: Config): Unit = {
    testConfig = inputConfig.getConfig("analytics.input.test")
    spotifyConfig = inputConfig.getConfig("analytics.input.spotify")

    spotifyState = spotifyConfig.getString("state")
    testState = testConfig.getString("state")

  }

  def executeProcessSpotify(spark: SparkSession): Unit = {
    val spotifyClass = new spotifyMethods
    val df_spotify = spotifyClass.spotifyProcess(spotifyConfig)

  }

  def executeProcessTest(spark: SparkSession): Unit ={
    val
  }


  def defExecuteProcess(spark: SparkSession, inputConfig: Config): Unit = {

    defGetParams(inputConfig)

    if (spotifyState == "active"){
      executeProcessSpotify(spark)
    }
    if (testState == "active"){

    }


  }


}
