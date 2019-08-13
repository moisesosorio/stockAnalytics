package com.stock.apis

import com.typesafe.config.Config

class auth {

  val cliente_id = "kzdv9pk4nud24le8bvth8l82m"
  var redirect_uri = "http://lab.possan.se/thirtify/callback.html"

  def defGetLoginURLSpotify(config: Config): Unit ={

    val scope = ("user-read-private",
    "playlist-read-private",
    "playlist-modify-public",
    "playlist-modify-private",
    "user-library-read",
    "user-library-modify",
    "user-follow-read",
    "user-follow-modify")

    val strUrl = "https://accounts.spotify.com/authorize?client_id=" + cliente_id +
      "&redirect_uri=" + redirect_uri +
      "&scope=" + scope   +
      "&response_type=token"


  }


}
