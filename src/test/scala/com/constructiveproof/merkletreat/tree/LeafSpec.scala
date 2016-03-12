package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack
import com.roundeights.hasher.Implicits._

class LeafSpec extends TestStack {

  describe("A leaf of the Merkle tree") {
    val store = new MapStore
    val item = "foo"
    val leaf = Leaf(item)

    describe("knows what item it's got inside it") {
      leaf.item shouldEqual item
    }

    describe("leaf identity") {
      it("should be a SHA256 hash of the item with the letter L prepended") {
        val identityHash = ("L" + item).sha256.hex.toString
        leaf.identity shouldEqual identityHash
      }
    }

    describe("checking if an item is in the leaf") {
      it("should return true if the item is the leaf's item") {
        leaf contains item shouldEqual true
      }

      it("should return false if the item isn't the leaf's item") {
        leaf contains "bar" shouldEqual false
      }
    }

    describe("adding an item to a Leaf") {
      describe("when the item is not yet in the tree") {
        it("should create and store a new Leaf"){

        }
        it("should create and store a new Branch") {}

        describe("when the item being added is lexicographically less than the current leaf's item") {
          val newItem = "blah"
          val newLeafIdentity = Leaf(newItem).identity
          val resultLeaf = leaf.add(store, newItem)

          it("should return a new Branch with the current leaf's item as the pivot") {
            resultLeaf shouldEqual Branch(leaf.item, leaf.identity, newLeafIdentity)
          }
        }

        describe("when the item being added is lexicographically greater than the current leaf's item") {
          it("should return a new Branch with the new item as the pivot"){
            val newItem = "zigzag"
            val newLeafIdentity = Leaf(newItem).identity
            val resultLeaf = leaf.add(store, newItem)

            resultLeaf shouldEqual Branch(newItem, newLeafIdentity, leaf.identity)
          }
        }
      }

      describe("when the item has previously been added to the tree") {
        it("should not add anything at all") {}
      }
    }
  }

}
