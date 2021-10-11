object BlackJack {

    fun isBust(hand: Hand): Boolean {
        return valueOver(hand, 21)
    }

    fun shouldStop(hand: Hand): Boolean {
        return valueOver(hand, 16)
    }

    fun valueOver(hand: Hand, number: Int): Boolean {
        return handValue(hand) > number
    }

    fun handValue(hand: Hand): Int {
        return hand.fold(0) { acc, card ->
            acc + cardToValue(card)
        }
    }

    fun takeACard(deck: Deck): Pair<Card, Deck> {
        return Pair(deck.first(), deck.drop(1))
    }

    fun cardToValue(card: Card) =
        when (card.rank) {
            Rank.TWO -> 2
            Rank.THREE -> 3
            Rank.FOUR -> 4
            Rank.FIVE -> 5
            Rank.SIX -> 6
            Rank.SEVEN -> 7
            Rank.EIGHT -> 8
            Rank.NINE -> 9
            Rank.TEN -> 10
            Rank.JACK -> 10
            Rank.QUEEN -> 10
            Rank.KING -> 10
            Rank.ACE -> 11
        }

    fun generateInitialState(deck: Deck): GameState {
        val (firstCard, rest1) = takeACard(deck)
        val (secondCard, rest2) = takeACard(rest1)
        val (thirdCard, rest3) = takeACard(rest2)
        val (fourthCard, rest4) = takeACard(rest3)

        return GameState(
            listOf(firstCard, thirdCard),
            listOf(secondCard, fourthCard),
            rest4
        )
    }

    fun checkInitial(gameState: GameState): GameResult? {
        if (handValue(gameState.samsCards) == 21) {
            return GameResult(gameState, Player.SAM)
        }

        if (handValue(gameState.dealersCards) == 21) {
            return GameResult(gameState, Player.DEALER)
        }

        if (isBust(gameState.samsCards)) {
            return GameResult(gameState, Player.DEALER)
        }

        if (isBust(gameState.dealersCards)) {
            return GameResult(gameState, Player.SAM)
        }

        return null
    }

    fun playGame(gameState: GameState, player: Player = Player.SAM): GameResult {
        when (player) {
            Player.SAM -> {
                if (shouldStop(gameState.samsCards)) {
                    return playGame(gameState, Player.DEALER)
                }
                if (isBust(gameState.samsCards)) {
                    return GameResult(gameState, Player.DEALER)
                }

                val newState = dealCard(gameState, Player.SAM)

                return playGame(newState, Player.SAM)
            }

            Player.DEALER -> {
                if (isBust(gameState.dealersCards)) {
                    return GameResult(gameState, Player.SAM)
                }

                if (valueOver(gameState.dealersCards, handValue(gameState.samsCards))) {
                    return GameResult(gameState, Player.DEALER)
                }

                val newState = dealCard(gameState, Player.DEALER)

                return playGame(newState, Player.DEALER)
            }
        }
    }

    fun dealCard(gameState: GameState, dealTo: Player): GameState {
        val (nextCard, restOfDeck) = takeACard(gameState.currentDeck)
        return when (dealTo) {
            Player.SAM -> {
                gameState.copy(
                    samsCards = gameState.samsCards + listOf(nextCard),
                    currentDeck = restOfDeck
                )
            }

            Player.DEALER -> {
                gameState.copy(
                    dealersCards = gameState.dealersCards + listOf(nextCard),
                    currentDeck = restOfDeck
                )
            }
        }
    }
}

enum class Player {
    SAM,
    DEALER
}

data class GameState(
    val samsCards: Hand,
    val dealersCards: Hand,
    val currentDeck: Deck
)

data class GameResult(
    val state: GameState,
    val winner: Player
) {

    override fun toString(): String {
        return winner.name.lowercase() +  "\n" +
            "sam: " + state.samsCards.toString() + "\n" +
            "dealer: " + state.dealersCards.toString()
    }
}
