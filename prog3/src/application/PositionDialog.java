package application;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class PositionDialog extends Stage{
	private TextField positionName;
	private PositionCallback callback;
	
	public PositionDialog(Stage parent, PositionCallback callback) {
		this.callback = callback;
    	initModality(Modality.APPLICATION_MODAL);
        initOwner(parent);

        setTitle("Add Position");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        grid.add(new Label("Enter the name of the position: "), 0, 1);
        positionName = new TextField();
        grid.add(positionName, 1, 1);

        // Adding Save and Cancel Buttons
        Button btnSave = new Button("Save");
        Button btnCancel = new Button("Cancel");
        grid.add(btnSave, 0, 2);
        grid.add(btnCancel, 1, 2);


        btnSave.setOnAction(e -> addPosition());
        btnCancel.setOnAction(e -> close());

        // Setting the scene and showing the stage
        Scene scene = new Scene(grid, 300, 200);
        setScene(scene);
    }

	private void addPosition() {
        possition newPosition = new possition(positionName.getText(), true);
        List<Integer> possitionsIds = DatabaseConnection.insertPossitions(Arrays.asList(newPosition));
        newPosition.setId(possitionsIds.get(0));
        if (callback != null) {
            callback.onPositionAdded(newPosition); // Call the callback with the new ID
            close();
        }
	}
}
