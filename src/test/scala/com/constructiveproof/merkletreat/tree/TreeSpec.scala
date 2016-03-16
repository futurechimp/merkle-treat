package com.constructiveproof.merkletreat.tree

import com.constructiveproof.merkletreat.stores.MapStore
import com.constructiveproof.merkletreat.testsupport.TestStack
import com.constructiveproof.merkletreat.utils.Hashify

class TreeSpec extends TestStack {

  describe("A Merkle Tree") {
    val tree = new Tree(new MapStore)
//
//    println("0. Tree store: " + tree.theStore)
//    val fooHash = Hashify("foo")
//    println("Reference: foo leaf id should be: " + fooHash)
//    println("Reference: L + foo's hash should be: " + Hashify("L" + fooHash))

    describe("when the tree is empty") {
      describe("adding a string") {
        tree.add("foo")

//        println("1. added 'foo': " + tree.theStore)

        it("should put the string in the tree") {
          tree.contains("foo") shouldBe true
        }
      }
    }

    describe("checking for a string that isn't in the tree") {
      it("should return false") {
        tree.contains("bar") shouldBe false
      }
    }

    describe("when the tree has one item in it") {


    }
  }

}
