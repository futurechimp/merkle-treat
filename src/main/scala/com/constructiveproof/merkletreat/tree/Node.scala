package com.constructiveproof.merkletreat.tree

import com.roundeights.hasher.Implicits._

sealed trait Node {
  val identity: String
}


case class Leaf(item: String) extends Node {

  val identity = ("L" + item).sha256.hex.toString

  def add(item: String) = {

  }

  def isIn(thing: String): Boolean = {
    item == thing
  }

}

case class Branch(pivot: String, leftBranchId: String, rightBranchId: String) extends Node {

  val identity = ("B" + pivot + leftBranchId + rightBranchId).sha256.hex.toString

}