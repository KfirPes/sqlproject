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

public class addEmpRol implements viewer,Serializable {
	ComboBox<String> departments = new ComboBox<String>();
	boolean isPos=false;
	private Vector<UIlistener>alllisteners=new Vector<UIlistener>();
	public addEmpRol(Stage primaryStage,company comp) {
		try {
			VBox vbRoot2=new VBox();
			Label lblText2 = new Label();
			Label lblText3 = new Label();
			lblText2.setText("Choose a department: " );
			Label lblName = new Label();
			lblName.setText("Enter the name of the employee: " );
			TextField tfName = new TextField();
			Label lblType = new Label();
			lblType.setText("Enter the type of the employee: " );
			TextField tfType = new TextField();
			Label lblPref = new Label();
			lblPref.setText("Enter the preference preference: " );
			ComboBox<String> prefer = new ComboBox<String>();
			prefer.getItems().addAll("later", "earlier", "home","same");
			Label lblRole = new Label();
			lblRole.setText("Enter the name of the role: " );
			TextField roleName = new TextField();
			CheckBox isPosi = new CheckBox("is possible?");
			isPosi.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
				isPos=true;
				}
				});

			Button btn2 = new Button();
			btn2.setText("submit");
			btn2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					if(departments.getValue()==null)
						lblText3.setText("You must choose a department");
			employee emp=new employee(tfType.getText(),tfName.getText(),pref(prefer.getValue()));
			for(UIlistener l:alllisteners)
				l.add(roleName.getText(), emp, isPosi.isSelected(), departments.getValue());
			
}
				
			});
			vbRoot2.getChildren().addAll(lblText2,departments,lblName,tfName,lblType,tfType,lblPref,prefer,lblRole,roleName,isPosi,btn2,lblText3);
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
		// TODO Auto-generated method stub
		
	}
	public preference pref(String s) {
		preference home=new preference("home",0.9);
		preference same=new preference("same",0);
		preference later=new preference("later",0.2);
		preference earlier=new preference("earlier",0.2);
		if(s.equals("home"))
			return home;
		else
			if(s.equals("same"))
				return same;
			else
				if(s.equals("later"))
					return later;
				else
						return earlier;
	}

	@Override
	public void calcDep(double calc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calcEmp(double calc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEmpUI(String name) {
		// TODO Auto-generated method stub
		
	}
}