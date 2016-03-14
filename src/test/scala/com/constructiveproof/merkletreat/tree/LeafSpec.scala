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
      describe("when it's the same item") {
        ignore("should return itself"){}
      }

      describe("when the item is not yet in the tree") {
        val newItem = "blah"
        val newLeaf = Leaf(newItem)

        val newBranch = leaf.add(store, newItem)

        it("should create and store a new Leaf"){
          store.retrieve(newLeaf.identity) shouldEqual newLeaf
        }

        it("should create and store a new Branch") {
          store.retrieve(newBranch.identity) shouldEqual newBranch
        }

        describe("when the item being added is lexicographically less than the current leaf's item") {
          val newItem = "blah"
          val newLeafIdentity = Leaf(newItem).identity
          val resultBranch = leaf.add(store, newItem)

          it("should return a new Branch with the current leaf's item as the pivot") {
            resultBranch shouldEqual Branch(leaf.item, leaf.identity, newLeafIdentity)
          }
        }

        describe("when the item being added is lexicographically greater than the current leaf's item") {
          it("should return a new Branch with the new item as the pivot"){
            val newItem = "zigzag"
            val newLeafIdentity = Leaf(newItem).identity
            val resultBranch = leaf.add(store, newItem)

            resultBranch shouldEqual Branch(newItem, newLeafIdentity, leaf.identity)
          }
        }
      }

      ignore("when the item has previously been added to the tree") {
        // In a console, adding twice results in the exact same mapstore
        // contents, and it doesn't seem worth it to add the guard as it'll
        // mean returning multiple types === superclass. Ask George about it.
        it("should not add anything at all") {}
      }
    }
  }

}
