package tech.bts.cardgame;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import tech.bts.cardgame.repository.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class InsertGamesSampleData {

    public static void main(String[] args) throws SQLException {

        DataSource dataSource = DataSourceUtil.getDataSourceInPath();
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql-scripts/sample-data.sql"));
    }
}

