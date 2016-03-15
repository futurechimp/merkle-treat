package com.constructiveproof.merkletreat.stores

import com.constructiveproof.merkletreat.tree.Node

trait Storable {

  def theMap: scala.collection.mutable.Map[String, Node]

  def add(node: Node): Unit

  def retrieve(key: String): Node

}
