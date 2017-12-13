/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SceneAccountGUI extends Application {
    User user = new User("User", "Password");
    Stage theStage;
    Scene scene;
    SplitPane root;
    ObservableList<Account> accountList = FXCollections.observableArrayList();

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
        Label addDuplicate = new Label();
        VBox addBox = new VBox();
        addBox.getChildren().addAll(addButton, addDuplicate);

        Button deleteButton = new Button("Delete Account");
        deleteButton.setPrefSize(100, 20);
        Label deleteErr = new Label();
        VBox deleteBox = new VBox();
        deleteBox.getChildren().addAll(deleteButton, deleteErr);

        Button modifyButton = new Button("Modify Account");
        modifyButton.setPrefSize(100, 20);
        Label modifyErr = new Label();
        VBox modifyBox = new VBox();
        modifyBox.getChildren().addAll(modifyButton, modifyErr);

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


        vbox.getChildren().addAll(userName, userTextField, password, autoPw, selfPw, pwBox, appName, appTextField, addBox, deleteBox, modifyBox);


        left.getChildren().addAll(vbox);

        TableView<Account> tvAccount = new TableView<Account>(accountList);

        TableColumn<Account, String> uName = new TableColumn<>("User Name");
        uName.setCellValueFactory(new PropertyValueFactory<>("Username"));

        TableColumn<Account, String> pw = new TableColumn<>("Password");
        pw.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<Account, String> aName = new TableColumn<>("Application name");
        aName.setCellValueFactory(new PropertyValueFactory<>("Appname"));

        tvAccount.getColumns().addAll(uName, pw, aName);
        tvAccount.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvAccount.setPrefWidth(300);
        tvAccount.setPrefHeight(700);

        right.getChildren().addAll(tvAccount);

        user.getHashMap().forEach((key, value) -> {
//            Account account = new Account ((EncryptedAccount)value, user.getKeyPass());
            Account account = ((EncryptedAccount) value).decryptAccount(user.getKeyPass());
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

        addButton.setOnAction(event -> {
            Account acc = new Account("", "", "");
            if (autoPw.isSelected())
                acc = new Account(userTextField.getText().trim(), 20, appTextField.getText().trim());
            else if (selfPw.isSelected())
                acc = new Account(userTextField.getText().trim(), pwBox.getText().trim(), appTextField.getText().trim());
            if (user.addAccount(acc)) {
                accountList.add(acc);
                addDuplicate.setText("");
            } else
                addDuplicate.setText("  You cannot add a duplicated or blank account");
            userTextField.setText("");
            pwBox.setText("");
            appTextField.setText("");
            textFieldsToVoid(userTextField, pwBox, appTextField);
            deleteErr.setText("");
            modifyErr.setText("");
        });


        deleteButton.setOnAction(event -> {
            Account deletedItem = tvAccount.getSelectionModel().getSelectedItem();
            if (deletedItem != null) {
                user.deleteAccount(deletedItem.getId());
                tvAccount.getItems().remove(deletedItem);
                deleteErr.setText("");
            } else
                deleteErr.setText("Please choose an account you want to delete.");
            tvAccount.getSelectionModel().clearSelection();
            textFieldsToVoid(userTextField, pwBox, appTextField);
            modifyErr.setText("");
            addDuplicate.setText("");
        });

        modifyButton.setOnAction(event -> {
            Account modifiedItem = tvAccount.getSelectionModel().getSelectedItem();
            Account newItem = new Account("", "", "");
            if (modifiedItem != null) {
                if (autoPw.isSelected())
                    newItem = new Account(userTextField.getText().trim(), 20, appTextField.getText().trim());
                else if (selfPw.isSelected())
                    newItem = new Account(userTextField.getText().trim(), pwBox.getText().trim(), appTextField.getText().trim());
                if (user.modifyAccount(modifiedItem.getId(), newItem)) {
                    tvAccount.getItems().set(accountList.indexOf(modifiedItem), newItem);
                    modifyErr.setText("");
                } else
                    modifyErr.setText("  You cannot modify this entry with a duplicated or blank account");
            } else
                modifyErr.setText("Please choose an account you want to modify.");
            tvAccount.getSelectionModel().clearSelection();
            textFieldsToVoid(userTextField, pwBox, appTextField);
            addDuplicate.setText("");
            deleteErr.setText("");
        });

        tvAccount.setOnMouseClicked(event -> {
            Account selectedItem = tvAccount.getSelectionModel().getSelectedItem();
            userTextField.setText(selectedItem.getUsername());
            pwBox.setText(selectedItem.getPassword());
            appTextField.setText(selectedItem.getAppname());
        });
    }

    public void textFieldsToVoid(TextField userName, TextField pw, TextField appName) {
        userName.setText("");
        pw.setText("");
        appName.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
