package dao;

import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO {

    protected Connection conn;


    public ClientDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertRow(Client client) throws SQLException {

        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO client (surname, name," +
                    " birthday, registration_date, password, email) VALUES(?,?,?,?,?,?) ");
            statement.setString(1, client.getSurname());
            statement.setString(2, client.getName());
            statement.setDate(3, client.getBirthday());
            statement.setDate(4, client.getRegistration_date());
            statement.setString(5, client.getPassword());
            statement.setString(6, client.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Replace the client in table purchase and rate the client by the deleted profile client (client_id=-1)
     * Delete the client's cart
     * Delete the client
     * @param client
     */
    public void deleteRow(Client client){
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE purchase SET client_id = -1 WHERE client_id =" + client.getClient_id());
            statement.executeUpdate();
            statement = conn.prepareStatement("UPDATE rate SET client_id = -1 WHERE client_id =" + client.getClient_id());
            statement.executeUpdate();
            statement = conn.prepareStatement("DELETE FROM cart WHERE client_id = " + client.getClient_id());
            statement.executeUpdate();
            statement = conn.prepareStatement("DELETE FROM client WHERE client_id =" + client.getClient_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateRow(Client client){
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE client SET surname = ?, name = ?, birthday = ?, registration_date = ?, password = ?, email = ? WHERE client_id =" + client.getClient_id());

            statement.setString(1, client.getSurname());
            statement.setString(2, client.getName());
            statement.setDate(3, client.getBirthday());
            statement.setDate(4, client.getRegistration_date());
            statement.setString(5, client.getPassword());
            statement.setString(6, client.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Client selectRow(int client_id){
        Client client = new Client();
        String sql = "SELECT * FROM client WHERE client_id = " + client_id;

        try{
            ResultSet result = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);

            if(result.first()){
                client = new Client();
                client.setClient_id(client_id);
                client.setSurname(result.getString("surname"));
                client.setName(result.getString("name"));
                client.setBirthday(result.getDate("birthday"));
                client.setRegistration_date(result.getDate("registration_date"));
                client.setPassword(result.getString("password"));
                client.setEmail(result.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public ArrayList<Client> getAllRows(){
        ArrayList<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client ORDER BY client.surname, client.name";

        try{
            ResultSet result = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);


            while (result.next()){
                Client client = new Client();

                client.setSurname(result.getString("surname"));
                client.setName(result.getString("name"));
                client.setClient_id(result.getInt("client_id"));
                client.setBirthday(result.getDate("birthday"));
                client.setRegistration_date(result.getDate("registration_date"));
                client.setPassword(result.getString("password"));
                client.setEmail(result.getString("email"));

                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    /**
     * Return the number of registered clients minus 2 (admin and delete profile)
     * @return nbClient
     */
    public int getNbClient(){
        int nbClient = 0;
        String sql = "SELECT count(client_id)-2 FROM client";

        try{
            ResultSet result = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);

            if(result.first()){
                nbClient = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nbClient;
    }

    public Client getByEmail(String email){
        String sql = "SELECT * FROM client WHERE email = '" + email + "'";
        Client client = new Client();
        try{
            ResultSet result = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);

            if(result.first()){
                client = new Client();

                client.setSurname(result.getString("surname"));
                client.setName(result.getString("name"));
                client.setClient_id(result.getInt("client_id"));
                client.setBirthday(result.getDate("birthday"));
                client.setRegistration_date(result.getDate("registration_date"));
                client.setPassword(result.getString("password"));
                client.setEmail(result.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

}
