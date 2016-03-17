package com.constructiveproof.merkletreat.stores

import com.constructiveproof.merkletreat.tree.Node

trait DataStore {

  def put(node: Node): Unit

  def get(key: String): Node

}
