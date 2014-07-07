package ebrana;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates and fill table "UCET" in database
 * @author Marek
 */
public class TableCreate {

    /**
     * Connects to the database.
     */
    private void connection() {
        try {
            OracleConnector.setUpConnection("fei-sql1.upceucebny.cz", 1521, "ee11", "ST36114", "ahoj1234");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Connects to the database and read a table from website. If table "UCET" 
     * doesn't exists, creates this table in DB. Else deletes all rows in this 
     * table. Then inserts all rows from website through the list to the 
     * SQL table. 
     * 
     * @throws IOException 
     */
    public void createSQLTable() throws IOException {
        try {
            connection();
            Connection conn = OracleConnector.getConnection();
            Statement st = conn.createStatement();
            WebRead vWebRead = new WebRead();
            List<String> list = vWebRead.read();
            Iterator<String> it = list.iterator();

            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "UCET", null);
            
            if(!rs.next()){
                st.executeUpdate("CREATE TABLE ucet ( "
                    + "Datum VARCHAR2(1000) NOT NULL,"
                    + "Objem VARCHAR2(1000) NOT NULL,"
                    + "Typ VARCHAR2(1000) NOT NULL,"
                    + "KS VARCHAR2(1000),"
                    + "VS VARCHAR2(1000),"
                    + "SS VARCHAR2(1000),"
                    + "Uzivatelska_Identifikace VARCHAR2(1000),"
                    + "Zprava_Pro_Prijemce VARCHAR2(1000))");
            }else{
                st.executeUpdate("delete from ucet");
            }
            while (it.hasNext()) {
                InsertToTable(it.next(), it.next(), it.next(), it.next(), 
                        it.next(), it.next(), it.next(), it.next());
            }
            conn.commit();
            System.out.println("Table \"UCET\" created in database...");
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Inserts row to table "UCET" from parameters below. 
     * Secured against SQL injection.
     * 
     * @param date
     * @param amount
     * @param type
     * @param cs
     * @param vs
     * @param ss
     * @param userIdentification
     * @param message
     * @throws SQLException 
     */
    private void InsertToTable(String date, String amount, String type, 
            String cs, String vs, String ss, String userIdentification, 
            String message) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement pst = conn.prepareStatement("INSERT INTO ucet"
                + " VALUES(?,?,?,?,?,?,?,?)");
        pst.setString(1, date);
        pst.setString(2, amount);
        pst.setString(3, type);
        pst.setString(4, cs);
        pst.setString(5, vs);
        pst.setString(6, ss);
        pst.setString(7, userIdentification);
        pst.setString(8, message);
        pst.executeUpdate();
    }
}
