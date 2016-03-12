package com.constructiveproof.merkletreat.stores

import com.constructiveproof.merkletreat.testsupport.TestStack
import com.constructiveproof.merkletreat.tree.Leaf

class MapStoreSpec extends TestStack {

  describe("Using a Map as a key-value store") {
    val store = new MapStore
    val item = "foo"
    val leaf = Leaf(item)

    describe("adding a leaf") {
      store.add(store, leaf)
    }

    describe("retrieving the item") {
      val key = leaf.identity

      it("should be retrievable by key") {
        store.retrieve(key) shouldEqual Leaf(item)
      }
    }
  }

}
