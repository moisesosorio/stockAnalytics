package com.stock.spotify

import org.apache.spark.sql.DataFrame
import com.typesafe.config.Config
import com.stock.apis.apisRestProcess

class spotifyMethods {

  val apiRest = new apisRestProcess
  var dfOutput: DataFrame = _
  var dataSpotify : String = ""
  val token = "BQCnyKxHS8hvYR-QUfVsDtkL-vY-O0-KhH3iHRcXp8YD4RVFZ8evb4iky4eHfepmm1pD-APWCjQ7RdG3wugmgmC9OfJ_XX1ojYdB71hX5lg2OpAu8FJIwJTlNg6eSDLlwJc2oDQzt2TfmGM7_AYRX7mGUl4I3vBqJ5K9xqY5zfXh7R8ADRmCJI_WKZZTvfa9dq5DXtp7Wd3CKZCXFD3Qkkzn1GGET2Re0YaYXR3TrlXS_4aU-SoXoP7B7W2PqMOj8otmVtyZtMyTFD1GKR5PE3Sb7xe4fKOrCAEWhVw"

  def getDataSpotify(url: String, token: String): String = {
    dataSpotify = apiRest.getRestContentWithToken(url, token)
    dataSpotify
  }

  def defGenerateUrl(config: Config, method: String): String ={
    val str_output = config.getString("url") + config.getString(method)
    str_output
  }

  def defGetPlaylist(config: Config): DataFrame ={
    val url = defGenerateUrl(config, "getPlayList")
    dataSpotify = getDataSpotify(url, token)
    dfOutput
  }


  def spotifyProcess(config: Config): Unit ={

  }

}
