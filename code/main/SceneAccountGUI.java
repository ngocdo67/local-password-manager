/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Collection;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SceneAccountGUI extends Application {
	User user = new User();
    Stage theStage;
    Scene scene;
    SplitPane root;
    ObservableList<Account> accountList = FXCollections.observableArrayList();; 
    
    @Override
    public void start(Stage primaryStage) {
        theStage = primaryStage;
        root = new SplitPane();
        VBox left = new VBox();

        VBox right = new VBox();
        right.setSpacing(5);
        right.setPadding(new Insets(10, 0, 0, 10));
        root.getItems().addAll(left, right);
        root.setDividerPosition(0, 0.5);
        scene = new Scene(root, 800, 800);

//        Button addButton = new Button("Add account");
//        addButton.setPrefSize(100, 20);
//
//        Label userName = new Label("User Name:");
//        TextField userTextField = new TextField();
//        hbox.getChildren().addAll(addButton);
//        root.setTop(hbox);

        TableView<Account> tvAccount;
        tvAccount = new TableView<Account>(accountList);

        TableColumn<Account, String> uName = new TableColumn<>("User Name");
        uName.setCellValueFactory(new PropertyValueFactory<>("Username"));

        TableColumn<Account, String> pw = new TableColumn<>("Password");
        pw.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<Account, String> aName = new TableColumn<>("Application name");
        aName.setCellValueFactory(new PropertyValueFactory<>("Appname"));

        tvAccount.getColumns().addAll(uName, pw, aName);
        tvAccount.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvAccount.setPrefWidth(350);
        //tvAccount.setPrefHeight(350);

        right.getChildren().addAll(tvAccount);

        primaryStage.setTitle("Password Protector");
        primaryStage.setScene(scene);
        primaryStage.show();

//        addButton.setOnAction(event ->
//        {
//    	        SceneAddAccountGUI sceneAddAccount = new SceneAddAccountGUI();
//    	        sceneAddAccount.start(primaryStage);
//        });
    }

   /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}