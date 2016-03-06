package com.constructiveproof.merkletreat


import com.roundeights.hasher.Implicits._

case class Leaf(item: String) {

  val identity = ("L" + item).sha256.hex.toString

  def add(item: String) = {

  }

  def isIn(thing: String): Boolean = {
    item == thing
  }

}
