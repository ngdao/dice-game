import dice.*;
import java.util.Scanner;

/**
 * Three to Twenty Three - Command Line Interface Version
 */
public class Main {
    public static void main(String[] args) {
        startScreen();
    }

    /**
     * Displays the CLI starting screen for the dice game.
     */
    public static void startScreen() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nWelcome to Three to Twenty Three: ");
        System.out.println("A simple dice game inspired by Blackjack.");

        int input = 0;
        boolean continueGame = true;

        //Prompts user to select a game option
        do {
            System.out.println("\nSelect an option below: ");
            System.out.println("1. How to play");
            System.out.println("2. Play");
            System.out.println("3. Leaderboard");
            System.out.println("4. Statistics");
            System.out.println("5. Quit");

            input = getIntInput();

            switch (input) {
                case 1: //How to play
                    howToPlay();
                    break;
                case 2: //Play
                    play();
                    break;
                case 3: //Leaderboard
                    leaderboard();
                    break;
                case 4: //Statistics
                    statistics();
                    break;
                case 5: //Quit
                    System.out.println("\nThanks for playing!");
                    continueGame = false;
                    break;
                default: //Invalid
                    System.out.println("\nError: Invalid option, try again!");
                    break;
            }
        } while (continueGame);
    }

    /**
     * Prints instructions on how to play the dice game.
     */
    public static void howToPlay() {
        System.out.println("\n- How to play -");

        System.out.print("The goal of the game is to reach a total of ");
        System.out.println("23, or get as close as you can without going over.\n");
        System.out.println("You reach the total by adding the sum of each roll.\n");

        System.out.print("You have the choice to use 1, 2, or 3 ");
        System.out.print("dice to increase your overall total ");
        System.out.println("and achieve the goal of 23.\n");

        System.out.println("If you go over 23, it's game over and your score is 0.\n");
        System.out.println("Stopping before 23, your total is recorded as your score.\n");
        System.out.println("If you reach 23, your score doubles to 46.");
    }

    /**
     * Allows the user to play the dice game.
     */
    public static void play() {
        //Prompt for initials
        String name = getInitials();

        //Initialize game
        DiceGame game = null;
        try {
            game = DiceGame.create(name);
        } catch (InvalidUsernameException e) {
            System.out.println("Error: " + e.getMessage());
        }

        Scanner scan = new Scanner(System.in);
        int input = 0;
        boolean continueGame = true;
        int numDice = 3;

        //Play the game
        do {
            System.out.println("\nCurrent total: " + game.getCurrentTotal());
            System.out.println("Number of dice: " + numDice);
            System.out.println("\n1. Roll");
            System.out.println("2. Change number of dice");
            System.out.println("3. Stop rolling");

            input = getIntInput();

            //Play based on user's input
            switch (input) {
                case 1: //Roll
                    int rollTotal = 0;
                    RollResult result;

                    try {
                        result = game.roll(numDice);
                        rollTotal = result.sum();
                    } catch (RollAfterGameOverException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    System.out.println("\nYou rolled " + numDice + " dice, for a total of " + rollTotal);

                    break;
                case 2: //Change number of dice
                    numDice = changeDice();
                    break;
                case 3: //Stop rolling
                    continueGame = false;
                    break;
                default: //Invalid
                    System.out.println("\nError: Invalid option, try again!");
                    break;
            }

            if (game.getCurrentTotal() >= 23) {
                //Game over
                continueGame = false;
            }
        } while (continueGame);

        //Display game's final score
        System.out.print("\nGame over! With a total of " + game.getCurrentTotal());
        System.out.println(", your final score is: " + game.getScore());
    }

    /**
     * Displays the leaderboard of high scores.
     */
    public static void leaderboard() {
        //Obtain leaderboard data
        StatsProcessor stats = StatsProcessor.create();
        LeaderboardEntry[] leaders = stats.getLeaderboard();
        
        //Header
        System.out.println("\n- High Scores -");
        System.out.printf("%-20s %-20s %-20s%n",
                          "Rank",
                          "Score",
                          "Name");
            
        //Data
        int rank = 1;
        for (LeaderboardEntry leader : leaders) {
            System.out.printf("%-20d %-20s %-20s%n",
                              rank,
                              leader.getHighestScore(),
                              leader.getPlayerName());
            
            rank++;
        }
    }

    /**
     * Prompts the user for the type of statistics.
     */
    public static void statistics() {
        boolean invalidInput = false;
        int input;

        //Allows user to choose the type of statistics.
        do {
            System.out.println("\nSelect an option below:");
            System.out.println("1. Player Statistics");
            System.out.println("2. All Statistics");

            input = getIntInput();

            switch (input) {
                case 1:
                    playerStatistics();
                    break;
                case 2:
                    allStatistics();
                    break;
                default:
                    System.out.println("\nError: Invalid option, try again!");
                    invalidInput = true;
                    break;
            }
        } while (invalidInput);
    }

    /**
     * Allows a player's statistics to be chosen and prepared for display.
     */
    public static void playerStatistics() {
        //Obtain statistical data
        StatsProcessor stats = StatsProcessor.create();
        String[] playerNames = stats.getPlayerList();
        int input;

        //Select from a list of player names
        do {
            System.out.println("\nSelect a player:");
            for (int index = 0; index < playerNames.length; index++) {
                System.out.println((index + 1) + ". " + playerNames[index]);
            }

            input = getIntInput() - 1;

        } while (!validIntInput(input, 0, (playerNames.length - 1)));

        //Select chosen player
        StatsData playerStats = stats.getPlayerStats(playerNames[input]);

        //Display player's statistics
        System.out.println("\n- Statistics for " + playerNames[input] + "-");
        displayStatistics(playerStats);
    }

    /**
     * Allows aggregate statistics to be prepared for display.
     */
    public static void allStatistics() {
        //Obtain statistical data
        StatsProcessor stats = StatsProcessor.create();
        StatsData allStats = stats.getAllStats();

        //Display aggregate statistics
        System.out.println("\n- All Statistics -");
        displayStatistics(allStats);
    }

    /**
     * Displays the statistics based on given parameter data.
     * @param stats     Chosen statistical data.
     */
    public static void displayStatistics(StatsData stats) {
        //Display headers
        System.out.printf("%-20s %-20s %-20s %-20s %-20s%n",
                          "Total Rolls",
                          "Avg Rolls Per Game",
                          "Cumulative Score",
                          "Avg Score",
                          "Avg # Of Dice Used");
        //Display data
        System.out.printf("%-20s %-20s %-20s %-20s %-20s%n",
                          stats.getTotalRolls(),
                          stats.getAvgRollsPerGame(),
                          stats.getCumulativeScore(),
                          stats.getAvgScore(),
                          stats.getAvgNumDiceUsed());
    }

    /**
     * Allows a user to change the number of dice in their hand.
     * @return          number of dice to play.
     */
    public static int changeDice() {
        Scanner scan = new Scanner(System.in);
        int numDice = 0;

        //Prompts user for the number of dice.
        do {
            System.out.print("\nEnter number of dice (1-3): ");

            numDice = getIntInput();

        } while (!validIntInput(numDice, 1, 3));

        return numDice;
    }

    /**
     * Prompt user for an integer input.
     * Displays an error if input is non-integer.
     * @return      user's integer input.
     */
    public static int getIntInput() {
        Scanner scan = new Scanner(System.in);
        int input = 0;

        while (!scan.hasNextInt()) {
            //Display an error if input is a non-integer
            System.out.println("\nError: Input must be an integer, try again!");
            //Clears the new line character
            scan.next();
        }

        //Gets user's integer input
        input = scan.nextInt();

        return input;
    }

    /**
     * Confirms given integer input is within the min and max range.
     * @param input     given integer input.
     * @param min       minimum number the input can be.
     * @param max       maximum number the input can be.
     * @return          whether the integer input is within min and max range.
     */
    public static boolean validIntInput(int input, int min, int max) {
        if (input < min || input > max) {
            //Displays error if integer input is out of range.
            System.out.println("\nError: Invalid option, try again!");
            return false;
        }
        return true;
    }

    /**
     * Prompts the user for their 3-character initials.
     * Displays an error if the initials contain non-alpha characters.
     * Displays an error if the initials aren't 3-characters.
     * @return      the user's 3-character initials.
     */
    public static String getInitials() {
        Scanner scan = new Scanner(System.in);
        String input;
        boolean invalidInitials;

        //Prompts user for 3-character initials.
        do {
            System.out.println("\nEnter your initials (3 characters): ");
            input = scan.nextLine();

            if (input.length() != 3 || !input.matches("[a-zA-Z]+")) {
                //Displays an error if input contains non-alpha character or is not 3 characters in length.
                System.out.println("\nError: Initials must contain 3 letters, try again!");
                invalidInitials = true;
            } else {
                invalidInitials = false;
            }
        } while (invalidInitials);

        return input;
    }
}