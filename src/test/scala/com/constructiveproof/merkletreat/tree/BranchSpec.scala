package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack
import com.roundeights.hasher.Implicits._

class BranchSpec extends TestStack {

  describe("A branch in the Merkle tree") {
    val store = new MapStore
    val item1 = "fred"
    val item2 = "zena"
    val leftLeaf = Leaf(item1)
    val rightLeaf = Leaf(item2)
    val leftBranchId = leftLeaf.identity
    val rightBranchId = rightLeaf.identity

    val branch = leftLeaf.add(store, item1)

    describe("identity") {
      ignore("should be a SHA256 hash of the branch properties prepended by the letter B") {
        println("Store: " + store.map)
        println("B: " + branch)
        println("lid: " + leftBranchId)
        println("rid: " + rightBranchId)
        branch.identity shouldEqual ("B" + item1 + leftBranchId + rightBranchId).sha256.hex.toString
      }
    }

    describe("adding an item") {
      describe("when the string being added is lexicographically less than the pivot") {
        it("should add the string to the left side of the branch") {
          branch.add(store, item1)
          branch contains item1 shouldEqual true
          // figure out which side of the branch it got added to
        }
      }

      describe("when the string being added is lexicographically greater than the pivot") {
        it("it should add the string to the right side of the branch") {
          branch.add(store, item2)
          branch contains item2 shouldEqual true
          // figure out which side of the branch it got added to
        }
      }
    }

  }

}
