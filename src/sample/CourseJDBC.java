package sample;

import java.sql.*;


public class CourseJDBC {

    public Connection connect(String url)
            throws SQLException {
        return DriverManager.getConnection(url);
    }

  public PreparedStatement selectPreparedStatement(Connection conn)
            throws SQLException {
        String query = "select StationName, Arrival from Arrive "
                + "where StationName = 'København' and Arrival > 800";
        PreparedStatement selectPreparedStatement = null;
        selectPreparedStatement = conn.prepareStatement(query);
        return selectPreparedStatement;
    }

}