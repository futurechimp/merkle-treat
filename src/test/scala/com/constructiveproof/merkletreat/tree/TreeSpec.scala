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
          tree.contains("foo") shouldBe true
        }
      }
    }

    describe("checking for a string that isn't in the tree") {
      it("should return false") {
        tree.contains("nope") shouldBe false
      }
    }

    describe("when the tree has one item in it") {
      describe("adding another string") {
        tree.add("bar")

        it("should put the string in the tree") {
          tree.contains("bar") shouldBe true
        }
      }

      describe("checking for a string that isn't in the tree") {
        it("should return false") {
          tree.contains("nope2") shouldBe false
        }
      }

      describe("checking for the original string we put in the tree") {
        it("should still return true") {
          tree.contains("foo") shouldEqual true
        }
      }
    }
  }
}
