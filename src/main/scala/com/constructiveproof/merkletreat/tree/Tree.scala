package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.Storable
import com.constructiveproof.merkletreat.utils.Hashify

class Tree(store: Storable, rootHash: String = "Om") {

  var head: String = rootHash

  def theStore = {
    store.theMap
  }

  def add(item: String) = {
    val key = Hashify(item)
    if (isEmpty) {
      val leaf = Leaf(key, store)
      store.add(leaf)
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
    store.retrieve(head)
  }

}
