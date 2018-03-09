Merkle Treat
============

A pluggable Merkle tree implementation in Scala. By default, it's backed by a 
Scala mutable Map, but if you choose to do so you can extend the 
DataStore trait in your own code, and store the tree in the 
data store technology you like.

This merkle tree is a port of George Danezis's [Hippie Hug](http://hippiehug.readthedocs.io/en/latest/)
into Scala.