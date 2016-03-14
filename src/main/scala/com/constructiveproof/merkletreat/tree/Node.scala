package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.Storable
import com.roundeights.hasher.Implicits._

sealed trait Node {
  val identity: String

  def add(store: Storable, item: String): Node

  def checkHash(key: String, value: Node) = {
    if (key != value.identity) {
      throw HashIdentityException()
    }
  }

  case class HashIdentityException(message: String = "Stored key doesn't match hash!") extends Throwable(message)

}


case class Leaf(item: String) extends Node {
  val identity = ("L" + item).sha256.hex.toString.substring(0, 4)

  def add(store: Storable, newItem: String): Node = {
    if (newItem == item) {
      return this
    }
    val newLeaf = Leaf(newItem)
    store.add(newLeaf)

    val newBranch = if (item < newItem) {
      Branch(item, identity, newLeaf.identity)
    } else {
      Branch(newItem, newLeaf.identity, identity)
    }

    store.add(newBranch)
    assert(newBranch.leftLeafId != newBranch.rightLeafId)
    newBranch
  }

  def contains(thing: String): Boolean = {
    item == thing
  }

}

case class Branch(pivot: String, leftLeafId: String, rightLeafId: String) extends Node {

  val identity = ("B" + pivot + leftLeafId + rightLeafId).sha256.hex.toString.substring(0, 4)

  def add(store: Storable, item: String): Branch = {
    val branch = if (item <= pivot) {
      val leftLeaf = store.retrieve(leftLeafId)
      checkHash(leftLeafId, leftLeaf)
      val subLeaf = leftLeaf.add(store, item)
      Branch(pivot, subLeaf.identity, rightLeafId)

    } else {
      val rightLeaf = store.retrieve(rightLeafId)
      checkHash(rightLeafId, rightLeaf)
      val subLeaf = rightLeaf.add(store, item)
      Branch(pivot, leftLeafId, subLeaf.identity)
    }
    store.add(branch)
    branch
  }

  def contains(store: Storable, thing: String): Boolean = {
    if (thing <= pivot) {
      store.retrieve(leftLeafId).asInstanceOf[Leaf].contains(thing)
    } else {
      store.retrieve(rightLeafId).asInstanceOf[Leaf].contains(thing)
    }
  }
}