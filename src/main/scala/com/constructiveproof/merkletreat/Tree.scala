package com.constructiveproof.merkletreat

class Tree {

  private var store: Map[String, String]  = Map.empty

  def add(key: String, value: String) = {
    val thing = (key -> value)
    store = store + thing
  }

  def isIn(key: String): Boolean = {
    store.contains(key)
  }

}
