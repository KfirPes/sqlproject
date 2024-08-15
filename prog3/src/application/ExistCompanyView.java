package application;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ExistCompanyView extends Application {
    private ComboBox<company> companyComboBox;
    private ComboBox<department> departmentComboBox;
    private ComboBox<employee> employeeComboBox;      // ComboBox for Employees
    private Button btnAddDepartment;
    private Button btnAddPreference;
    private Button btnAddEmployee;
    private Button btnAddCompany;
    private ObservableList<company> companies;
    private ObservableList<department> departments = FXCollections.observableArrayList();
    private ObservableList<preference> preferences;  // ObservableList for Preferences
    private ObservableList<employee> roles = FXCollections.observableArrayList();
    private ObservableList<employee> employees = FXCollections.observableArrayList();
    private Integer departmentId;
    private Integer companyId;

    @Override
    public void start(Stage primaryStage) {
        Connection conn = DatabaseConnection.getConnection();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Setup Companies ComboBox
        companyComboBox = new ComboBox<>();
        companies = FXCollections.observableArrayList(DatabaseConnection.getCompanies("SELECT * FROM public.companies",conn));
        companyComboBox.setItems(companies);
        
        btnAddCompany = new Button("Add Company");
        btnAddCompany.setOnAction(e -> openNewCompanyDialog());
        
        companyComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        	if(newValue != null) {
            	this.companyId = newValue.getId(); 
                setDepartments();
                departmentComboBox.setItems(departments);
        	}
        });
        
        // Setup Departments ComboBox
        departmentComboBox = new ComboBox<>();
        
        departmentComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        	System.out.println(newValue);
        	if(newValue != null) {
            	this.departmentId = newValue.getId(); 
                setEmployees();
        	}
        });

        // Setup Buttons
        btnAddDepartment = new Button("Add Department");
        btnAddDepartment.setOnAction(e -> openDepartmentDialog(companyComboBox.getValue()));
        
        // New Add Preference Button
        btnAddPreference = new Button("Add Preference");
        btnAddPreference.setOnAction(e -> addPreference());
        
        btnAddEmployee = new Button("Add Employee");
        btnAddEmployee.setOnAction(e -> openEmployeeDialog());
        
        // Setup Employees ComboBox
        employeeComboBox = new ComboBox<>();
        employees = FXCollections.observableArrayList(getEmployees());  // Populate employees from database
        employeeComboBox.setItems(employees);
        
        // Add components to Grid
        grid.add(new Label("Select Company:"), 0, 0);
        grid.add(companyComboBox, 1, 0);
        
        grid.add(btnAddCompany, 1, 1);

	     grid.add(new Label("Select Department:"), 0, 2);
	     grid.add(departmentComboBox, 1, 2);
	     grid.add(btnAddDepartment, 1, 3);
	
	     grid.add(btnAddPreference, 1, 4);
	
	     // Add Employee Label and ComboBox
	     grid.add(new Label("Select Employee:"), 0, 5);
	     grid.add(employeeComboBox, 1, 5);
	     grid.add(btnAddEmployee, 1, 6);
        

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setTitle("Company and Departments");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void openNewCompanyDialog() {
        Stage stage = new Stage();
        CompanyDialog dialog = new CompanyDialog(stage, newComp -> {
        	if(newComp != null) {
            	this.companyId = newComp.getId();
            	companies.add(newComp);
        	}
        });
        dialog.show();
	}

	private void addPreference() {
        JFrame frame = new JFrame();
        PreferenceDialog dialog = new PreferenceDialog(frame,companyId);
        dialog.setVisible(true);
        
        preference newPreference = dialog.getPreference();
        if (newPreference != null) {
            preferences.add(newPreference);  // Add the new Preference to the ObservableList
        }
    }
    
    private void updatePreferences() {
        // This method should fetch departments based on the selected company
        // For simplicity, here we just clear the departments list
        preferences.clear();
        String sql = "SELECT * FROM public.preferences";//need to change;
        preferences.addAll(DatabaseConnection.getPreferences(sql,DatabaseConnection.getConnection()));
    }

    private void setDepartments() {
        departments.clear();
        String sql = "SELECT * FROM public.departments WHERE company_id = " + this.companyId;
        departments.addAll(DatabaseConnection.getDepartments(sql,DatabaseConnection.getConnection()));
    }
    
    private void setEmployees() {
    	employees.clear();
    	if(this.companyId != null) {
            String sql = "SELECT * FROM public.employees WHERE company_id = " + this.companyId;
            employees.addAll(DatabaseConnection.getEmployees(sql));	
    	}
    }
    
    private ObservableList<employee> getEmployees(){
    	return employees;
    }

    private void openDepartmentDialog(company company) {
        if(company != null) {
        	Stage stage = new Stage();
            DepartmentDialog dialog = new DepartmentDialog(stage, company.getId(), newDepartment -> {           
                if (newDepartment != null) {
                    departments.add(newDepartment);  // Add the new Preference to the ObservableList
                }
            });

            dialog.showAndWait();
        }
    }
    
    
    private void openEmployeeDialog() {
    	if(companyId != null) {
    		Stage stage = new Stage();
        
            EmployeeDialog dialog = new EmployeeDialog(stage, companyId);
            dialog.showAndWait();

            employee newEmployee = dialog.getEmployee();
            if (newEmployee != null) {
                employees.add(newEmployee);  // Add the new Employee to the ObservableList
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}