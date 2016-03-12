package com.constructiveproof.merkletreat.stores

import com.constructiveproof.merkletreat.tree.{Branch, Leaf, Node}
import scala.collection.mutable

class MapStore extends Storable {

  var map = mutable.Map[String, String]()

  override def add(node: Node): Unit = {
    node match {
      case leaf: Leaf => map.update(leaf.identity, leaf.item)
    }

  }

  override def retrieve(key: String): Node = {
    Leaf(map(key))
  }

}
