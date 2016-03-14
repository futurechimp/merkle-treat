package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.Storable
import com.roundeights.hasher.Implicits._

sealed trait Node {
  val identity: String

  def add(item: String): Node

  def checkHash(key: String, value: Node) = {
    if (key != value.identity) {
      throw HashIdentityException()
    }
  }

  def contains(thing: String): Boolean

  case class HashIdentityException(message: String = "Stored key doesn't match hash!") extends Throwable(message)

}


case class Leaf(item: String, store: Storable) extends Node {
  val identity = ("L" + item).sha256.hex.toString

  def add(newItem: String): Node = {
    if (newItem == item) {
      return this // TODO: gross for type safety
    }

    val newLeaf = Leaf(newItem, store)
    store.add(newLeaf)

    val newBranch = if (item < newItem) {
      Branch(item, identity, newLeaf.identity, store)
    } else {
      Branch(newItem, newLeaf.identity, identity, store)
    }

    store.add(newBranch)
    newBranch
  }

  def contains(thing: String): Boolean = {
    item == thing
  }

}

case class Branch(pivot: String, leftLeafId: String, rightLeafId: String, store: Storable) extends Node {

  val identity = ("B" + pivot + leftLeafId + rightLeafId).sha256.hex.toString

  def add(newItem: String): Branch = {
    val branch = if (newItem <= pivot) {
      val leftLeaf = store.retrieve(leftLeafId)
      checkHash(leftLeafId, leftLeaf)
      val subLeaf = leftLeaf.add(newItem)
      Branch(pivot, subLeaf.identity, rightLeafId, store)

    } else {
      val rightLeaf = store.retrieve(rightLeafId)
      checkHash(rightLeafId, rightLeaf)
      val subLeaf = rightLeaf.add(newItem)
      Branch(pivot, leftLeafId, subLeaf.identity, store)
    }
    store.add(branch)
    branch
  }

  def contains(thing: String): Boolean = {
    if (thing <= pivot) {
      store.retrieve(leftLeafId).contains(thing)
    } else {
      store.retrieve(rightLeafId).contains(thing)
    }
  }

}