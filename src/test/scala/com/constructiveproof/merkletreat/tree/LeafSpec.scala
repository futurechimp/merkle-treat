package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack
import com.roundeights.hasher.Implicits._

class LeafSpec extends TestStack {

  describe("A leaf of the Merkle tree") {
    val store = new MapStore
    val item = "foo"
    val leaf = Leaf(store, item)

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
        leaf.isIn(item) shouldEqual true
      }

      it("should return false if the item isn't the leaf's item") {
        leaf.isIn("bar") shouldEqual false
      }
    }
  }

}
