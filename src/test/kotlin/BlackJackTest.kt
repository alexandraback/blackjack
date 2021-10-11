import org.junit.jupiter.api.Test

class BlackJackTest {

    @Test
    fun givenCardListGivesCorrectResult() {
        val deck = listOf(
            Card(Suit.CLUBS, Rank.ACE),
            Card(Suit.DIAMONDS, Rank.FIVE),
            Card(Suit.HEARTS, Rank.NINE),
            Card(Suit.HEARTS, Rank.QUEEN),
            Card(Suit.SPADES, Rank.EIGHT)
        )

        val initialState = BlackJack.generateInitialState(deck)

        val result = BlackJack.playGame(initialState)

        print(result)

        assert(result.winner == Player.SAM)
        assert(result.state.dealersCards == listOf(
            Card(Suit.DIAMONDS, Rank.FIVE),
            Card(Suit.HEARTS, Rank.QUEEN),
            Card(Suit.SPADES, Rank.EIGHT)
        ))

    }
}