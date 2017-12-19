
package main;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The SceneAccountGUI program creates a screen for all accounts in which user can modify, delete, add accounts
 *
 * @author Group 3
 * @version 1.1
 * @since 2017-10-12
 */

public class SceneAccountGUI extends Application {
    private static final int DEFAULT_PADDING = 10;
    private static final int DEFAULT_FONT_SIZE = 15;

    private User user = new User("User", "Password");
    private ObservableList<Account> accountList = FXCollections.observableArrayList();
    private TextField userTextField;
    private TextField appTextField;
    private PasswordField passwordField;
    private CheckBox autoPassword;
    private CheckBox selfPassword;
    private TextField passwordLengthTextField;
    private Button addButton = new Button("Add Account");
    private Button modifyButton = new Button("Modify Account");
    private Button deleteButton = new Button("Delete Account");
    private VBox addBox = new VBox();
    private VBox deleteBox = new VBox();
    private VBox modifyBox = new VBox();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SplitPane root = new SplitPane();
        VBox left = new VBox();
        VBox right = new VBox();
        autoPassword = new CheckBox();
        selfPassword = new CheckBox();
        root.getItems().addAll(left, right);
        root.setDividerPosition(0, 0.45);

        Scene scene = new Scene(root, 800, 800);

        right.setSpacing(5);
        right.setPadding(new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
        left.setSpacing(5);
        left.setPadding(new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));

        createButton(addButton, addBox);
        createButton(deleteButton, deleteBox);
        createButton(modifyButton, modifyBox);

        Label userName = new Label("User Name:");
        userName.setFont(Font.font(null, FontWeight.BOLD, DEFAULT_FONT_SIZE));
        userTextField = new TextField();

        autoPassword.setText("Automatically generate password");
        selfPassword.setText("Set password yourself");

        Label passwordLengthLabel = new Label("Password Length (must be longer than 8): ");
        passwordLengthTextField = new TextField();
        passwordLengthTextField.setEditable(false);
        passwordLengthTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                                                            String newValue) -> {
            if (!newValue.matches("\\d*")) {
                passwordLengthTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        Label password = new Label("Password: ");
        password.setFont(Font.font(null, FontWeight.BOLD, DEFAULT_FONT_SIZE));
        passwordField = new PasswordField();
        passwordField.setEditable(false);

        Label appName = new Label("Application:");
        appName.setFont(Font.font(null, FontWeight.BOLD, DEFAULT_FONT_SIZE));
        appTextField = new TextField();

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        vbox.getChildren().addAll(userName, userTextField, password, autoPassword, passwordLengthLabel, passwordLengthTextField, selfPassword, passwordField, appName, appTextField, addBox, deleteBox, modifyBox);

        left.getChildren().addAll(vbox);

        TableView<Account> tvAccount = createAccountTableView();


        right.getChildren().addAll(tvAccount);

        user.getPlainAccounts().forEach(account -> {
            accountList.add(account);
        });

        createPrimaryStage(primaryStage, scene);

        setOnActionForButtons(tvAccount);
    }

    private void createPrimaryStage(Stage primaryStage, Scene scene) {
        primaryStage.setTitle("Password Protector");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Account> createAccountTableView() {
        TableView<Account> tvAccount = new TableView<>(accountList);
        TableColumn<Account, String> userNameColumn = new TableColumn<>("User Name");
        TableColumn<Account, String> passwordColumn = new TableColumn<>("Password");
        TableColumn<Account, String> appNameColumn = new TableColumn<>("Application name");

        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        appNameColumn.setCellValueFactory(new PropertyValueFactory<>("Appname"));

        setUpTableViewAccount(tvAccount, userNameColumn, passwordColumn, appNameColumn);
        return tvAccount;
    }

    private void setOnActionForButtons(TableView<Account> tvAccount) {
        selfPassword.setOnAction(event ->
        {
            activateSelfPassword();
        });

        autoPassword.setOnAction(event ->
        {
            activateAutoPassword();
        });

        addButton.setOnAction(event -> {
            addAccountAction(autoPassword, selfPassword, userTextField, passwordField, appTextField);
        });

        deleteButton.setOnAction(event -> {
            deleteAccountAction(userTextField, passwordField, appTextField, tvAccount);
        });

        modifyButton.setOnAction(event -> {
            modifyAccountAction(autoPassword, selfPassword, userTextField, passwordField, appTextField, tvAccount);
        });

        tvAccount.setOnMouseClicked(event -> {
            selectOneTableItem(tvAccount);
        });
    }

    private void addAccountAction(CheckBox autoPassword, CheckBox selfPassword, TextField userTextField, PasswordField pwBox, TextField appTextField) {
        Account acc = createAccount(autoPassword, selfPassword);
        if (user.addAccount(acc)) {
            accountList.add(acc);
        } else {
            createErrorAlert("add", "Please complete all required fields and make sure account is not duplicate.");
        }
        resetTextFields(userTextField, passwordField, appTextField, passwordLengthTextField);
    }

    private void selectOneTableItem(TableView<Account> tvAccount) {
        Account selectedItem = tvAccount.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            userTextField.setText(selectedItem.getUsername());
            passwordField.setText(selectedItem.getPassword());
            appTextField.setText(selectedItem.getAppname());
        }
    }

    private void activateAutoPassword() {
        if (autoPassword.isSelected()) {
            passwordField.setEditable(false);
            passwordField.setText("");
            selfPassword.setSelected(false);
            passwordLengthTextField.setEditable(true);
        }
    }

    private void activateSelfPassword() {
        if (selfPassword.isSelected()) {
            passwordField.setEditable(true);
            autoPassword.setSelected(false);
        }
    }

    private void setUpTableViewAccount(TableView<Account> tvAccount, TableColumn<Account, String> userName, TableColumn<Account, String> password, TableColumn<Account, String> appName) {
        tvAccount.getColumns().addAll(userName, password, appName);
        tvAccount.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvAccount.setPrefWidth(300);
        tvAccount.setPrefHeight(700);
    }

    private void resetTextFields(TextField userName, TextField password, TextField appName, TextField passwordLength) {
        userName.setText("");
        password.setText("");
        appName.setText("");
        passwordLength.setText("");
        autoPassword.setSelected(false);
        selfPassword.setSelected(false);
    }


    private void modifyAccountAction(CheckBox autoPassword, CheckBox selfPw, TextField userTextField, PasswordField pwBox, TextField appTextField, TableView<Account> tvAccount) {
        Account modifiedItem = tvAccount.getSelectionModel().getSelectedItem();
        if (modifiedItem != null) {
            selfPw.setSelected(true);
            Account newItem = createAccount(autoPassword, selfPw);
            if (user.modifyAccount(modifiedItem.getId(), newItem) && alertMessage("modify")) {
                tvAccount.getItems().set(accountList.indexOf(modifiedItem), newItem);
            } else {
                createErrorAlert("modify", "You cannot modify this entry with a duplicated or blank account.");
            }
        } else {
            createErrorAlert("modify", "Please choose an account you want to modify.");
        }
        tvAccount.getSelectionModel().clearSelection();
        resetTextFields(userTextField, pwBox, appTextField, passwordLengthTextField);
    }

    private void deleteAccountAction(TextField userTextField, PasswordField pwBox, TextField appTextField, TableView<Account> tvAccount) {
        Account deletedItem = tvAccount.getSelectionModel().getSelectedItem();
        if (deletedItem != null) {
            if (alertMessage("delete")) {
                user.deleteAccount(deletedItem.getId());
                tvAccount.getItems().remove(deletedItem);
            }
        } else {
            createErrorAlert("delete", "Please choose an account you want to delete.");
        }
        tvAccount.getSelectionModel().clearSelection();
        resetTextFields(userTextField, pwBox, appTextField, passwordLengthTextField);
    }

    private Account createAccount(CheckBox autoPassword, CheckBox selfPassword) {
        Account newItem = new Account("", "", "");
        if (autoPassword.isSelected()) {
            int passwordLength = 20;
            String passwordLengthInput = passwordLengthTextField.getText();
            try {
                passwordLength = Integer.parseInt(passwordLengthInput);
            } catch (NumberFormatException e) {
                Logger.getLogger(SceneAccountGUI.class.getName()).log(Level.SEVERE, "Error parsing number", e);
            }

            if (passwordLength >= 8) {
                newItem = new Account(userTextField.getText().trim(), passwordLength, appTextField.getText().trim());
            }
        } else if (selfPassword.isSelected()) {
            newItem = new Account(userTextField.getText().trim(), passwordField.getText().trim(), appTextField.getText().trim());
        }
        return newItem;
    }


    private static void createButton(Button button, VBox vBox) {
        button.setPrefSize(200, 20);
        vBox.getChildren().addAll(button);
    }

    private boolean alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to permanently " + message + " this account?");
        Optional<ButtonType> action = alert.showAndWait();
        return action.isPresent() && action.get() == ButtonType.OK;
    }

    private void createErrorAlert(String message, String cause) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Fail to " + message + " this account! " + cause);
        alert.showAndWait();
    }


}