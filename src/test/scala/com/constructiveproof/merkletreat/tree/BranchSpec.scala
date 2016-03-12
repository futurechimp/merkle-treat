package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack
import com.roundeights.hasher.Implicits._

class BranchSpec extends TestStack {

  describe("A branch in the Merkle tree") {
    val store = new MapStore
    val leftItem = "fred" // should be less than "pivot"
    val rightItem = "zena" // should be greater than "pivot"
//    store.add()

    val branch = Branch(store, "pivot", "leftId", "rightId")

    describe("identity") {
      it("should be a SHA256 hash of the branch properties prepended by the letter B") {
        branch.identity shouldEqual ("B" + "pivot" + "leftId" + "rightId").sha256.hex.toString
      }
    }

    describe("adding a string") {
      describe("when the string being added is lexicographically less than the pivot") {
        ignore("should add the string to the left side of the branch") {
          branch.add(store, leftItem)
          branch contains leftItem shouldEqual true
          // figure out which side of the branch it got added to
        }
      }

      describe("when the string being added is lexicographically greater than the pivot") {
        ignore("it should add the string to the right side of the branch") {
          branch.add(store, rightItem)
          branch contains rightItem shouldEqual true
          // figure out which side of the branch it got added to
        }
      }
    }

  }

}
