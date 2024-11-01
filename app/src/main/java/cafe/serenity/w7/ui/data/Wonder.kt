package cafe.serenity.w7.ui.data

import kotlinx.serialization.Serializable

@Serializable
data class Wonder(val name: String, val side: Side)

enum class Side {
    A,
    B,
    Undersided
}

val allWonders = listOf(
    Wonder("The Colossus of Rhodes", Side.Undersided),
    Wonder("The Lighthouse of Alexandria", Side.Undersided),
    Wonder("The Temple of Artemis in Ephesus", Side.Undersided),
    Wonder("The Hanging Gardens of Babylon", Side.Undersided),
    Wonder("The Statue of Zeus in Olympia", Side.Undersided),
    Wonder("The Mausoleum of Halicarnassus", Side.Undersided),
    Wonder("The Pyramids of Giza", Side.Undersided)
)