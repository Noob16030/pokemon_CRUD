package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBPokemon {

    public void printAllPokemons(){
        final String query = "SELECT * FROM pokemon";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Pokemon id: " + resultSet.getInt("pokemon_ID") + " ,name: "
                        + resultSet.getString("pokemon_name")
                        + " ,trainer id: "
                        + resultSet.getString("trainer_ID"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addPokemon (String pokemonName){
        final String insert = "INSERT INTO pokemon (pokemon_name, trainer_ID) values (?, NULL)";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, pokemonName);
            preparedStatement.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deletePokemon(int id) {
        final String delete = "DELETE FROM pokemon WHERE pokemon_ID = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePokemonName(int id, String pokemonName) {
        final String update = "UPDATE pokemon SET pokemon_name = ? WHERE pokemon_ID = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, pokemonName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePokemonOwner(int pokemonId, int trainerID) {
        final String update = "UPDATE pokemon SET trainer_ID = ? WHERE pokemon_ID = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, trainerID);
            preparedStatement.setInt(2, pokemonId);
            preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printAllPokemonsOfThisTrainer(String firstName, String lastName){
        final String firstQuery = "SELECT trainer_ID FROM trainer where first_name = ? AND last_name = ?";
        final String secondQuery = "SELECT * FROM pokemon where trainer_ID = ?";
        int trainerID = 0;

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(firstQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()){
                while (resultSet.next()) {
                    trainerID = resultSet.getInt("trainer_ID");
                }
            } else {
                System.out.println("Trainer with this name does not exist!");
                return;
            }

            final PreparedStatement preparedNextStatement = connection.prepareStatement(secondQuery);
            preparedNextStatement.setInt(1, trainerID);
            final ResultSet resultNextSet = preparedNextStatement.executeQuery();
            System.out.println("Pokemons of " + firstName + " " + lastName + ":");
            while (resultNextSet.next()) {
                System.out.println("Pokemon name: " + resultNextSet.getString("pokemon_name"));}
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printAllPokemonsWithoutTrainer(){
        final String query = "SELECT * FROM pokemon WHERE trainer_ID IS NULL";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("All pokemons was already catched.");
                return;
            }

            while (resultSet.next()) {
                System.out.println("Pokemon "
                        + resultSet.getString("pokemon_name")
                        + " is free.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void catchPokemon(String firstName, String lastName, String pokemonName){
        final String firstQuery = "SELECT trainer_ID FROM trainer where first_name = ? AND last_name = ?";
        final String secondQuery = "SELECT * FROM pokemon where pokemon_name = ? AND trainer_ID IS NULL";
        int trainerID = 0;
        int pokemonID = 0;

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(firstQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()){
                while (resultSet.next()) {
                    trainerID = resultSet.getInt("trainer_ID");
                }
            } else {
                System.out.println("Trainer with this name does not exist!");
                return;
            }

            final PreparedStatement preparedNextStatement = connection.prepareStatement(secondQuery);
            preparedNextStatement.setString(1, pokemonName);
            final ResultSet resultNextSet = preparedNextStatement.executeQuery();

            if (resultNextSet.isBeforeFirst()){
                while (resultNextSet.next()) {
                    pokemonID = resultNextSet.getInt("pokemon_ID");
                }
            } else {
                System.out.println("Pokemon with this name does not exist or is not free!");
                return;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        updatePokemonOwner(pokemonID, trainerID);
    }
}
