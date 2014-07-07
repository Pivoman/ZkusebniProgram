package ebrana;

import java.io.IOException;

/**
 * Main class
 * @author Marek
 */
public class Main {

    /**
     * creates file in project and table in DB with data
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        FileCreate fc = new FileCreate();
        TableCreate tc = new TableCreate();
        fc.writeToFile(fc.createString());
        tc.createSQLTable();
    }
}
