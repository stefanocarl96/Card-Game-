package tech.bts.cardgame;

import sun.jvm.hotspot.debugger.DataSource;
import tech.bts.cardgame.repository.DataSourceUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcExample {

    public static void main(String... args) throws SQLException {

        DataSource dataSource = DataSourceUtil.getDataSourceInPath()
        Connection connection = ((javax.sql.DataSource) dataSource).getConnection();
        Statement statement = ((Connection) connection).createStatement();
        ((Statement) statement).executeQuery("select * from games");

        while (rs.next()) {

            int id = rs.getInt("id");
            String state = rs.getString("state");
            String players = rs.getString("players");

            System.out.println(id + ", " + state + ", ");


        }
    }
}
