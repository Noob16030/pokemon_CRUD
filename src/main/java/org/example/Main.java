package org.example;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBTrainer trainer = new DBTrainer();
        DBPokemon pokemon = new DBPokemon();

        Scanner scannerOne = new Scanner(System.in);
        Scanner scannerTwo = new Scanner(System.in);

        String inputFirstName;
        String inputLastName;
        String inputPokemonName;
        int inputInt = -1;

        System.out.println("Welcome in my small pokemon manager!");
        while (inputInt != 9) {
            System.out.println("Choose number of action.");
            System.out.println("1. Print all Pokemons");
            System.out.println("2. Print all Trainers");
            System.out.println("3. Print all Pokemons of one specific trainer");
            System.out.println("4. Print all Trainers by pokemons count");
            System.out.println("5. Print all free Pokemons");
            System.out.println("6. Catch Pokemon by trainer (recommended using number 5 first)");
            System.out.println("9. Exit");

            try {
                inputInt = scannerOne.nextInt();
            } catch (InputMismatchException a) {
                System.out.println("Wrong input!");
                scannerOne.next();
                inputInt = -1;
            }

            switch (inputInt) {
                case -1:
                    break;
                case 1:
                    pokemon.printAllPokemons();
                    break;
                case 2:
                    trainer.printAllTrainers();
                    break;
                case 3:
                    System.out.print("Enter First Name of trainer: ");
                    inputFirstName = scannerTwo.nextLine();
                    System.out.print("Enter his Last Name: ");
                    inputLastName = scannerTwo.nextLine();
                    pokemon.printAllPokemonsOfThisTrainer(inputFirstName, inputLastName);
                    break;
                case 4:
                    trainer.printAllTrainersByPokemons();
                    break;
                case 5:
                    pokemon.printAllPokemonsWithoutTrainer();
                    break;
                case 6:
                    System.out.print("Enter First Name of trainer: ");
                    inputFirstName = scannerTwo.nextLine();
                    System.out.print("Enter his Last Name: ");
                    inputLastName = scannerTwo.nextLine();
                    System.out.print("Enter which pokemon has to be catched: ");
                    inputPokemonName = scannerTwo.nextLine();
                    pokemon.catchPokemon(inputFirstName, inputLastName, inputPokemonName);
                    break;
                case 9:
                    System.out.println("Goodbye!");
            }
        }



    }
}