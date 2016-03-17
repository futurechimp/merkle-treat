package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.Storable
import com.constructiveproof.merkletreat.utils.Hashify

sealed trait Node {

  val identity: String

  def add(it: String): Node

  def checkHash(key: String, value: Node) = {
    if (key != value.identity) {
      throw HashIdentityException()
    }
  }

  def contains(it: String): Boolean

  case class HashIdentityException(message: String = "Stored key doesn't match hash!") extends Throwable(message)

}


case class Leaf(item: String, dataStore: Storable) extends Node {

  val identity = Hashify("L" + item)

  def add(it: String): Node = {
    if (it == item) {
      this
    } else {
      create(it)
    }
  }

  def contains(it: String): Boolean = {
    it == item
  }

  private

  def create(it: String): Branch = {
    val newLeaf = createNewLeaf(it)
    val newBranch = createNewBranch(it, newLeaf)
    dataStore.add(newLeaf)
    dataStore.add(newBranch)
    newBranch
  }

  def createNewLeaf(it: String): Leaf = {
    Leaf(it, dataStore)
  }

  def createNewBranch(it: String, newLeaf: Leaf): Branch = {
    if (item < it) {
      Branch(item, identity, newLeaf.identity, dataStore)
    } else {
      Branch(it, newLeaf.identity, identity, dataStore)
    }
  }

}

case class Branch(pivot: String, leftLeafId: String, rightLeafId: String, dataStore: Storable) extends Node {

  val identity = Hashify("B" + pivot + leftLeafId + rightLeafId)

  def add(it: String): Branch = {
    val branch = if (it <= pivot) {
      val leftLeaf = dataStore.retrieve(leftLeafId)
      checkHash(leftLeafId, leftLeaf)
      val subLeaf = leftLeaf.add(it)
      Branch(pivot, subLeaf.identity, rightLeafId, dataStore)

    } else {
      val rightLeaf = dataStore.retrieve(rightLeafId)
      checkHash(rightLeafId, rightLeaf)
      val subLeaf = rightLeaf.add(it)
      Branch(pivot, leftLeafId, subLeaf.identity, dataStore)
    }
    dataStore.add(branch)
    branch
  }

  def contains(thing: String): Boolean = {
    if (thing <= pivot) {
      dataStore.retrieve(leftLeafId).contains(thing)
    } else {
      dataStore.retrieve(rightLeafId).contains(thing)
    }
  }

}