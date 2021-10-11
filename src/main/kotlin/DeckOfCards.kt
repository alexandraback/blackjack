class DeckOfCards {
  companion object {
      val deck = Suit.values().flatMap { suit ->
          Rank.values().map { rank ->
              Card(suit, rank)
          }
      }
  }
}