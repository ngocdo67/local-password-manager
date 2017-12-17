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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;


public class SceneAccountGUI extends Application {

    private TextField userTextField;
    private TextField appTextField;
    private PasswordField pwBox;
    private CheckBox autoPw;
    private CheckBox selfPw;

    private User user = new User("User", "Password");
    private SplitPane root;
    private ObservableList<Account> accountList = FXCollections.observableArrayList();
    private Button addButton = new Button("Add Account");
    private Button modifyButton = new Button ("Modify Account");
    private Button deleteButton = new Button ("Delete Account");
    private Label addDuplicate = new Label();
    private VBox addBox = new VBox();
    private Label deleteErr = new Label();
    private VBox deleteBox = new VBox();
    private Label modifyErr = new Label();
    private VBox modifyBox = new VBox();

    @Override
    public void start(Stage primaryStage) {
        Scene scene;
        SplitPane root;
        root = new SplitPane();
        VBox left = new VBox();
        VBox right = new VBox();
        autoPw = new CheckBox();
        selfPw = new CheckBox();
        root.getItems().addAll(left, right);
        root.setDividerPosition(0, 0.45);

        right.setSpacing(5);
        right.setPadding(new Insets(10, 10, 10, 10));
        left.setSpacing(5);
        left.setPadding(new Insets(10, 10, 10, 10));
        scene = new Scene(root, 800, 800);

        createButton(addButton, addDuplicate, addBox);
        createButton(deleteButton, deleteErr, deleteBox);
        createButton(modifyButton, modifyErr, modifyBox);

        Label userName = new Label("User Name:");
        userTextField = new TextField();

        autoPw.setText("Automatically generate password");
        selfPw.setText("Set password yourself");

        Label password = new Label("Password: ");
        pwBox = new PasswordField();
        pwBox.setEditable(false);

        Label appName = new Label("Application:");
        appTextField = new TextField();

        VBox vbox = new VBox();
        vbox.setSpacing(30);

        vbox.getChildren().addAll(userName, userTextField, password, autoPw, selfPw, pwBox, appName, appTextField, addBox, deleteBox, modifyBox);

        left.getChildren().addAll(vbox);

        TableView<Account> tvAccount = new TableView<>(accountList);
        TableColumn<Account, String> uName = new TableColumn<>("User Name");
        TableColumn<Account, String> pw = new TableColumn<>("Password");
        TableColumn<Account, String> aName = new TableColumn<>("Application name");

        uName.setCellValueFactory(new PropertyValueFactory<>("Username"));
        pw.setCellValueFactory(new PropertyValueFactory<>("password"));
        aName.setCellValueFactory(new PropertyValueFactory<>("Appname"));

        tvAccount.getColumns().addAll(uName, pw, aName);
        tvAccount.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvAccount.setPrefWidth(300);
        tvAccount.setPrefHeight(700);

        right.getChildren().addAll(tvAccount);


        user.getPlainAccounts().forEach(account -> {
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
            addAccountAction(autoPw, selfPw, addDuplicate, deleteErr, modifyErr, userTextField, pwBox, appTextField);
        });

        deleteButton.setOnAction(event -> {
            deleteAccountAction(addDuplicate, deleteErr, modifyErr, userTextField, pwBox, appTextField, tvAccount);
        });

        modifyButton.setOnAction(event -> {
            modifyAccountAction(autoPw, selfPw, addDuplicate, deleteErr, modifyErr, userTextField, pwBox, appTextField, tvAccount);
        });

        tvAccount.setOnMouseClicked(event -> {
            Account selectedItem = tvAccount.getSelectionModel().getSelectedItem();
            userTextField.setText(selectedItem.getUsername());
            pwBox.setText(selectedItem.getPassword());
            appTextField.setText(selectedItem.getAppname());
        });
    }

    private void inputToVoid(TextField userName, TextField pw, TextField appName) {
        userName.setText("");
        pw.setText("");
        appName.setText("");
        autoPw.setSelected(false);
        selfPw.setSelected(false);
    }

    private Account pwOption (CheckBox autoPw, CheckBox selfPw) {
        Account newItem;
        if (autoPw.isSelected())
            newItem = new Account(userTextField.getText().trim(), 20, appTextField.getText().trim());
        else if (selfPw.isSelected())
            newItem = new Account(userTextField.getText().trim(), pwBox.getText().trim(), appTextField.getText().trim());
        else
            newItem = new Account ("","","");
        return newItem;
    }

    private boolean alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to permanently " + message + " this account?");
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;

    }
    private void modifyAccountAction(CheckBox autoPw, CheckBox selfPw, Label addDuplicate, Label deleteErr, Label modifyErr, TextField userTextField, PasswordField pwBox, TextField appTextField, TableView<Account> tvAccount) {
            selfPw.setSelected(true);
            Account modifiedItem  = tvAccount.getSelectionModel().getSelectedItem();
            if (modifiedItem != null) {
                Account newItem = pwOption(autoPw, selfPw);
                if (user.modifyAccount(modifiedItem.getId(), newItem)) {
                    if (alertMessage("modify")) {
                        tvAccount.getItems().set(accountList.indexOf(modifiedItem), newItem);
                        modifyErr.setText("");
                    }
                }
                else {
                    modifyErr.setText("You cannot modify this entry with a duplicated or blank account. If you omit the password options, the account is considered blank.");
                    modifyErr.setWrapText(true);
                }
            }
            else
                modifyErr.setText("Please choose an account you want to modify.");
            tvAccount.getSelectionModel().clearSelection();
            inputToVoid(userTextField,pwBox,appTextField);
            addDuplicate.setText("");
            deleteErr.setText("");
    }

    private void deleteAccountAction(Label addDuplicate, Label deleteErr, Label modifyErr, TextField userTextField, PasswordField pwBox, TextField appTextField, TableView<Account> tvAccount) {
        Account deletedItem = tvAccount.getSelectionModel().getSelectedItem();
        if (deletedItem != null) {
            if (alertMessage("delete")) {
                user.deleteAccount(deletedItem.getId());
                tvAccount.getItems().remove(deletedItem);
                deleteErr.setText("");
            }
        } else
            deleteErr.setText("Please choose an account you want to delete.");
        tvAccount.getSelectionModel().clearSelection();
        inputToVoid(userTextField, pwBox, appTextField);
        modifyErr.setText("");
        addDuplicate.setText("");
    }

    private void addAccountAction(CheckBox autoPw, CheckBox selfPw, Label addDuplicate, Label deleteErr, Label modifyErr, TextField userTextField, PasswordField pwBox, TextField appTextField) {
        Account acc = pwOption(autoPw, selfPw);
        if (user.addAccount(acc)) {
            accountList.add(acc);
            addDuplicate.setText("");
        }
        else
            addDuplicate.setText("You cannot add a duplicated or blank account");
        userTextField.setText("");
        pwBox.setText("");
        appTextField.setText("");
        inputToVoid(userTextField,pwBox,appTextField);
        deleteErr.setText("");
        modifyErr.setText("");
    }

    public void textFieldsToVoid(TextField userName, TextField pw, TextField appName) {
        userName.setText("");
        pw.setText("");
        appName.setText("");
    }

    public static void createButton(Button button, Label label, VBox vBox) {
        button.setPrefSize(200, 20);
        vBox.getChildren().addAll(button, label);
    }



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}