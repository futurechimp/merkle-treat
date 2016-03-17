package com.constructiveproof.merkletreat.utils

import com.roundeights.hasher.Implicits._

/**
  * A single spot for hashification.
  */
object Hashify {

  def apply(it: String): String = {
    it.sha256.hex.toString
  }

}
