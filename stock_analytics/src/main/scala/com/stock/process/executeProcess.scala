package com.stock.process

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.stock.channels.{spotifyMethods, youtubeMethods}
import com.typesafe.config.Config

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
    spotifyConfig = inputConfig.getConfig("analytics.input.spotify")
    youtubeConfig = inputConfig.getConfig("analytics.input.youtube")

    testState = testConfig.getString("state")
    spotifyState = spotifyConfig.getString("state")
    youtubeState = youtubeConfig.getString("state")


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


}
