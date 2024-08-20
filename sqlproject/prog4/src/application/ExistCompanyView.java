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
    private Button btnDeleteDepartment;
    private Button btnAddPreference;
    private Button btnDeletePreference;
    private Button btnAddEmployee;
    private Button btnDeleteEmployee;
    private Button btnUpdateEmployee;
    private Button btnAddCompany;
    private Button btnDeleteCompany;
    private Button btnUpdateCompany;
    private ObservableList<company> companies;
    private ObservableList<department> departments = FXCollections.observableArrayList();
    private ObservableList<preference> preferences;  // ObservableList for Preferences
    private ObservableList<employee> roles = FXCollections.observableArrayList();
    private ObservableList<employee> employees = FXCollections.observableArrayList();
    private Integer departmentId;
    private Integer companyId;
    private company selectedCompany;
	private Button btnUpdateDepartment;
	private department selectedDepartment;
	private employee selectedEmployee;
	private int employeeId;
	
	
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
        selectedCompany = companyComboBox.getValue();
        btnAddCompany = new Button("Add Company");
        btnAddCompany.setOnAction(e -> openNewCompanyDialog());
        
        btnDeleteCompany = new Button("Delete Company");
        btnDeleteCompany.setOnAction(e -> {
            company selectedCompany = companyComboBox.getValue();
            if (selectedCompany != null) {
            	deleteCompany(selectedCompany);
            }else {
                System.out.println("selectedCompany is NULL.");
            }
        });
        
        btnUpdateCompany = new Button("Update Company");
        btnUpdateCompany.setOnAction(e -> {
            company selectedCompany = companyComboBox.getValue();
            if (selectedCompany != null) {
            	openUpdateCompanyDialog(selectedCompany);
            }else {
                System.out.println("selectedCompany is NULL.");
            }
        });
        
        
        companyComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        	if(newValue != null) {
            	this.companyId = newValue.getId(); 
            	this.selectedCompany = newValue;
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
            	this.selectedDepartment = newValue;
                setEmployees();
        	}
        });

        // Setup Buttons
        btnAddDepartment = new Button("Add Department");
        btnAddDepartment.setOnAction(e -> openDepartmentDialog("NEW"));
        
        btnDeleteEmployee = new Button("Delete Employee");
        btnDeleteEmployee.setOnAction(e -> {
            employee selectedEmployee = employeeComboBox.getValue();
            if (selectedEmployee != null) {
            	deleteEmployee(selectedEmployee);
            }else {
            	System.out.println("selectedEmployee is NUll");
            }
        });
        
        btnUpdateEmployee = new Button("Update Employee");
        btnUpdateEmployee.setOnAction(e -> {
            employee selectedEmployee = employeeComboBox.getValue();
            if (selectedEmployee != null) {
            	openEmployeeDialog("UPDATE");
            }else {
                System.out.println("selectedEmployee is NULL.");
            }
        });
        
        // New Add Preference Button
        btnAddPreference = new Button("Add Preference");
        btnAddPreference.setOnAction(e -> openPreferenceDialog());
        
        /*btnDeletePreference = new Button("Delete Preference");
        btnDeletePreference.setOnAction(e -> {
            preference selectedPreference = preferenceComboBox.getValue();
            if (selectedPreference != null) {
            	deletePreference(selectedPreference);
            }else {
            	System.out.println("selectedPreference is NUll");
            }
        });*/
        
        btnAddEmployee = new Button("Add Employee");
        btnAddEmployee.setOnAction(e -> openEmployeeDialog("NEW"));
        
        btnDeleteDepartment = new Button("Delete Department");
        btnDeleteDepartment.setOnAction(e -> {
            department selectedDepartment = departmentComboBox.getValue();
            if (selectedDepartment != null) {
            	deleteDepartment(selectedDepartment);
            }else {
            	System.out.println("selectedDepartment is NUll");
            }
        });
        
        btnUpdateDepartment = new Button("Update Department");
        btnUpdateDepartment.setOnAction(e -> {
            department selectedDepartment = departmentComboBox.getValue();
            if (selectedDepartment != null) {
            	openDepartmentDialog("UPDATE");
            }else {
            	System.out.println("selectedDepartment is NUll");
            }
        });
        
        // Setup Employees ComboBox
        employeeComboBox = new ComboBox<>();
        employees = FXCollections.observableArrayList(getEmployees());  // Populate employees from database
        employeeComboBox.setItems(employees);
        
        employeeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        	System.out.println(newValue);
        	if(newValue != null) {
            	this.employeeId = newValue.getId(); 
            	this.selectedEmployee = newValue;
        	}
        });
        
        // Add components to Grid
     // Setup and add components to the grid
        grid.add(new Label("Select Company:"), 0, 0);
        grid.add(companyComboBox, 1, 0);
        grid.add(btnUpdateCompany, 0, 1);
        grid.add(btnAddCompany, 1, 1);
        grid.add(btnDeleteCompany, 2, 1);

        // Department management
        grid.add(new Label("Select Department:"), 0, 2);
        grid.add(departmentComboBox, 1, 2);
        grid.add(btnUpdateDepartment, 0, 3);
        grid.add(btnAddDepartment, 1, 3);
        grid.add(btnDeleteDepartment, 2, 3);

        // Preference management
        grid.add(new Label("Select Preference:"), 0, 4);
        //grid.add(preferenceComboBox, 1, 4);
        grid.add(btnAddPreference, 1, 5);
        //grid.add(btnDeletePreference, 2, 5);

        // Employee management
        grid.add(new Label("Select Employee:"), 0, 6);
        grid.add(employeeComboBox, 1, 6);
        grid.add(btnUpdateEmployee, 0, 7);
        grid.add(btnAddEmployee, 1, 7);
        grid.add(btnDeleteEmployee, 2, 7);

        // Scene and stage setup
        Scene scene = new Scene(grid, 400, 400);  // Adjusted size for additional controls
        primaryStage.setTitle("Company and Departments");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

	private void openNewCompanyDialog() {
		CompanyCallback callback = new CompanyCallback() {
			
			@Override
			public void onCompanyUpdated(String name) {

			}
			
			@Override
			public void onCompanyAdded(company insertedComp) {
	        	if(insertedComp != null) {
	            	companyId = insertedComp.getId();
	            	companies.add(insertedComp);
	            	companyComboBox.setItems(companies);
	            	selectedCompany = insertedComp;
	            	companyComboBox.getSelectionModel().select(insertedComp);
	        	}
			}
		};
        Stage stage = new Stage();
        CompanyDialog dialog = new CompanyDialog(stage, null, "NEW", callback);
        dialog.show();

	}
    
	private void openUpdateCompanyDialog(company selectedCompany) {
		CompanyCallback callback = new CompanyCallback() {
			
			@Override
			public void onCompanyUpdated(String name) {
				companyComboBox.getItems().remove(selectedCompany);
				System.out.println("before " + selectedCompany);
				selectedCompany.setName(name);
				companyId = selectedCompany.getId();
				companyComboBox.getItems().add(selectedCompany);
				companyComboBox.getSelectionModel().select(selectedCompany);
				System.out.println("after " + selectedCompany);
			}
			
			@Override
			public void onCompanyAdded(company insertedComp) {
				// TODO Auto-generated method stub
				
			}
		};
        Stage stage = new Stage();
        CompanyDialog dialog = new CompanyDialog(stage, companyId, "UPDATE", callback);
        dialog.show();
	}
	
    private void deleteCompany(company selectedCompany) {
    	if(DatabaseConnection.deleteCompany(selectedCompany.getId())) {
            companies.remove(selectedCompany);  // Update the observable list
            System.out.println("Company deleted successfully.");
            companyComboBox.setItems(companies);
    	}else {
    		System.out.println("Failed to delete company.");
    	}
    }
    
    private void deleteDepartment(department selectedDepartment) {
        if (DatabaseConnection.deleteDepartment(selectedDepartment.getId())) {
            departments.remove(selectedDepartment);  // Update the observable list
            System.out.println("Department deleted successfully.");
            departmentComboBox.setItems(departments);
        } else {
            System.out.println("Failed to delete department.");
        }
 	}
    
	private void deleteEmployee(employee selectedEmployee) {
        if (DatabaseConnection.deleteEmployee(selectedEmployee.getId())) {
            employees.remove(selectedEmployee);  // Update the observable list
            System.out.println("Employee deleted successfully.");
            employeeComboBox.setItems(employees);
        } else {
            System.out.println("Failed to delete Employee.");
        }
		
	}
    
	private void openPreferenceDialog() {
        JFrame frame = new JFrame();
        PreferenceDialog dialog = new PreferenceDialog(frame,companyId);
        dialog.setVisible(true);
        
        preference newPreference = dialog.getPreference();
        if (newPreference != null) {
            preferences.add(newPreference);  // Add the new Preference to the ObservableList
        }
    }
    
    /*private void updatePreferences() {
        // This method should fetch departments based on the selected company
        // For simplicity, here we just clear the departments list
        preferences.clear();
        String sql = "SELECT * FROM public.preferences";//need to change;
        preferences.addAll(DatabaseConnection.getPreferences(sql,DatabaseConnection.getConnection()));
    }*/

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

    private void openDepartmentDialog(String action) {
    	DepartmentCallback callback = new DepartmentCallback() {
			
			@Override
			public void onDepartmentUpdated(department updatedDep) {
				if(updatedDep != null) {
					departmentComboBox.getItems().remove(updatedDep);
					System.out.println("before " + updatedDep);
					selectedDepartment = updatedDep;
					departmentId = selectedCompany.getId();
					departmentComboBox.getItems().add(selectedDepartment);
					departmentComboBox.getSelectionModel().select(selectedDepartment);
					System.out.println("after " + selectedDepartment);
				}
			}
			
			@Override
			public void onDepartmentAdded(department insertedDep) {
	        	if(insertedDep != null) {
	            	companyId = insertedDep.getId();
	            	departments.add(insertedDep);
	            	companyComboBox.setItems(companies);
	            	selectedDepartment = insertedDep;
	            	departmentComboBox.getSelectionModel().select(insertedDep);
	        	}
				
			}
		};
        Stage stage = new Stage();
        
        DepartmentDialog dialog = new DepartmentDialog(stage, companyId, this.selectedDepartment, action, callback);
        dialog.show();
    }
    
    
    private void openEmployeeDialog(String action) {
    	if(companyId != null) {
    		Stage stage = new Stage();
        
            EmployeeDialog dialog = new EmployeeDialog(stage, companyId, selectedEmployee, action);
            dialog.showAndWait();
            employee emp = dialog.getEmployee();
            System.out.println("emp" + emp);
            if (emp != null) {
            	if(action == "NEW") {
            		employees.add(emp);  // Add the new Employee to the ObservableList
            	}else {
    				employeeComboBox.getItems().remove(emp);
    				System.out.println("before " + emp);
    				selectedEmployee = emp;
    				departmentId = selectedCompany.getId();
    				employeeComboBox.getItems().add(selectedEmployee);
    				employeeComboBox.getSelectionModel().select(selectedEmployee);
    				System.out.println("after " + selectedEmployee);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}