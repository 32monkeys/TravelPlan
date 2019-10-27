package files;

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
        //adding all options

        submitButt.setOnAction(event -> updateText());
        for (int i = 2; i < 12; i++) {
            time.getItems().add(i + ":00");
            time.getItems().add(i + ":30");
        }

        from.getItems().addAll("København", "Høje Tåstrup", "Roskilde", "Ringsted", "Odense", "Næstved", "Nykøbing F");
        to.getItems().addAll("København", "Høje Tåstrup", "Roskilde", "Ringsted", "Odense", "Næstved", "Nykøbing F");

    }

    public void updateText() {
        // reset the textarea, so the appendText method doesn't keep adding stuff
        textArea.setText("");
        runDatabase();

    }

    public void runDatabase() {
        Database database = new Database();
        Connection conn = null;

        //converting String of options in time to Integer for comparison in sql query
        String timeString = time.getValue().toString().replace(":", "");
        int timeInt = Integer.parseInt(timeString);

        try {
            String url = "jdbc:sqlite:Rejseplan.db";
            conn = database.connect(url);

            // prepared statement is a select query with connection to the database
            PreparedStatement preparedStatement = database.selectPreparedStatement(conn);
            // parameter index must be given in the order of question marks in the select query
            preparedStatement.setString(1, to.getValue());
            preparedStatement.setString(2, from.getValue());
            preparedStatement.setInt(3, timeInt);
            // executeQuery() returns a resultSet
            ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    //splitting the time integer into minutes and hours for a nicer looking time format
                    int arriM = resultSet.getInt(3)%100;
                    int arriH = (resultSet.getInt(3)-arriM)/100;
                    int depM = resultSet.getInt(4)%100;
                    int depH = (resultSet.getInt(4)-arriM)/100;

                    textArea.appendText(
                            "From: " + resultSet.getString(1) +
                                    " at: " + String.format("%02d:%02d",arriH,arriM) +
                                    "   To: " + resultSet.getString(2) +
                                    " at: " + String.format("%02d:%02d",depH,depM) + "\n");
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


