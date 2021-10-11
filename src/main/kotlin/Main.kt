import BlackJack.checkInitial
import BlackJack.generateInitialState
import BlackJack.playGame
import java.io.File
import java.lang.Exception

fun main(args: Array<String>) {

    val shuffledDeck = if (args.isNotEmpty()) {
        try {
            fileToDeck(args)
        } catch (e: Exception) {
            println("Something went wrong $e")
            return
        }
    } else {
        DeckOfCards.deck.shuffled()
    }

    val initialGameState = generateInitialState(shuffledDeck)
    println(checkInitial(initialGameState) ?: playGame(initialGameState))
}

private fun fileToDeck(args: Array<String>) = File(args[0])
    .readLines()
    .first()
    .split(",")
    .map { it.trim() }
    .map { Card.fromString(it) }