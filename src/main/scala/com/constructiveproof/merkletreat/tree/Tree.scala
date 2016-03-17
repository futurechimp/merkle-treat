package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.Storable
import com.constructiveproof.merkletreat.utils.Hashify

class Tree(dataStore: Storable, rootHash: String = "Om") {

  var head: String = rootHash

  def add(item: String): Unit = {
    val key = Hashify(item)
    if (isEmpty) {
      val leaf = Leaf(key, dataStore)
      dataStore.put(leaf)
      head = leaf.identity
    } else {
      val newHeadNode = headNode.add(key)
      head = newHeadNode.identity
    }
  }

  def contains(item: String): Boolean = {
    if (isEmpty) {
      false
    } else {
      val key = Hashify(item)
      headNode.contains(key)
    }
  }

  private

  def isEmpty: Boolean = {
    head == rootHash
  }

  def headNode: Node = {
    dataStore.get(head)
  }

}
