package com.constructiveproof.merkletreat.stores

import com.constructiveproof.merkletreat.tree.{Branch, Leaf, Node}
import scala.collection.mutable

class MapStore extends Storable {

  private val map = mutable.Map[String, Node]()

  override def put(node: Node): Unit = {
    node match {
      case leaf: Leaf => map.update(leaf.identity, leaf)
      case branch: Branch => map.update(branch.identity, branch)
    }
  }

  override def get(key: String): Node = {
    map(key)
  }
}
