/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ebrana;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import oracle.jdbc.OracleConnection;

/**
 *
 * @author Zechmeister Ji��
 */
public class OracleConnector {
    private static String userName;
    private static String password;
    private static String serverName;
    private static int portNumber;
    private static String dbms;
    private static String sid;
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (OracleConnector.conn == null) {
            throw new SQLException("Session is not been established");
        }
        return OracleConnector.conn;
    }

    public static void setUpConnection(String tnsAlias, String userName, String password) throws SQLException {
        OracleConnector.dbms = "oracle:thin";
        OracleConnector.userName = userName;
        OracleConnector.password = password;

        Properties connectionProps = new Properties();
        connectionProps.put("user", OracleConnector.userName);
        connectionProps.put("password", OracleConnector.password);
               
        OracleConnector.conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@"+tnsAlias,
                connectionProps);

        OracleConnector.conn.setAutoCommit(false);

        System.out.println("Connected to database via TNS");
    }
    
    public static void setSessionSign(String action, String module, String clientId) throws SQLException {
        String[] metrics = new String[OracleConnection.END_TO_END_STATE_INDEX_MAX];
        metrics[OracleConnection.END_TO_END_CLIENTID_INDEX] = clientId;
        metrics[OracleConnection.END_TO_END_MODULE_INDEX] = module;
        metrics[OracleConnection.END_TO_END_ACTION_INDEX] = action;
        ((OracleConnection)conn).setEndToEndMetrics(metrics, (short)0);
    }
    
    public static void setUpConnection(String serverName, int portNumber, String sid, String userName, String password) throws SQLException {

        OracleConnector.serverName = serverName;
        OracleConnector.portNumber = portNumber;
        OracleConnector.dbms = "oracle:thin";
        OracleConnector.sid = sid;
        OracleConnector.userName = userName;
        OracleConnector.password = password;

        Properties connectionProps = new Properties();
        connectionProps.put("user", OracleConnector.userName);
        connectionProps.put("password", OracleConnector.password);

        OracleConnector.conn = DriverManager.getConnection(
                "jdbc:" + OracleConnector.dbms + ":@"
                + OracleConnector.serverName
                + ":" + OracleConnector.portNumber + ":" + OracleConnector.sid,
                connectionProps);

        OracleConnector.conn.setAutoCommit(false);

        System.out.println("Connected to database");
    }

    public static String getConnectionString() {
        return "//jdbc:" + OracleConnector.dbms + ":@" + OracleConnector.serverName + ":" + OracleConnector.portNumber + ":" + OracleConnector.sid;
    }

    public static void closeConnection(boolean commit) throws SQLException {

        if (conn != null) {

            if (commit) {
                conn.commit();
            }

            conn.close();
            conn = null;
            System.out.println("Connection closed");
        }
    }
    
    public static java.sql.Date parseDate(String date, String format){
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        java.sql.Date result = null; 
        try {
            result = new java.sql.Date(df.parse(date).getTime());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
