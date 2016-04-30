# Three to 23

[![Join the chat at https://gitter.im/anderspitman/dice-game](https://badges.gitter.im/anderspitman/dice-game.svg)](https://gitter.im/anderspitman/dice-game?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/anderspitman/dice-game.svg?branch=master)](https://travis-ci.org/anderspitman/dice-game)

A simple dice game inspired by Blackjack.

# Goal of the game
The goal of the game is to accumulate a total of exactly 23, or get as close as you can without going over, by rolling 1-3 dice an unlimited amount of times. You will have the choice to use 1, 2, or 3 dice to increase your overall total and achieve the goal of 23.

# Rules of the Game
1.  The player must enter three letters, generally initials, for their game name.
2.  The player can choose the number of dice to use per roll.
3.  The player must roll the chosen number of dice and add the die faces to get a total.
    - For example, if you roll a 5, 6, and 3, your total will be 14.
4.  The player will have the option to stop there and record their score or they can roll again to increase their total.
5.  The player can roll as many times as they want, but as soon as their total score equals or exceeds 23, the game is over.

# How scores are calculated
If you go over 23, it's game over and your score is 0.
Stopping before 23, your total is recorded as your score.
If you reach 23, your score doubles to 46.

# Getting the Game

Clone this repository using git. Open a terminal console and navigate to the
root of the repository.

# Running the Game

CLI and GUI jar files of the game can be downloaded from the releases page.

Note that the game requires Java 8 in order to run.

The game can also be compiled and run using gradle as described below.

Note that gradle does not need to be installed prior to running the commands
below.

# Special Rolls:

1. Three of a kind: 3 identical dice (bonus 15)
2. Consecutive: dice values are consecutive regardless of their order (bonus 10)


## Linux

### CLI Version

```bash
./gradlew :cli:run
```

### Swing GUI Version

```bash
./gradlew :gui:run
```

## Windows

### CLI Version

```
gradlew :cli:run
```

### Swing GUI Version

```
gradlew :gui:run
```

# Running the Tests

Running the tests requires Java JDK 8 or higher to be installed and available
on your `PATH`.

## Linux

```bash
./gradlew check 
```
## Windows

```
gradlew check
```
