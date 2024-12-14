package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBTrainer {

    public void printAllTrainers(){
        final String query = "SELECT * FROM trainer";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Trainer id: " + resultSet.getInt("trainer_ID") + " ,first name: "
                        + resultSet.getString("first_name")
                        + " ,last name: "
                        + resultSet.getString("last_name"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTrainer (String firstName, String lastName){
        final String insert = "INSERT INTO trainer (first_name, last_name) values (?, ?)";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteTrainer(int id) {
        final String delete = "DELETE FROM trainer WHERE trainer_ID = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTrainer(int id, String firstName, String lastName) {
        final String update = "UPDATE trainer SET first_name = ?, last_name = ? WHERE trainer_ID = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, id);
            preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printAllTrainersByPokemons(){
        final String query = "SELECT H.first_name, H.last_name, COUNT(P.pokemon_ID) AS total \n" +
                "FROM trainer H \n" +
                "LEFT JOIN pokemon P on (H.trainer_ID=P.trainer_ID) \n" +
                "GROUP BY H.first_name \n" +
                "ORDER BY total DESC;";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Trainer "
                        + resultSet.getString("first_name")
                        + " "
                        + resultSet.getString("last_name")
                        + " have "
                        + resultSet.getString("total")
                        + " pokemons.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
