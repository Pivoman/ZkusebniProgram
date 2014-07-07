package ebrana;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Reads data from table of transparent account that is given by URL 
 * @author Marek
 */
public class WebRead {
    private final URL fioURL;

    /**
     * Constructor - sets URL
     * @throws MalformedURLException 
     */
    public WebRead() throws MalformedURLException {
        fioURL = new URL("https://www.fio.cz/scgi-bin/hermes/dz-transparent.cgi?ID_ucet=2600088789");
    }
    
    /**
     * Parse column names from the table by using Jsoup
     * 
     * @return list of column names from the table on website
     * @throws IOException 
     */
    public List<String> readHead() throws IOException{
        List<String> list = new ArrayList<>();
        Document doc = Jsoup.parse(fioURL, 5000);
        Element table = doc.select("table[class=main]").first();
        Iterator<Element> it = table.select("th").iterator();
        
        while (it.hasNext()) {
            list.add(it.next().text());
        }
        return list;
    }
    
    /**
     * Parse data from the table by using Jsoup
     * 
     * @return list of data from the table by using Jsoup
     * @throws IOException 
     */
    public List<String> read() throws IOException{
        List<String> list = new ArrayList<>();
        Document doc = Jsoup.parse(fioURL, 5000);
        Element table = doc.select("table[class=main]").first();
        Iterator<Element> it = table.select("td").iterator();
        
        while (it.hasNext()) {
            String text = it.next().text();
            if(text.equals("Suma"))
                break;
            list.add(text);
        }
        return list;
    }
}
