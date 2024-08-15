package application;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

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
    private Button btnAddDepartment;
    private ObservableList<company> companies;
    private ObservableList<department> departments = FXCollections.observableArrayList();

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
        //updateDepartments(companies.get(0));
        companyComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateDepartments(newValue);
        });

        // Setup Departments ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.setItems(departments);

        // Setup Buttons
        btnAddDepartment = new Button("Add Department");
        btnAddDepartment.setOnAction(e -> addDepartment(companyComboBox.getValue()));

        // Add components to Grid
        grid.add(new Label("Select Company:"), 0, 0);
        grid.add(companyComboBox, 1, 0);
        grid.add(new Label("Select Department:"), 0, 1);
        grid.add(departmentComboBox, 1, 1);
        grid.add(btnAddDepartment, 1, 2);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setTitle("Company and Departments");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateDepartments(company company) {
        // This method should fetch departments based on the selected company
        // For simplicity, here we just clear the departments list
        departments.clear();
        String sql = "SELECT * FROM public.departments WHERE company_id = 1";
        departments.addAll(DatabaseConnection.getDepartments(sql,DatabaseConnection.getConnection()));
    }

    private void addDepartment(company company) {
        // Code to add a new department
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Department");
        dialog.setHeaderText("Add a new department for " + company.getName());
        dialog.setContentText("Please enter the department name:");
        
        dialog.getDialogPane().getButtonTypes().clear();
        ButtonType okButtonType= new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType cancelButtonType= new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        dialog.showAndWait().ifPresent(response -> {
    		String departmentName = dialog.getEditor().getText();
            System.out.println("Adding new department: " + departmentName);
            department newDep = new department(departmentName, true, true);
            departments.add(newDep);
            DatabaseConnection.insertDepartments(Arrays.asList(newDep), company.getId());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}