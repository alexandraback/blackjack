
enum class Suit(val stringRep: String) {
    HEARTS("H"),
    SPADES("S"),
    DIAMONDS("D"),
    CLUBS("C");

    companion object {
        fun fromString (s: String) = values().find { it.stringRep == s } ?: throw Exception("Not a Rank")
    }
    override fun toString():String {
        return this.stringRep
    }
}

enum class Rank(val stringRep: String) {
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    companion object {
        fun fromString (s: String) = values().find { it.stringRep == s } ?: throw Exception("Not a Rank")
    }
}


data class Card(val suit: Suit, val rank: Rank) {
    override fun toString(): String {
        return suit.toString() + rank.toString()
    }

    companion object {
        fun fromString(str: String): Card {
            return Card(Suit.fromString(str.substring(0, 1)), Rank.fromString(str.substring(1)))
        }
    }
}

typealias Deck = List<Card>
typealias Hand = List<Card>
