package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack
import com.roundeights.hasher.Implicits._

class LeafSpec extends TestStack {

  describe("A leaf of the Merkle tree") {
    val store = new MapStore
    val foo = "foo"
    val fooLeaf = Leaf(foo)
    store.add(fooLeaf)

    describe("knows what item it's got inside it") {
      fooLeaf.item shouldEqual foo
    }

    describe("leaf identity") {
      it("should be a SHA256 hash of the item with the letter L prepended") {
        val identityHash = ("L" + foo).sha256.hex.toString
        fooLeaf.identity shouldEqual identityHash
      }
    }

    describe("checking if an item is in the leaf") {
      it("should return true if the item is the leaf's item") {
        fooLeaf contains foo shouldEqual true
      }

      it("should return false if the item isn't the leaf's item") {
        fooLeaf contains "bar" shouldEqual false
      }
    }

    describe("adding an item to a Leaf") {
      describe("when it's the same item") {
        ignore("should return itself") {}
      }

      describe("when the item is not yet in the tree") {
        val newItem = "blah"
        val newLeaf = Leaf(newItem)

        val newBranch = fooLeaf.add(store, newItem)

        it("should create and store a new Leaf") {
          store.retrieve(newLeaf.identity) shouldEqual newLeaf
        }

        it("should create and store a new Branch") {
          store.retrieve(newBranch.identity) shouldEqual newBranch
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
