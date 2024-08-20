package application;

import java.awt.Dialog;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DepartmentDialog extends Stage{

    private TextField departmentNameField;
	private CheckBox isSynCheckBox;
	private CheckBox isPossibleCheckBox;
	private int companyId;
	private department newDep;
	private Button btnAddPosition;
	private possition newPosition;
	private DepartmentCallback callback;

	public DepartmentDialog(Stage parent, int companyId, department departmentToUpdate, String action,  DepartmentCallback callback) {
		this.callback = callback;
		this.companyId = companyId;
    	initModality(Modality.APPLICATION_MODAL);
        initOwner(parent);

        setTitle("Add Department");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Employee Name
        grid.add(new Label("Department Name:"), 0, 0);
        departmentNameField = new TextField();
        grid.add(departmentNameField, 1, 0);
        
        // Is Syn
        isSynCheckBox = new CheckBox();
        grid.add(new Label("Is Syn:"), 0, 1);
        grid.add(isSynCheckBox, 1, 1);

        isPossibleCheckBox = new CheckBox();
        grid.add(new Label("Is Possible:"), 0, 2);
        grid.add(isPossibleCheckBox, 1, 2);
        
        btnAddPosition = new Button("Add Position");
        btnAddPosition.setOnAction(e -> openPositionDialog());
        grid.add(btnAddPosition, 1, 3);
        
        // Buttons
        Button btnAdd = new Button("Add");
        Button btnCancel = new Button("Cancel");
        if(action == "NEW") {
        	btnAdd.setOnAction(e -> addDeparment());        	
        }else {
        	btnAdd.setOnAction(e -> updateDeparment(departmentToUpdate));    
        }
        btnCancel.setOnAction(e -> close());
        
        

        grid.add(btnAdd, 0, 3);
        grid.add(btnCancel, 1, 3);

        Scene scene = new Scene(grid, 300, 200);
        setScene(scene);
    }

	private void openPositionDialog() {
        Stage stage = new Stage();
        PositionDialog dialog = new PositionDialog(stage, newPosition -> {
            this.newPosition = newPosition;
        });

        dialog.show();
        
	}

	private void addDeparment() {
		this.newDep = new department(departmentNameField.getText(), isSynCheckBox.isSelected(), isPossibleCheckBox.isSelected());
		List<Integer> departmentsIds = DatabaseConnection.insertDepartments(Arrays.asList(newDep), companyId);
		this.newDep.setId(departmentsIds.get(0));
		int positionId = DatabaseConnection.insertPosition(this.newPosition, this.companyId, this.newDep.getId());
		this.newPosition.setId(positionId);
		//DatabaseConnection.insertDepartmentPositionPairs(Arrays.asList(new DepartmentPositionPair(departmentsIds.get(0), this.positionId)));
		
		if (callback != null) {
            callback.onDepartmentAdded(this.newDep); // Call the callback with the new ID
            close();
        }
	}
	
	private void updateDeparment(department departmentToUpdate) {
		System.out.println("selectedDepartment"+ departmentToUpdate);
		System.out.println("companyId"+ companyId);
		departmentToUpdate.setName(departmentNameField.getText());
		departmentToUpdate.setPossible(isPossibleCheckBox.isSelected());
		departmentToUpdate.setSyn(isSynCheckBox.isSelected());
		//departmentToUpdate.setPref(null);
		//nnection.updateDepartment(departmentToUpdate.getId(), departmentToUpdate, companyId);
		
		if (callback != null) {
            callback.onDepartmentUpdated(departmentToUpdate); // Call the callback with the new ID
            close();
        }
	}
	
	public department getDepartment() {
		return this.newDep;
	}
}
