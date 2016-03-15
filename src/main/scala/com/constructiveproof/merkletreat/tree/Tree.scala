package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.Storable

class Tree(store: Storable) {

  def add(item: String) = {
    val node = Leaf(item, store)
    store.add(node)
  }

  def isIn(key: String): Boolean = {
    true
  }

}
