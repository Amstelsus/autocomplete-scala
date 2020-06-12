package tn.aziz.autocomplete

import scala.collection.mutable
//Implementation of Trie with alphabet set at 256 (ASCII characters)
class Autocomplete(alphabet: Int = 256) {

  /**
   *
   * @param knownWord does the node chain until this one constitute a word
   * @param children node children of the current node
   */
  case class Node(var knownWord: Boolean = false, var children: List[Option[Node]] = List.fill(alphabet)(None))
  //root empty node
  private val root: Node = new Node

  /***
   * method that inserts a word in the trie dictionary (recursive)
   * @param word word to be inserted
   * @param node current node being investigated
   * @param index cursor of the word being inserted
   */
  private def putWord(word: String, node: Node = root, index: Int = 0): Unit = {
      if (index == word.length && !node.knownWord) {
        node.knownWord = true //Cursor has navigated through all the word
      }
      else if (index < word.length) {
        val nextNodePosition = word.charAt(index).toInt //convert character to ASCII
        // Do I have an existent letter in this node's children
        node.children(nextNodePosition) match {
          case Some(_) => putWord(word, node.children(nextNodePosition).get, index + 1)
          case None => {
            node.children = node.children.updated(nextNodePosition, Some(new Node))
            putWord(word, node.children(nextNodePosition).get, index + 1)
          }
        }
    }
  }
  def putListOfWords(words: List[String]) = words.map(_.toLowerCase).foreach(putWord(_)) //lowercase

  /***
   * gives resulting keywords for a search
   * @param search search word initiated by the user
   * @return list of resulting keywords
   */
  def wordsSuggestion(search: String): List[String] = {
    val lowerCaseSearch = search.toLowerCase
    val startingPoint = getSearch(Some(root), lowerCaseSearch)
    val results = new mutable.Queue[String]()
    collect(startingPoint, new StringBuilder(lowerCaseSearch), results)
    results.toList
  }

  /**
   * returns node corresponding to the last character of the search string
   */
  private def getSearch(node: Option[Node], search: String, cursor: Int = 0): Option[Node] = {
      node.map(node => {
        if(search.length == cursor)
          node
        else
          return getSearch(node.children(search.charAt(cursor).toInt), search, cursor + 1)
      })
    }

      /**
       * method to collect results for search
       */
      private def collect(node: Option[Node], search: StringBuilder, results: mutable.Queue[String]): Unit = {
        node.map(node => {
      if (node.knownWord)
        results.enqueue(search.toString) // it's a known word, so I add it. No need to sort, ASCII does the job
      if (results.length < 4) {
        for (c <- 0 to alphabet - 1) {
          search.append(c.toChar) // looking for all the possibilies between 0 and 256 nodes
          collect(node.children(c), search, results)
          search.deleteCharAt(search.length - 1)//recursive property, we need to look for the right character
          //search.dropRight(search.length - 1)
        }
      }
    })
  }
}
