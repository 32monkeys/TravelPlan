package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {

    @FXML
    TextArea textArea;
    @FXML
    Button submitButt;
    @FXML
    ChoiceBox<String> routes;
    @FXML
    ComboBox time;


    public void initialize() {
        //executed when GUI is ready
        CourseJDBC.startCourseJDBC();
        submitButt.setOnAction(event -> updateText());
        for (int i=2;i<12;i++){
            time.getItems().add(i+":00");
            time.getItems().add(i+":30");
        }
    }

    public void updateText(){

    }
}
