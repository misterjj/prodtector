package com.prodtector.server.config

import com.typesafe.config.Config

case class MainConfig(configPath: String)

object MainConfig {
  def from(config: Config): MainConfig = {
    MainConfig(
      configPath = config.getString("config-path")
    )
  }
}
