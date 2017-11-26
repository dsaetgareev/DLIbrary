package ru.dinis.library.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * class DMManager gets connection with database.
 * User: Dinis Saetgareev (dinis0086@gmail.com)
 * Date: 25.11.17
 */
public class DBManager {
    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DBManager.class);

    /**
     * gets connection to database.
     * @return connection
     */
    public static Connection getConnection() {
        Connection conn = null;

        try {
            DataSource dataSource = (DataSource) new InitialContext().lookup("jdbc/library");
            conn = dataSource.getConnection();
        } catch (SQLException | NamingException e) {
            LOG.error(e.getMessage(), e);
        }
        return conn;
    }

    /**
     * closes connection.
     * @param rs - result set
     * @param stm -statement
     * @param conn - connection
     */
    public static void closeConnection(ResultSet rs, Statement stm, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
                stm.close();
                conn.close();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
