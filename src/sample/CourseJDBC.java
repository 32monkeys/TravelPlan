package sample;

import java.sql.*;


public class CourseJDBC {

    public Connection connect(String url)
            throws SQLException {
        return DriverManager.getConnection(url);
    }

  public PreparedStatement selectPreparedStatement(Connection conn)
            throws SQLException {
        String query = "select A.StationName as start, B.StationName as dest, A.Arrival as departure, B.Arrival as arrival "
                + "from Arrive as A "
                + "join (select TrainID, StationName, Arrival from Arrive "
                + "where StationName = ?) as B "
                + "on A.TrainID = B.TrainID "
                + "where start = ? and departure > ? and A.Arrival < B.Arrival "
                + "order by departure";
      PreparedStatement selectPreparedStatement = null;
        selectPreparedStatement = conn.prepareStatement(query);
        return selectPreparedStatement;
    }
}