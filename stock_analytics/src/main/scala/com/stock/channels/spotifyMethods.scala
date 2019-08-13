package com.stock.channels

import org.apache.spark.sql.DataFrame
import com.typesafe.config.Config
import com.stock.apis.apisRestProcess

class spotifyMethods {

  val apiRest = new apisRestProcess
  var dfOutput: DataFrame = _
  var dataSpotify : String = ""
  //val token = "BQCnyKxHS8hvYR-QUfVsDtkL-vY-O0-KhH3iHRcXp8YD4RVFZ8evb4iky4eHfepmm1pD-APWCjQ7RdG3wugmgmC9OfJ_XX1ojYdB71hX5lg2OpAu8FJIwJTlNg6eSDLlwJc2oDQzt2TfmGM7_AYRX7mGUl4I3vBqJ5K9xqY5zfXh7R8ADRmCJI_WKZZTvfa9dq5DXtp7Wd3CKZCXFD3Qkkzn1GGET2Re0YaYXR3TrlXS_4aU-SoXoP7B7W2PqMOj8otmVtyZtMyTFD1GKR5PE3Sb7xe4fKOrCAEWhVw"

  //cliendID = bbc015f3c2294031b2aa548f7db177ea


  def getDataSpotify(url: String, config: Config): String = {
    dataSpotify = apiRest.getRestContentWithToken(url, config)
    dataSpotify
  }

  def defGenerateUrl(config: Config, method: String): String ={
    val str_output = config.getString("url") + config.getString(method)
    str_output
  }

  def defGetPlaylist(config: Config): DataFrame ={
    val url = defGenerateUrl(config, "getPlayList")
    dataSpotify = getDataSpotify(url, config)
    dfOutput
  }


  def spotifyProcess(config: Config): Unit ={
     var df = defGetPlaylist(config)
  }

}
