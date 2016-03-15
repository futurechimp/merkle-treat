package com.constructiveproof.merkletreat.stores

import com.constructiveproof.merkletreat.tree.{Branch, Leaf, Node}
import scala.collection.mutable

class MapStore extends Storable {

  var map = mutable.Map[String, Node]()

  def theMap = {
    map
  }

  override def add(node: Node): Unit = {
    node match {
      case leaf: Leaf => map.update(leaf.identity, leaf)
      case branch: Branch => map.update(branch.identity, branch)
    }
  }

  override def retrieve(key: String): Node = {
    map(key)
  }

}
