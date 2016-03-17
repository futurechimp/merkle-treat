package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.DataStore
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


case class Leaf(item: String, dataStore: DataStore) extends Node {

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
    dataStore.put(newLeaf)
    dataStore.put(newBranch)
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

case class Branch(pivot: String, leftLeafId: String, rightLeafId: String, dataStore: DataStore) extends Node {

  val identity = Hashify("B" + pivot + leftLeafId + rightLeafId)

  def add(it: String): Branch = {
    val branch = if (it <= pivot) {
      val leftLeaf = dataStore.get(leftLeafId)
      checkHash(leftLeafId, leftLeaf)
      val newLeaf = leftLeaf.add(it)
      Branch(pivot, newLeaf.identity, rightLeafId, dataStore)

    } else {
      val rightLeaf = dataStore.get(rightLeafId)
      checkHash(rightLeafId, rightLeaf)
      val newLeaf = rightLeaf.add(it)
      Branch(pivot, leftLeafId, newLeaf.identity, dataStore)
    }
    dataStore.put(branch)
    branch
  }

  def contains(it: String): Boolean = {
    if (it <= pivot) {
      dataStore.get(leftLeafId).contains(it)
    } else {
      dataStore.get(rightLeafId).contains(it)
    }
  }

}