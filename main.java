package machine;

import java.util.Scanner;

// Enum representing different types of coffee
enum CoffeeType {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    int waterNeeded;
    int milkNeeded;
    int beansNeeded;
    int cost;

    CoffeeType(int waterNeeded, int milkNeeded, int beansNeeded, int cost) {
        this.waterNeeded = waterNeeded;
        this.milkNeeded = milkNeeded;
        this.beansNeeded = beansNeeded;
        this.cost = cost;
    }
}

// Class representing the Coffee Machine
class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int cash;
    private int coffeeCount;

    public CoffeeMachine() {
        this.water = 400;
        this.milk = 540;
        this.beans = 120;
        this.cups = 9;
        this.cash = 550;
        this.coffeeCount = 0;
    }

    public boolean hasEnoughResources(CoffeeType coffee) {
        if (water < coffee.waterNeeded) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (milk < coffee.milkNeeded) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (beans < coffee.beansNeeded) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
        if (cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }
        return true;
    }

    public void makeCoffee(CoffeeType coffee) {
        if (hasEnoughResources(coffee)) {
            water -= coffee.waterNeeded;
            milk -= coffee.milkNeeded;
            beans -= coffee.beansNeeded;
            cups--;
            cash += coffee.cost;
            coffeeCount++;
            System.out.println("I have enough resources, making you a coffee!");
        }
    }

    public void fill(int water, int milk, int beans, int cups) {
        this.water += water;
        this.milk += milk;
        this.beans += beans;
        this.cups += cups;
    }

    public void takeMoney() {
        System.out.println("I gave you $" + cash);
        cash = 0;
    }

    public void displayState() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(beans + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + cash + " of money");
        System.out.println();
    }

    public int getCoffeeCount() {
        return coffeeCount;
    }

    public void resetCoffeeCount() {
        coffeeCount = 0;
    }

    public void clean() {
        System.out.println("I have been cleaned!");
        resetCoffeeCount();
    }
}

// Class for managing machine operations and user input
class CoffeeMachineManager {
    private CoffeeMachine machine;
    private boolean requiresCleaning;

    public CoffeeMachineManager(CoffeeMachine machine) {
        this.machine = machine;
        this.requiresCleaning = false;
    }

    public void processUserInput(Scanner scanner) {
        while (true) {
            if (machine.getCoffeeCount() >= 10 && !requiresCleaning) {
                requiresCleaning = true;
            }

            System.out.println("Write action (buy, fill, take, clean, remaining, exit):");
            String action = scanner.next();

            if (requiresCleaning && !action.equals("clean")) {
                System.out.println("I need cleaning!");
                continue;
            }

            switch (action) {
                case "buy":
                    if (!requiresCleaning) {
                        buy(scanner);
                    }
                    break;
                case "fill":
                    fill(scanner);
                    break;
                case "take":
                    machine.takeMoney();
                    break;
                case "remaining":
                    machine.displayState();
                    break;
                case "clean":
                    machine.clean();
                    requiresCleaning = false;
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid action");
                    break;
            }
        }
    }

    private void buy(Scanner scanner) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String choice = scanner.next();

        switch (choice) {
            case "1":
                machine.makeCoffee(CoffeeType.ESPRESSO);
                break;
            case "2":
                machine.makeCoffee(CoffeeType.LATTE);
                break;
            case "3":
                machine.makeCoffee(CoffeeType.CAPPUCCINO);
                break;
            case "back":
                return;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void fill(Scanner scanner) {
        System.out.println("Write how many ml of water you want to add:");
        int water = scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        int milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        int beans = scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        int cups = scanner.nextInt();

        machine.fill(water, milk, beans, cups);
    }
}

// Main class to start the program
class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        CoffeeMachineManager manager = new CoffeeMachineManager(coffeeMachine);

        Scanner scanner = new Scanner(System.in);
        manager.processUserInput(scanner);
        scanner.close();
    }
}
