package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.Storable
import com.roundeights.hasher.Implicits._

sealed trait Node {
  val identity: String

  def add(store: Storable, item: String): Unit

  def checkHash(key: String, value: Node) = {
    if (key != value.identity) {
      throw HashIdentityException()
    }
  }

  case class HashIdentityException(message: String = "Stored key doesn't match hash!") extends Throwable(message)

  def contains(thing: String): Boolean

}


case class Leaf(item: String) extends Node {

  val identity = ("L" + item).sha256.hex.toString

  def add(store: Storable, item: String) = {

  }

  def contains(thing: String): Boolean = {
    item == thing
  }

}

case class Branch(pivot: String, leftBranchId: String, rightBranchId: String) extends Node {

  val identity = ("B" + pivot + leftBranchId + rightBranchId).sha256.hex.toString

  def add(store: Storable, item: String): Unit = {
    if (item <= pivot) {
      val leftBranch = store.retrieve(leftBranchId)
      checkHash(leftBranchId, leftBranch)
    } else {
      println("RIGHT!!")
    }
  }

  def contains(thing: String): Boolean = {
    true
  }


}