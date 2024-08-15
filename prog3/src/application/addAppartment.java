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
public class addAppartment implements viewer,Serializable  {
	boolean isPos=false;
	private Vector<UIlistener>alllisteners=new Vector<UIlistener>();
		public addAppartment(Stage primaryStage) {
			try {
				VBox vbRoot2=new VBox();
				TextField tfText = new TextField();
			Label lbltext=new Label();
			Label lbltext2=new Label();
			lbltext2.setText("Enter the name of the department");
			CheckBox isPosi = new CheckBox("is possible?");
			CheckBox isSyn = new CheckBox("is Synchronized?");
			isSyn.setVisible(false);
			ComboBox<String> preferences = new ComboBox<String>();
			preferences.getItems().addAll("later", "earlier", "home","same");
			preferences.setVisible(false);
			isPosi.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
				isPos=true;
				}
				});
			Button btn = new Button();
			btn.setText("submit");
			isPosi.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
				isSyn.setVisible(isPosi.isSelected());
				}
			});
			isSyn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
				preferences.setVisible(isSyn.isSelected());
				}
				});
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					if(tfText.getText().equals(""))
						lbltext.setText("You must enter the name of the department");
					else
					{
			department Dep=new department(tfText.getText(),isPosi.isSelected());
				if(isSyn.isSelected())
					if(preferences.getValue()==null)
						lbltext.setText("You must choose a preferance");
					else
						for(UIlistener l:alllisteners) {
					Dep.setPref(l.setPref(preferences.getValue()));
					l.addDepToUI(Dep);
						}
				else
					for(UIlistener l:alllisteners)
					l.addDepToUI(Dep);
					}
				}
				
			});
			vbRoot2.getChildren().addAll(lbltext2,tfText,isPosi,isSyn,preferences,btn,lbltext);
			Scene scene = new Scene(vbRoot2,400,400);
			scene.getStylesheets().add( getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		 catch(Exception e) {
			e.printStackTrace();
		}
		}

		public void start(Stage arg0) throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void add(String name, employee emp, boolean isPossibol) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addDepUI(department Dep) {
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
