package com.constructiveproof.merkletreat.tree

import com.roundeights.hasher.Implicits._

sealed trait Node {

}


case class Leaf(item: String) extends Node {

  val identity = ("L" + item).sha256.hex.toString

  def add(item: String) = {

  }

  def isIn(thing: String): Boolean = {
    item == thing
  }

}

case class Branch() extends Node