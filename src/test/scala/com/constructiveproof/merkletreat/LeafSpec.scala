package com.constructiveproof.merkletreat

import com.constructiveproof.merkletreat.testsupport.TestStack
import com.roundeights.hasher.Implicits._

class LeafSpec extends TestStack {

  describe("A leaf of the Merkle tree") {
    val leaf = Leaf("foo")

    describe("knows what item it's got inside it") {
      leaf.item shouldEqual "foo"
    }

    describe("knows its own identity") {
      describe("as a SHA256 hash of the item with the letter L prepended") {
        val output = ("L" + "foo").sha256.hex.toString
        leaf.identity shouldEqual output
      }
    }
  }

}
