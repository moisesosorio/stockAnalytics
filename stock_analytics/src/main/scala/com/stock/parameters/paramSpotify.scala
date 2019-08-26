package com.stock.parameters

import com.typesafe.config.Config

object paramSpotify {
  var state : String = ""
  var url : String = ""
  var getAlbums : String = ""
  var getArtists : String = ""
  var getCategories : String = ""
  var getRecomendations : String = ""
  var getPlaylistFollows=""
  var getPlayList : String = ""
  var getSearch : String = ""
  var getUsers : String = ""
  var spotifyConfig : Config = _

  def parameter(config: Config): Unit = {
    spotifyConfig = config.getConfig("analytics.input.spotify")
    setParameter(spotifyConfig)
  }

  def setParameter(paramConfig : Config): Unit = {
    state = paramConfig.getString("state")
    url = paramConfig.getString("url")
    getAlbums = paramConfig.getString("getAlbums")
    getArtists = paramConfig.getString("getArtists")
    getCategories = paramConfig.getString("getCategories")
    getRecomendations = paramConfig.getString("getRecomendations")
    getPlaylistFollows = paramConfig.getString("getPlaylistFollows")
    getPlayList = paramConfig.getString("getPlayList")
    getSearch = paramConfig.getString("getSearch")
    getUsers = paramConfig.getString("getUsers")
  }
}
