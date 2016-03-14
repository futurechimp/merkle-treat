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

  def contains(thing: String): Boolean

  def contains(store: Storable, thing: String): Boolean

  case class HashIdentityException(message: String = "Stored key doesn't match hash!") extends Throwable(message)

}


case class Leaf(item: String) extends Node {
  val identity = ("L" + item).sha256.hex.toString //.substring(0, 4)

  def add(store: Storable, newItem: String): Node = {
    if (newItem == item) {
      return this // TODO: gross for type safety
    }

    val newLeaf = Leaf(newItem)
    store.add(newLeaf)

    val newBranch = if (item < newItem) {
      Branch(item, identity, newLeaf.identity)
    } else {
      Branch(newItem, newLeaf.identity, identity)
    }

    store.add(newBranch)
    newBranch
  }

  def contains(thing: String): Boolean = {
    item == thing
  }

  def contains(store: Storable, thing: String) = ???

}

case class Branch(pivot: String, leftLeafId: String, rightLeafId: String) extends Node {

  val identity = ("B" + pivot + leftLeafId + rightLeafId).sha256.hex.toString

  def add(store: Storable, newItem: String): Branch = {
    val branch = if (newItem <= pivot) {
      val leftLeaf = store.retrieve(leftLeafId)
      checkHash(leftLeafId, leftLeaf)
      val subLeaf = leftLeaf.add(store, newItem)
      Branch(pivot, subLeaf.identity, rightLeafId)

    } else {
      val rightLeaf = store.retrieve(rightLeafId)
      checkHash(rightLeafId, rightLeaf)
      val subLeaf = rightLeaf.add(store, newItem)
      Branch(pivot, leftLeafId, subLeaf.identity)
    }
    store.add(branch)
    branch
  }

  def contains(thing: String) = ???

  def contains(store: Storable, thing: String): Boolean = {
    if (thing <= pivot) {
      store.retrieve(leftLeafId) match {
        case leaf: Leaf => leaf.contains(thing)
        case branch: Branch => branch.contains(store, thing)
      }
    } else {
      store.retrieve(rightLeafId) match {
        case leaf: Leaf => leaf.contains(thing)
        case branch: Branch => branch.contains(store, thing)
      }
    }
  }
}