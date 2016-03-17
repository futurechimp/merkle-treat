package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.DataStore
import com.constructiveproof.merkletreat.utils.Hashify

/**
  * A Merkle tree class which can either use a Map as a datastore (the default)
  * or be backed by a persistent datastore of your choice.
  * @param dataStore a DataStore, defaults to a Scala Map. Implement your own
  *                  by extending DataStore, and you have a persistent Merkle
  *                  tree using whatever technology suits you.
  * @param rootHash the initial hash value of the tree.
  */
class Tree(dataStore: DataStore, rootHash: String = "Om") {

  var head: String = rootHash

  /**
    * Add an item to the tree.
    * @param item the String to add.
    */
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

  /**
    * Find out whether this item has previously been added to the tree.
    * @param item the String to check for
    * @return a Boolean indicating whether the item is already in the tree.
    */
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
