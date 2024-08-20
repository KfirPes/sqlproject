package application;
import java.io.Serializable;
import java.util.Vector;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class calcComp implements viewer,Serializable {
	private Vector<UIlistener>alllisteners=new Vector<UIlistener>();
	ComboBox<String> departments = new ComboBox<String>();
	ComboBox<String> employees = new ComboBox<String>();
	Label lblText2 = new Label();
	Label lblText3 = new Label();
	Label lblText4 = new Label();
public calcComp(Stage primaryStage)
{
	try {
		VBox vbRoot2=new VBox();
		lblText3.setVisible(false);
		lblText4.setVisible(false);
		Button btn2 = new Button();
		btn2.setText("The benefit of the company");
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for(UIlistener l:alllisteners)
					l.calcComp();
			}
		});
		Button btn3 = new Button();
		btn3.setText("The benefit of the department");
		btn3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for(UIlistener l:alllisteners)
					l.calcDep(departments.getValue());
				lblText3.setVisible(true);
			}
		});
		Button btn4 = new Button();
		btn4.setText("The benefit of the employee");
		btn4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for(UIlistener l:alllisteners)
					l.calcEmp(employees.getValue());
				lblText4.setVisible(true);
			}
		});
		Button btn5 = new Button();
		btn5.setText("save");
		btn5.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for(UIlistener l:alllisteners)
					l.save();
				primaryStage.close();
			}
		});
		vbRoot2.getChildren().addAll(btn2,lblText2,departments,btn3,lblText3,employees,btn4,lblText4,btn5);
		Scene scene = new Scene(vbRoot2,400,400);
		scene.getStylesheets().add( getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	 catch(Exception e) {
			e.printStackTrace();
		}
}
@Override
public void addDepUI(department Dep) {
	departments.getItems().add(Dep.getName());
	
}
@Override
public void add(String name, employee emp, boolean isPossibol) {
	// TODO Auto-generated method stub
	
}
@Override
public void addlistener(UIlistener listener) {
	
	alllisteners.add(listener);
}
@Override
public void calcComp(double calc) {
	lblText2.setText(" "+10*calc);
	
}
@Override
public void calcDep(double calc) {
	lblText3.setText(" "+10*calc);
	
}
@Override
public void calcEmp(double calc) {
	lblText4.setText(" "+10*calc);
	
}
@Override
public void addEmpUI(String name) {
	employees.getItems().add(name);
	
}
}