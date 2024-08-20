package application;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CompanyDialog extends Stage{

	private TextField nameTextField;
	private CompanyCallback callback;
	
	public CompanyDialog(Stage parent, Integer companyId, String action, CompanyCallback callback) {
		this.callback = callback;
    	initModality(Modality.APPLICATION_MODAL);
        initOwner(parent);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        setTitle("Add Company");
        
        grid.add(new Label("Company Name:"), 0, 1);
        nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);

        // Adding Save and Cancel Buttons
        Button btnSave = new Button("Save");
        Button btnCancel = new Button("Cancel");
        if(action == "NEW") {
        	btnSave.setOnAction(e -> addCompany());
        }else {
        	btnSave.setOnAction(e -> updateCompany(companyId));
        }
        
        btnCancel.setOnAction(e -> close());
        grid.add(btnSave, 0, 2);
        grid.add(btnCancel, 1, 2);
        


        Scene scene = new Scene(grid, 300, 200);
        setScene(scene);

	}

	private void addCompany() {
		if(nameTextField.getText() != null) {
			int companyId = DatabaseConnection.insertCompany(nameTextField.getText());
			if (callback != null) {
	            callback.onCompanyAdded(new company(companyId, nameTextField.getText())); // Call the callback with the new ID
	            close();
	        }
		}
	}
	
	private void updateCompany(int companyId) {
		if(nameTextField.getText() != null) {
			DatabaseConnection.updateCompany(companyId, nameTextField.getText());
			if (callback != null) {
	            callback.onCompanyUpdated(nameTextField.getText()); // Call the callback with the new ID
	            close();
	        }
		}
	}
	
}
