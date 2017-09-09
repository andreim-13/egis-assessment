package html_to_json;

import java.io.IOException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author FireHawk2300
 */
public class Html_to_json {

    public static void main(String[] args) throws IOException {
        
        //JSOUP connects to URL and gets HTML        
        String html = Jsoup.connect("https://github.com/egis/handbook/blob/master/Tech-Stack.md").get().html();
                
        //Parse retrieved html to JSOUP for interpretation
        Document doc = Jsoup.parse(html);
        
        //Interrogates <div id="readme"> tag in html and gets its <h2> tags
        Element divID = doc.getElementById("readme");
        Elements h2Tags = divID.getElementsByTag("h2");
        
        //Loops through each heading <h2>                
        for (int a = 0, b = h2Tags.size(); a < b; a++) {
            int headNum = a+1;
            Elements heading = h2Tags.eq(a).select("h2");
            JSONObject jsonHeadObject = new JSONObject();
            jsonHeadObject.put("Heading:"+headNum, heading.text());
            System.out.println(jsonHeadObject.toString());
            
            //Interrogates <tbody> tag in html for every <h2> tag and gets its <tr> rows
            Element tBody = doc.select("tbody").get(a);
            Elements row = tBody.getElementsByTag("tr");
                
            //Loops through each row
            for (int i = 0, l = row.size(); i < l; i++) {
                //re-initialise for each object (row)                   
                JSONObject jsonObject = new JSONObject();
                
                //Interrogates <table> tag in html for every <h2> tag and gets heading <th> for each column
                Element table = doc.select("table").get(a);
                Elements tHead = table.getElementsByTag("th");                    
                Elements cols = row.eq(i).select("td");
                    
                //Loops though each heading for each row
                for (int j = 0, k = tHead.size(); j <k; j++) {
                    Elements th = tHead.eq(j).select("th");
                    String colCont = cols.get(j).text();
                    jsonObject.put(th.text(), colCont);
                }                
                //Displays each row on a new line
                System.out.println(jsonObject.toString());                    
            }
            //End of each <h2> tag
            if (headNum < h2Tags.size()) {                
                System.out.println();
                System.out.println();
            }
            
        }
        
    }
        
    
}
