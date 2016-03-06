package com.constructiveproof.merkletreat


import com.roundeights.hasher.Implicits._

case class Leaf(item: String) {

  def add(item: String) = {

  }

  def identity = {
    ("L" + item).sha256.hex.toString
  }

}
