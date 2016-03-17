package com.constructiveproof.merkletreat.utils

import com.roundeights.hasher.Implicits._

/**
  * A single spot for hashification.
  */
object Hashify {

  /**
    * Does a SHA256 hash on incoming data.
    *
    * @param it the incoming string to hash
    * @return a SHA256 hex digest string
    */
  def apply(it: String): String = {
    it.sha256.hex.toString
  }

}
