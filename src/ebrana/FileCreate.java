package ebrana;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

/**
 * Creates file "File.txt" and fills this file from website
 * @author Marek
 */
public class FileCreate {

    /**
     * by using StringBuilder creates complete String for file.
     * data are obtained from list and they are separated via " | ".
     * 
     * @return Complete String for file
     * @throws IOException 
     */
    public String createString() throws IOException {
        WebRead vWebRead = new WebRead();
        StringBuilder sb = new StringBuilder();
        List<String> list = vWebRead.readHead();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String text = it.next();
            sb.append(text).append(" | ");
        }
        sb.append("\r\n");

        int counter = 0;
        list = vWebRead.read();
        it = list.iterator();
        while (it.hasNext()) {
            counter++;
            String text = it.next().toString();
            if (text.equals("Suma")) {
                break;
            } else {
                sb.append(text).append(" | ");
                if (counter % 8 == 0) {
                    sb.append("\r\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Creates file "File.txt" with UTF-8 encoding 
     * and write @param text to this File
     * 
     * @param text - text written to file
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException 
     */
    public void writeToFile(String text) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("File.txt", "UTF-8");
        writer.println(text);
        writer.close();
        System.out.println("Written to file File.txt...");
    }
}
