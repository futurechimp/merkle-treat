package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack

class TreeSpec extends TestStack {

  describe("A Merkle Tree") {
    val tree = new Tree(new MapStore)

    describe("when the tree is empty") {
      describe("adding a string") {
        tree.add("foo")

        it("should put the string in the tree") {
          tree.isIn("foo") shouldBe true
        }
      }
    }

    describe("checking for a string that isn't in the tree") {
      it("should return false") {
        val isIn = tree.isIn("bar")
        isIn shouldBe false
      }
    }
  }

}
