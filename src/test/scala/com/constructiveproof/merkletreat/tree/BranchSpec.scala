package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack
import com.roundeights.hasher.Implicits._

class BranchSpec extends TestStack {

  describe("A branch in the Merkle tree") {
    val store = new MapStore
    val fred = "fred"
    val zena = "zena"
    val fredLeaf = Leaf(fred)
    store.add(fredLeaf)

    val branch = fredLeaf.add(store, zena).asInstanceOf[Branch]
    val tmpZenaLeaf = Leaf("zena")
    val zenaLeaf = store.retrieve(branch.rightLeafId)
    assert(zenaLeaf == tmpZenaLeaf)
    assert(zenaLeaf.identity != fredLeaf.identity)

    describe("identity") {
      ignore("should be a SHA256 hash of the branch properties prepended by the letter B") {
        branch.identity shouldEqual ("B" + fred + fredLeaf.identity + zenaLeaf.identity).sha256.hex.toString.substring(0, 4)
      }
    }

    describe("adding an item containing 'aaa'") {
      val aaa = "aaa"
      val result = branch.add(store, aaa)
      val aaaLeaf = store.retrieve(Leaf("aaa").identity)

      describe("since 'aaa' is lexicographically greater than the branch pivot, currently 'zena'") {
        it("xxx should add a new Leaf(aaa) to the left side of the branch") {
          store.retrieve(result.leftLeafId).asInstanceOf[Branch].leftLeafId shouldEqual aaaLeaf.identity
        }

        it("should connect the branch to the other leaf") {
          result.rightLeafId shouldEqual zenaLeaf.identity
        }

        ignore("should make the new branch's pivot 'aaa'") {}

        describe("asking whether the item is in one of the leaves connected to the branch") {
          it("returns true") {
            result contains(store, fred) shouldEqual true
          }
        }
      }

      describe("when the string being added is lexicographically greater than the pivot") {

        branch.add(store, zena)
        it("it should add a new Leaf to the right side of the branch") {
          branch.leftLeafId shouldEqual fredLeaf.identity
          branch.rightLeafId shouldEqual zenaLeaf.identity
        }

        describe("asking whether the item is in one of the leaves connected to the branch") {
          it("returns true") {
            branch contains(store, zena) shouldEqual true
          }
        }


      }
    }

    describe("attempting to find an item which isn't in the tree") {
      it("should fail") {
        branch.contains(store, "nope") shouldEqual false
      }
    }

  }

}
