package com.constructiveproof.merkletreat.stores

import com.constructiveproof.merkletreat.tree.Node

trait Storable {

  def add(node: Node): Unit

  def retrieve(key: String): Node

}
