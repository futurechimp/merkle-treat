package com.constructiveproof.merkletreat.stores

import com.constructiveproof.merkletreat.testsupport.TestStack
import com.constructiveproof.merkletreat.tree.{Branch, Leaf}

class MapStoreSpec extends TestStack {

  describe("Using a Map as a key-value store") {
    val store = new MapStore
    val item = "foo"
    val leaf = Leaf(item)
    val branch = Branch("pivot", "leftBranchId", "rightBranchId")

    describe("adding a leaf") {
      store.add(leaf)

      describe("retrieving the item") {
        val key = leaf.identity

        it("should be retrievable by key") {
          store.retrieve(key) shouldEqual leaf
        }
      }
    }

    describe("adding a branch") {
      store.add(branch)
      val key = branch.identity

      it("should be retrievable by key") {
        store.retrieve(key) shouldEqual branch
      }
    }


  }

}
