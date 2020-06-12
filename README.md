# Submission for ContentSquare Data Engineering test
## Code
#### Comments on the code
This program contains an implementation of a Trie, applied to a autocompletion search algorithm case. It is typed in Scala 2.11.8,
built with sbt. Execute the test to get the results.

#### Comments on the autocompletion algorithm
There are usually two known algorithms for autocompletion : Tries and TST (Ternary Search Tree) which is a particular case of a trie with a binary tree store.
Depending on the cases, a trie or TST can be the better solution.

A trie obviously consumes a lot of memory due to it creating an Alphabet-length number of nodes for each new character position inserted but for this type of tests, it is not an issue.
A trie can otherwise be a good solution if, for example, the alphabet is strictly controlled to only contain characters we know we are going to have in our keywords / searches. It can also be quite efficient if the keywords have common prefixes.

A TST, on the other hand, has a generally better memory control politic, since each node only has three children. It also has an overall better time complexity than a trie. It also can be a way better solution if alphabet is large.

## Additional questions
#### That would you change if the list of keywords was much larger (300 Gb) ?
I would use 2 approaches : 

First one would be to use a modern framework that is made for searching and indexing large data : ElasticSearch. ElasticSearch is really good at these kinds of tasks. We could be for example creating a whole index only made for autocompletion., so search queries only apply on its scope. 

We have many ways of enhancing our auto-completion via ES : 

- Completion Suggester type, along with multifield mapping. A good strategy would be to copy existing fields with the completion, indexing the original and the suggester-copy when adding documents, then applying the suggest on the suggester-field them while querying.
- Applying a n-gram analyzer to a field you need to get autocompletion on, also along with multifield mapping
- Using the Prefix Query on a field, matched with aggregations. It can be a good solution if we do not have the possibility to alter mappings.

Apart from using a particular framework, caching particularly known searches and results can be a good idea, since not all 300 gb keywords are equally used.

#### What would you change if the requirements were to match any portion of the keywords ?
Using contains would be the obvious but seemingly very unnefective idea.
If the keyword is composed of multiple words separated by a blankspace, using a bag of words approach and doing the trie / tst approach on each of them.
Otherwise, using prefix-search approach and showing its results first since it should have "semantic priority", then doing a contains on the list of words we have in the tree.
