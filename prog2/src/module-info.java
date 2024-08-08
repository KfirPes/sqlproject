module prog2 {
	 requires java.sql;
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
}
