package com.constructiveproof.merkletreat.stores

import com.constructiveproof.merkletreat.tree.Node

/**
  * Extend this trait with code specific to your data store, and you can
  * keep Merkle Tree data in whatever store suits your needs.
  */
trait DataStore {

  def put(node: Node): Unit

  def get(key: String): Node

}
