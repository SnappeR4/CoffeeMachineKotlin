package machine

enum class State {
    CHOOSING_ACTION,
    CHOOSING_BUY,
    FILL_WATER,
    FILL_MILK,
    FILL_BEANS,
    FILL_CUPS
}

fun main() {
    val machine = CoffeeMachine()
    machine.printActionPrompt()

    while (true) {
        val input = readln()
        if (input == "exit") {
            break
        }
        machine.processInput(input)
    }
}

class CoffeeMachine(
    var water: Int = 400,
    var milk: Int = 540,
    var beans: Int = 120,
    var cups: Int = 9,
    var money: Int = 550
) {
    private var state: State = State.CHOOSING_ACTION
    private var addedWater = 0
    private var addedMilk = 0
    private var addedBeans = 0

    fun printActionPrompt() {
        print("Write action (buy, fill, take, remaining, exit):")
    }

    fun processInput(input: String) {
        when (state) {
            State.CHOOSING_ACTION -> {
                when (input) {
                    "buy" -> {
                        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
                        state = State.CHOOSING_BUY
                    }
                    "fill" -> {
                        println("Write how many ml of water you want to add:")
                        state = State.FILL_WATER
                    }
                    "take" -> {
                        println("I gave you \$$money")
                        money = 0
                        printActionPrompt()
                    }
                    "remaining" -> {
                        machineStatus()
                        printActionPrompt()
                    }
                    else -> {
                        println("Invalid action.")
                        printActionPrompt()
                    }
                }
            }
            State.CHOOSING_BUY -> {
                when (input) {
                    "1" -> {
                        makeCoffee(250, 0, 16, 4)
                        state = State.CHOOSING_ACTION
                        printActionPrompt()
                    }
                    "2" -> {
                        makeCoffee(350, 75, 20, 7)
                        state = State.CHOOSING_ACTION
                        printActionPrompt()
                    }
                    "3" -> {
                        makeCoffee(200, 100, 12, 6)
                        state = State.CHOOSING_ACTION
                        printActionPrompt()
                    }
                    "back" -> {
                        state = State.CHOOSING_ACTION
                        printActionPrompt()
                    }
                    else -> {
                        println("Invalid choice.")
                        state = State.CHOOSING_ACTION
                        printActionPrompt()
                    }
                }
            }
            State.FILL_WATER -> {
                addedWater = input.toInt()
                println("Write how many ml of milk you want to add:")
                state = State.FILL_MILK
            }
            State.FILL_MILK -> {
                addedMilk = input.toInt()
                println("Write how many grams of coffee beans you want to add:")
                state = State.FILL_BEANS
            }
            State.FILL_BEANS -> {
                addedBeans = input.toInt()
                println("Write how many disposable cups you want to add:")
                state = State.FILL_CUPS
            }
            State.FILL_CUPS -> {
                val addedCups = input.toInt()
                water += addedWater
                milk += addedMilk
                beans += addedBeans
                cups += addedCups
                state = State.CHOOSING_ACTION
                printActionPrompt()
            }
        }
    }

    private fun machineStatus() {
        println("The coffee machine has:")
        println("$water ml of water")
        println("$milk ml of milk")
        println("$beans g of coffee beans")
        println("$cups disposable cups")
        println("\$$money of money\n")
    }

    private fun makeCoffee(reqWater: Int, reqMilk: Int, reqBeans: Int, cost: Int) {
        if (water >= reqWater && milk >= reqMilk && beans >= reqBeans && cups >= 1) {
            println("I have enough resources, making you a coffee!")
            water -= reqWater
            milk -= reqMilk
            beans -= reqBeans
            cups -= 1
            money += cost
        } else {
            if (water < reqWater) {
                println("Sorry, not enough water!")
            } else if (milk < reqMilk) {
                println("Sorry, not enough milk!")
            } else if (beans < reqBeans) {
                println("Sorry, not enough beans!")
            } else if (cups < 1) {
                println("Sorry, not enough cups!")
            } else {
                println("Sorry, not enough resources!")
            }
        }
    }
}