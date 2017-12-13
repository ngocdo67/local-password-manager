/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ha
 */
public class PasswordProtectorGUI extends Application {
	private LogIn login = new LogIn();

    @Override
    public void start(Stage primaryStage) {

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(root, 800, 800);

        Text scenetitle = new Text("Please login: ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label userName = new Label("User Name:");
        TextField userTextField = new TextField();
        Label pw = new Label("Password: ");
        PasswordField pwBox = new PasswordField();

        root.add(scenetitle, 0, 0, 2, 1);
        root.add(userName, 0, 1);
        root.add(userTextField, 1, 1);
        root.add(pw, 0, 2);
        root.add(pwBox, 1, 2);

        root.setGridLinesVisible(false);

        Button signInButton = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(signInButton);
        root.add(hbBtn, 1, 4);
        
        final Text actiontarget = new Text();
        root.add(actiontarget, 1, 6);
        
        primaryStage.setTitle("Pa$$w0rd");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        signInButton.setOnAction(event ->
        {
            SceneAccountGUI sceneAccount = new SceneAccountGUI();
            if (login.verifyNameAndPassword(userTextField.getText(),pwBox.getText()))
                sceneAccount.start(primaryStage);
            else
                actiontarget.setText("Wrong user name or password");
        }); 
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}