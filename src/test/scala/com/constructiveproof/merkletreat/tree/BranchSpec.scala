package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack
import com.roundeights.hasher.Implicits._

class BranchSpec extends TestStack {

  describe("A branch in the Merkle tree") {
    val store = new MapStore
    val fred = "fred"
    val zena = "zena"
    val fredLeaf = Leaf(fred, store)
    store.add(fredLeaf)

    val branch = fredLeaf.add(zena).asInstanceOf[Branch]
    val zenaLeaf = store.retrieve(branch.rightLeafId)

    describe("identity") {
      ignore("should be a SHA256 hash of the branch properties prepended by the letter B") {
        branch.identity shouldEqual ("B" + fred + fredLeaf.identity + zenaLeaf.identity).sha256.hex.toString
      }
    }

    describe("adding an item containing 'aaa'") {
      val aaa = "aaa"
      val result = branch.add(aaa)
      val aaaLeaf = store.retrieve(Leaf("aaa", store).identity)

      describe("since 'aaa' is lexicographically greater than the branch pivot, currently 'zena'") {
        it("should add a new Leaf(aaa) to the left side of the branch") {
          store.retrieve(result.leftLeafId).asInstanceOf[Branch].leftLeafId shouldEqual aaaLeaf.identity
        }

        it("should connect the branch to the other leaf") {
          result.rightLeafId shouldEqual zenaLeaf.identity
        }

        ignore("should make the new branch's pivot 'aaa'") {}

        describe("asking whether the item is in one of the leaves connected to the branch") {
          it("xxx returns true"){
            result contains fred shouldEqual true
          }
        }
      }

      describe("when the string being added is lexicographically greater than the pivot") {
        branch.add(zena)

        it("it should add a new Leaf(zena) to the right side of the branch") {
          branch.leftLeafId shouldEqual fredLeaf.identity
          branch.rightLeafId shouldEqual zenaLeaf.identity
        }

        describe("asking whether the item is in one of the leaves connected to the branch") {
          it("returns true") {
            branch contains zena shouldEqual true
          }
        }
      }
    }

    describe("attempting to find an item which isn't in the tree") {
      it("should fail") {
        branch.contains("nope") shouldEqual false
      }
    }
  }
}
