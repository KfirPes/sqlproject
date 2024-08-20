package application;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EmployeeDialog extends Stage {
    private TextField employeeNameField;
    private TextField employeeTypeField;
    private employee employee;
    private Button btnAddEmployee;
    private ComboBox<preference> preferenceComboBox;
    private ObservableList<preference> preferences = FXCollections.observableArrayList();
    private ObservableList<possition> positions = FXCollections.observableArrayList();
    private Connection conn = DatabaseConnection.getConnection();
	private ComboBox positionComboBox;
	private int companyId;

    public EmployeeDialog(Stage parent, int companyId, employee emp, String action) {
    	this.companyId = companyId;
    	initModality(Modality.APPLICATION_MODAL);
        initOwner(parent);

        setTitle("Add Employee");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Employee Name
        Label nameLabel = new Label("Employee Name:");
        grid.add(nameLabel, 0, 0);
        employeeNameField = new TextField();
        grid.add(employeeNameField, 1, 0);

        // Employee Type
        Label typeLabel = new Label("Employee Type:");
        grid.add(typeLabel, 0, 1);
        employeeTypeField = new TextField();
        grid.add(employeeTypeField, 1, 1);

        // Preferences ComboBox
        Label preferenceLabel = new Label("Select Preference:");
        grid.add(preferenceLabel, 0, 2);
        preferenceComboBox = new ComboBox<>();
        preferences = FXCollections.observableArrayList(getPreferences());  // Populate preferences from database
        preferenceComboBox.setItems(preferences);
        grid.add(preferenceComboBox, 1, 2);

        Label positionLabel = new Label("Select Position:");
        grid.add(positionLabel, 0, 3);
        positionComboBox = new ComboBox<>();
        positions = FXCollections.observableArrayList(getPositions());  // Populate positions from database
        positionComboBox.setItems(positions);
        grid.add(positionComboBox, 1, 3);

        // Buttons
        btnAddEmployee = new Button("Add");
        Button btnCancel = new Button("Cancel");
        if(action == "NEW") {
        	btnAddEmployee.setOnAction(e -> addEmployee());        	
        }else {
        	btnAddEmployee.setOnAction(e -> updateEmployee(emp));        	
        }
        btnCancel.setOnAction(e -> close());

        grid.add(btnAddEmployee, 0, 4);
        grid.add(btnCancel, 1, 4);

        Scene scene = new Scene(grid, 300, 200);
        setScene(scene);
    }
    
    private List<possition> getPositions() {
        positions.clear();
        String sql = "SELECT * FROM public.roles WHERE company_id = " + companyId;
        return DatabaseConnection.getPositions(sql);
	}

	private List<preference> getPreferences(){
        preferences.clear();
        String sql = "SELECT * FROM public.preferences WHERE company_id = " + companyId;//need to change;
        return DatabaseConnection.getPreferences(sql,DatabaseConnection.getConnection());
    }
    
    
    private void addEmployee() {
    	preference selectedPref = preferenceComboBox.getValue();
        if(selectedPref == null) {
        	System.out.println("please choose a preference");
        	return;
        }
    	
        employee = new employee(employeeNameField.getText(), employeeTypeField.getText(), selectedPref);  // Assuming Employee has a constructor that takes name and type
        List<Integer> employeesIds = DatabaseConnection.insertEmployees(Arrays.asList(employee), Arrays.asList(selectedPref.getId()));
        EmployeePositionPair pair = new EmployeePositionPair(employeesIds.get(0), selectedPref.getId());
        
        close();
    }
    
    private void updateEmployee(employee emp) {
    	preference selectedPref = preferenceComboBox.getValue();
        if(selectedPref == null) {
        	System.out.println("choose a preference");
        	return;
        }
    	emp.setName(employeeNameField.getText());
    	emp.setType(employeeTypeField.getText());
    	emp.setPref(selectedPref);
    	this.employee = emp;
        DatabaseConnection.updateEmployee(emp);
        
        close();
    }

    public employee getEmployee() {
        return employee;
    }
}
