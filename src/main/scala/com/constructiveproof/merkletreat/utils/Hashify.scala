package com.constructiveproof.merkletreat.utils

import com.roundeights.hasher.Implicits._

/**
  * Created by dave on 15/03/16.
  */
object Hashify {

  def apply(item: String): String = {
    item.sha256.hex.toString.substring(0, 4)
  }

}
