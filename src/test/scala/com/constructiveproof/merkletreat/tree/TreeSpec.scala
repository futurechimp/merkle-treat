package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.testsupport.TestStack

class TreeSpec extends TestStack {

  describe("A Merkle Tree") {
    val tree = new Tree

    describe("after adding a string") {
      tree.add("foo", "value")

      it("should be be in the tree") {
        val isIn = tree.isIn("foo")
        isIn shouldBe true
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
