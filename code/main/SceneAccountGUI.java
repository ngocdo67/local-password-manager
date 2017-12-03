/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SceneAccountGUI extends Application {
	User user = new User("User", "Password");
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
        CheckBox autoPw = new CheckBox();
        CheckBox selfPw = new CheckBox();
        root.getItems().addAll(left, right);
        root.setDividerPosition(0, 0.45);

        right.setSpacing(5);
        right.setPadding(new Insets(10, 0, 0, 10));
        scene = new Scene(root, 800, 800);

        Button addButton = new Button("Add account");
        addButton.setPrefSize(100, 20);

        Label userName = new Label("User Name:");
        TextField userTextField = new TextField();

        autoPw.setText("Automatically generate password");
        selfPw.setText("Set password yourself");

        Label password = new Label("Password: ");
        PasswordField pwBox = new PasswordField();
        pwBox.setEditable(false);

        Label appName = new Label("Application:");
        TextField appTextField = new TextField();

        VBox vbox = new VBox();
        vbox.setSpacing(30);

        vbox.getChildren().addAll(userName, userTextField, password, autoPw, selfPw, pwBox, appName, appTextField, addButton);


        left.getChildren().addAll(vbox);

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
        tvAccount.setPrefHeight(700);

        right.getChildren().addAll(tvAccount);

        user.getHashMap().forEach((key, value) -> {
            Account account = new Account ((EncryptedAccount)value, user.getKeyPass());
            accountList.add(account);
        });

        primaryStage.setTitle("Password Protector");
        primaryStage.setScene(scene);
        primaryStage.show();

        selfPw.setOnAction(event ->
        {
            if (selfPw.isSelected()) {
                pwBox.setEditable(true);
                autoPw.setSelected(false);
            }
        });

        autoPw.setOnAction(event ->
        {
            if (autoPw.isSelected()) {
                pwBox.setEditable(false);
                pwBox.setText("");
                selfPw.setSelected(false);
            }
        });

        addButton.setOnAction(event ->
        {
            if (autoPw.isSelected()) {
                Account acc = new Account(userTextField.getText(), 20, appTextField.getText());
                if (user.addAccount(acc))
                    accountList.add(acc);
            } else if (selfPw.isSelected()) {
                Account acc = new Account(userTextField.getText(), pwBox.getText(), appTextField.getText());
                if (user.addAccount(acc))
                    accountList.add(acc);
            }
        });
    }

   /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}