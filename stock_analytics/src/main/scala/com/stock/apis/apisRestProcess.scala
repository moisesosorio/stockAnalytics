package com.stock.apis

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import scala.io.Source.fromInputStream

class apisRestProcess {

  def getRestContentWithoutToken(url:String): String = {
    val httpClient = HttpClientBuilder.create.build
    val httpResponse = httpClient.execute(new HttpGet(url))
    val entity = httpResponse.getEntity
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent
      content = fromInputStream(inputStream).getLines.mkString
      inputStream.close()
    }
    content
  }

  def getRestContentWithToken(url:String, token: String): String = {
    val httpClient = HttpClientBuilder.create.build
    val httpGet = new HttpGet(url)
    httpGet.setHeader("Authorization", "Bearer " + token)
    val httpResponse = httpClient.execute(httpGet)
    val entity = httpResponse.getEntity
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent
      content = fromInputStream(inputStream).getLines.mkString
      inputStream.close()
    }
    content
  }

  def postRestContent(): Unit ={

  }

}
