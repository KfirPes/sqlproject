package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NewPositionView extends Application{

	@Override
    public void start(Stage primaryStage) {
        // Creating the GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Label positionLabel = new Label("Enter the name of the position: ");
        grid.add(positionLabel, 0, 1);
        TextField positionName = new TextField();
        grid.add(positionName, 1, 1);

        // Adding Save and Cancel Buttons
        Button btnSave = new Button("Save");
        Button btnCancel = new Button("Cancel");
        grid.add(btnSave, 0, 2);
        grid.add(btnCancel, 1, 2);

        // Handling Save Button Action
        btnSave.setOnAction(e -> {
            String name = positionName.getText();
            System.out.println("Saving position: name: "+ name);
            // Here you can add code to save these details to a database or another storage
        });

        // Handling Cancel Button Action
        btnCancel.setOnAction(e -> primaryStage.close());

        // Setting the scene and showing the stage
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setTitle("New Company Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
