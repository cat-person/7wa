package cafe.serenity.w7.model

class Repository {

    class Initial {
        val allWonders = listOf<Wonder>()
    }
}

data class Wonder(val id: Int, val name: String)

data class Guild(val name: String)

sealed interface Building {
    data class Resources(val name: String, val epoch: Int, val price: Int): Building
    data class CraftedGoods(val name: String, val epoch: Int, val price: Int): Building
    data class Military(val name: String, val epoch: Int, val strenght: Int): Building
    data class Culture(val name: String, val epoch: Int, val points: Int): Building
    data class Science(val name: String, val epoch: Int, val symbol: Symbol): Building
}

enum class Symbol{
    Gear,
    ClayTablet,
    Caliper,
}

sealed interface Event {

}