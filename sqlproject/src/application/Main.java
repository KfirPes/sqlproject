package application;
	
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


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			company comp=new company();
			addAppartment addApp=new addAppartment(primaryStage);
			controller cont=new controller(comp,addApp);
			addEmpRol aer=new addEmpRol(new Stage(),comp);
			controller cont2=new controller(comp,aer);
			calcComp cc=new calcComp(new Stage());
			controller cont3=new controller(comp,cc);
		}
		 catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
