Merkle Treat
============

A Merkle tree implementation in Scala. By default, it's backed by a 
Scala mutable Map, but if you choose to do so you can extend the 
DataStore trait in your own code, and store the tree in the 
data store technology you like.

How it works
------------

A good opportunity for ASCII art:

L: a leaf
P: a pivot
/: a branch

A leaf is a data structure that holds the content  
