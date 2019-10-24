package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.sql.*;


public class Controller {

    @FXML
    TextArea textArea;
    @FXML
    Button submitButt;
    @FXML
    ChoiceBox<String> from;
    @FXML
    ChoiceBox<String> to;
    @FXML
    ComboBox time;


    public void initialize() {
        //executed when GUI is ready


        submitButt.setOnAction(event -> updateText());
        for (int i = 2; i < 12; i++) {
            time.getItems().add(i + ":00");
            time.getItems().add(i + ":30");
        }

        from.getItems().addAll("København", "Høje Tåstrup", "Roskilde", "Ringsted", "Odense", "Næstved", "Nykøbing F");

        to.getItems().addAll("København", "Høje Tåstrup", "Roskilde", "Ringsted", "Odense", "Næstved", "Nykøbing F");
    }

    public void updateText() {
        textArea.setText("");
        runDatabase();

    }

    public void runDatabase() {
        CourseJDBC CJ = new CourseJDBC();
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:Rejseplan.db";
            conn = CJ.connect(url);
            //Select;

            PreparedStatement pstmt = CJ.selectPreparedStatement(conn);
            ResultSet resultSet = pstmt.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    textArea.appendText(" " + resultSet.getString(i) + " ");
                }
                textArea.appendText("\n");
                /*
                String name = resultSet.getString("StationName");
                textArea.appendText(name + "\n");
                System.out.println(name);
                */
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

