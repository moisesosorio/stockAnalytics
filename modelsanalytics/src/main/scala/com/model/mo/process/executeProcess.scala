package com.model.mo.process

import co.theasi.plotly.writer
import com.model.mo.utils.utiles
import org.apache.spark.sql.{DataFrame, SparkSession}
import com.typesafe.config.Config
import com.model.mo.analytics.linearRegression


object executeProcess {

  var inputConfig : Config = _
  var df_housing : DataFrame = _

  def process(spark: SparkSession, config: Config): Unit = {

    inputConfig = config.getConfig("analytics.alicorp")

    //data quality
    df_housing = utiles.readInput(inputConfig.getString("path2"), spark)
    df_housing = utiles.cleanData(df_housing)

    //feature enginreering
    val model = new linearRegression
    model.lr_housing(spark, inputConfig, df_housing)
    //

  }
}
