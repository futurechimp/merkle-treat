package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.Storable
import com.roundeights.hasher.Implicits._

sealed trait Node {
  val identity: String

  def add(store: Storable, item: String): Branch

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

  def add(store: Storable, newItem: String): Branch = {
    val newLeaf = Leaf(newItem)
    store.add(newLeaf)

    val newBranch = if(item < newItem) {
      Branch(newItem, newLeaf.identity, identity)
    } else {
      Branch(item, identity, newLeaf.identity)
    }
//    store.add(newBranch)
    newBranch
  }

  def contains(thing: String): Boolean = {
    item == thing
  }

}

case class Branch(pivot: String, leftBranchId: String, rightBranchId: String) extends Node {

  val identity = ("B" + pivot + leftBranchId + rightBranchId).sha256.hex.toString

  def add(store: Storable, item: String): Branch = {
    if (item <= pivot) {
      val leftBranch = store.retrieve(leftBranchId)
      checkHash(leftBranchId, leftBranch)
      leftBranch.asInstanceOf[Branch]
    } else {
      val rightBranch = store.retrieve(rightBranchId)
      checkHash(rightBranchId, rightBranch)
      rightBranch.asInstanceOf[Branch]
    }
  }

  def contains(thing: String): Boolean = {
    true
  }


}