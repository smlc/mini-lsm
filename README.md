# Mini-LSM in Java
From https://github.com/skyzh/mini-lsm


Why we need an ordered data structure for the in-memory table ?

*Range Queries*
An ordered data structure allows for efficient range queries, if we need to retrieve key between "apple" and "banana" for
example. Using an unordered map would require to scan all the entries. 

*Read Amplification*
When reading data if the key are sorted it's easier to determine which SSTable might contain the key we're looking for.

*Write Amplification*
Sorted data can help reduce write amplification during compaction.


### Ressources :
- http://khangaonkar.blogspot.com/2015/07/concurrenthashmap-vs.html
- https://skyzh.github.io/mini-lsm