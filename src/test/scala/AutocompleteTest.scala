import org.scalatest._
import tn.aziz.autocomplete.Autocomplete

class AutocompleteTest extends FunSuite with Matchers {

  val keywords = List(
    "project runway",
    "pinterest",
    "river",
    "kayak",
    "progenex",
    "progeria",
    "pg&e",
    "project free tv",
    "bank",
    "PROActive",
    "progesterone",
    "press democrat",
    "priceline",
    "pandora",
    "reprobe",
    "paypal"
  )

  val autocomplete: Autocomplete = new Autocomplete
  autocomplete.putListOfWords(keywords)
    test("test"){
      autocomplete.wordsSuggestion("p") shouldBe List("pandora", "paypal", "pg&e", "pinterest")
      autocomplete.wordsSuggestion("pr") shouldBe List("press democrat", "priceline", "proactive", "progenex")
      autocomplete.wordsSuggestion("PRO") shouldBe List("proactive", "progenex", "progeria", "progesterone")
      autocomplete.wordsSuggestion("prog") shouldBe List("progenex", "progeria", "progesterone")
    }
}