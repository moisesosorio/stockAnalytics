package com.stock.channels

import com.stock.apis.apisRestProcess
import com.typesafe.config.Config
import org.apache.spark.sql.DataFrame

class youtubeMethods {

  val apiRest = new apisRestProcess
  var dfOutput: DataFrame = _
  var dataYoutube : String = ""
  val token = "AIzaSyCkraRNEMz5tcFAKGNSLzMJ-5RluRt-4zU"

  def getDataYoutube(url: String, token: String): String = {
    dataYoutube = apiRest.getRestContentWithToken(url, token)
    dataYoutube
  }

  def defGenerateUrl(config: Config, method: String): String ={
    val str_output = config.getString("url") + config.getString(method)
    str_output
  }

  def defGetActivities(config: Config): DataFrame ={
    val url = defGenerateUrl(config, "getActivities")
    dataYoutube = getDataYoutube(url, token)
    println(dataYoutube)

    dfOutput
  }


  def youtubeProcess(config: Config): Unit ={
     val df = defGetActivities(config)
  }

}
