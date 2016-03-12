package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack
import com.roundeights.hasher.Implicits._

class BranchSpec extends TestStack {

  describe("A branch in the Merkle tree") {
    val store = new MapStore
    val branch = Branch(store, "pivot", "leftId", "rightId")

    describe("identity") {
      it("should be a SHA256 hash of the branch properties prepended by the letter B") {
        branch.identity shouldEqual ("B" + "pivot" + "leftId" + "rightId").sha256.hex.toString
      }
    }

  }

}
