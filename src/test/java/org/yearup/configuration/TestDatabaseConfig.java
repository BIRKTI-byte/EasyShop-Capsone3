package org.yearup.configuration;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@PropertySource("classpath:application.properties")
public class TestDatabaseConfig
{
    private final String serverUrl;
    private final String testDb;
    private final String username;
    private final String password;

    @Autowired
    public TestDatabaseConfig(@Value("${datasource.url}") String serverUrl,
                              @Value("${datasource.username}") String username,
                              @Value("${datasource.password}") String password,
                              @Value("${datasource.testdb}") String testDb)
    {
        this.serverUrl = serverUrl;
        this.testDb = testDb;
        this.username = username;
        this.password = password;
    }

    // Initialize the database before creating the dataSource bean
    @Bean
    public TestDatabaseInitializer testDatabaseInitializer() throws SQLException {
        return new TestDatabaseInitializer(serverUrl, testDb, username, password);
    }

    @Bean
    public DataSource dataSource(TestDatabaseInitializer initializer) throws SQLException, IOException
    {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setUrl(serverUrl + testDb);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setAutoCommit(false);
        dataSource.setSuppressClose(true);

        ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
        Reader reader = new BufferedReader(new FileReader((new ClassPathResource("test-data.sql")).getFile().getAbsolutePath()));
        runner.runScript(reader);
        dataSource.getConnection().commit();

        return dataSource;
    }

    @PreDestroy
    public void cleanup() {

        try(Connection connection = DriverManager.getConnection(serverUrl.replaceAll("/$", ""), username, password);
            Statement statement = connection.createStatement();
        )
        {
            statement.execute("DROP DATABASE IF EXISTS " + testDb + ";");
        }
        catch (SQLException ignored){}

    }

    // Inner class to handle database initialization
    public static class TestDatabaseInitializer {
        public TestDatabaseInitializer(String serverUrl, String testDb, String username, String password) throws SQLException {
            // Connect without specifying a database (just to server)
            String connectionUrl = serverUrl.replaceAll("/$", ""); // Remove trailing slash if present
            try(Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                Statement statement = connection.createStatement();
            )
            {
                statement.execute("DROP DATABASE IF EXISTS " + testDb + ";");
                statement.execute("CREATE DATABASE " + testDb + ";");
                System.out.println("Test database created successfully: " + testDb);
            }
            catch (SQLException e) {
                System.err.println("Failed to create test database: " + e.getMessage());
                throw e;
            }
        }
    }
}
