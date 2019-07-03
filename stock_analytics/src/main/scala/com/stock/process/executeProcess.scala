package com.stock.process

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.stock.apis.apisRestProcess
import com.typesafe.config.Config

object executeProcess {

  var urlTest : String = ""
  var urlGoogle : String = ""
  var urlSpotify : String = ""
  var urlYahooFinance : String = ""
  var urlNetflix : String = ""

  var dfTestData: DataFrame = _

  def defGetParams(inputConfig: Config): Unit = {
    urlTest = inputConfig.getString("transactiondiykJob.input.urlapis.urltest")
  }

  def defGetInput(spark: SparkSession): Unit = {
    val apiRest = new apisRestProcess
    val testData = apiRest.getRestContent(urlTest)
    println(testData)

  }


  def defExecuteProcess(spark: SparkSession, inputConfig: Config): Unit = {
    defGetParams(inputConfig)
    defGetInput(spark)

  }


}
