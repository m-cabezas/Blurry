package controller;

import connection.Connect;
import dao.ClientDAO;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Client;

import java.util.ArrayList;

public class ClientListController {
    @FXML
    private VBox clientBox;

    private ClientDAO clientDAO;
    private ArrayList<Client> clients;

    public ClientListController() {
        clientDAO = new ClientDAO(Connect.getInstance());
        clients = new ArrayList<>();
    }

    @FXML
    public void initialize(){
        clients = clientDAO.getAllRows();

        /* Creating a pane for each client */
        for(Client client : clients){
            HBox oneClientBox = new HBox();
            oneClientBox.getStyleClass().add("border");
            oneClientBox.setAlignment(Pos.CENTER);
            oneClientBox.setPrefHeight(100);
            oneClientBox.setSpacing(10);

            Font font = new Font("verdana", 20);
            Label name = new Label(client.getName());
            name.setTextFill(Color.WHITE);
            name.setFont(font);
            Label surname = new Label(client.getSurname());
            surname.setTextFill(Color.WHITE);
            surname.setFont(font);
            Label email = new Label(client.getEmail());
            email.setTextFill(Color.WHITE);
            email.setFont(font);

            oneClientBox.getChildren().add(name);
            oneClientBox.getChildren().add(surname);
            oneClientBox.getChildren().add(email);

            clientBox.getChildren().add(oneClientBox);
        }
    }
}
