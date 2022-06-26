package MainPart;

import Objects.City;
import Objects.User;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Stack;

public class DataBaseConnector implements Serializable {
    Connection connection;
    Statement statement;
    Statement statement1;
    Model model;

    public synchronized void CreateCollection(Model model) {

        this.model = model;
        model.cities = new Stack<>();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(model.dataBaseUrl, model.dataBaseUser, model.dataBasePassword);
            //connection.setAutoCommit(false);
            statement = connection.createStatement();

            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM CITY");
                while (resultSet.next()) {
                    City city = new City(resultSet.getInt("id"), resultSet.getString("Name"),
                            resultSet.getFloat("CoordinateX"), resultSet.getFloat("CoordinateY"),
                            resultSet.getInt("Area"), resultSet.getInt("Population"), resultSet.getFloat("metersabovesealevel"),
                            resultSet.getInt("Timezone"), resultSet.getInt("Capital"), StringManipulation.government(resultSet.getString("government")),
                            resultSet.getString("Governor"), resultSet.getString("author"));
                    model.numberOfObjectsInStack++;
                    model.cities.push(city);
                    if (model.id <= resultSet.getInt("id")){
                        model.id = resultSet.getInt("id");
                    }

                    //System.out.println(model.id);

                }
                statement1 = connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM Users");
                while (resultSet1.next()){
                    model.userList.put(resultSet1.getString("login"), resultSet1.getString("password"));


                }
            } finally {
                statement.close();
                connection.close();

            }
            System.out.println("Все зашибись");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void addUserToDataBase(User user){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(model.dataBaseUrl, model.dataBaseUser, model.dataBasePassword);
            statement = connection.createStatement();
            try {
                //System.out.println("INSERT INTO Users (login, password) VALUES (\'" + user.name + "\', \'" + user.password + "\');");
                statement.executeUpdate("INSERT INTO Users (login, password) VALUES (\'" + user.name + "\', \'" + user.password + "\');");
            }finally {
                statement.close();
                connection.close();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Данный логин уже занят");
        }
    }

    public synchronized void saveToDataBase(City city){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(model.dataBaseUrl, model.dataBaseUser, model.dataBasePassword);
            statement = connection.createStatement();
            try {
                int capital;
                if (city.getCapital() == null){
                    capital = -1;
                }else if (city.getCapital()){
                    capital = 1;
                }else {
                    capital = 0;
                }
                statement.executeUpdate("INSERT INTO City (ID,NAME,CoordinateX,CoordinateY,AREA,POPULATION,MetersAboveSeaLevel,Timezone," +
                        "Capital,Government, Governor, Author) VALUES (nextval('id'),\'" + city.getName() +"\', " + city.getCoordinates().getX() +
                        ", " + city.getCoordinates().getY() + ", " + city.getArea() + ", " + city.getPopulation() + ", " + city.getMetersAboveSeaLevel() +
                        ", " + city.getTimezone() + ", " + capital + ", \'" + String.valueOf(city.getGoverment()) + "\', \'" + city.getGoverner().getName() +
                        "\', \'" + city.getAuthor() + "\');");
                System.out.println("Объект сохранен");
            }finally {
                statement.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteFromDataBase(City... city){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(model.dataBaseUrl, model.dataBaseUser, model.dataBasePassword);
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            try {
                for (int i = 0; i < city.length; i++){
                    statement.executeUpdate("DELETE FROM City WHERE id = " + city[i].getId() + ";");
                }
                connection.commit();
            }finally {
                statement.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateIntoDataBase(City city){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(model.dataBaseUrl, model.dataBaseUser, model.dataBasePassword);
            statement = connection.createStatement();
            try {
                int capital;
                if (city.getCapital() == null){
                    capital = -1;
                }else if (city.getCapital()){
                    capital = 1;
                }else {
                    capital = 0;
                }
                statement.executeUpdate("UPDATE City SET NAME = \'" + city.getName() + "\', CoordinateX = " + city.getCoordinates().getX() +
                        ", CoordinateY = " + city.getCoordinates().getY() + ", AREA = " + city.getArea() + ", POPULATION = " + city.getPopulation() +
                        ", MetersAboveSeaLevel = " + city.getMetersAboveSeaLevel() + ", Timezone = " + city.getTimezone() + ", Capital = " + capital +
                        ",Government = \'" + String.valueOf(city.getGoverment()) + "\', Governor = \'" + city.getGoverner().getName() + "\', Author = \'" + city.getAuthor() + "\' WHERE id = " + city.getId() + ";");
            }finally {
                statement.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
