/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package html_to_json;

import java.io.IOException;
import static jdk.nashorn.internal.objects.Global.getJSON;
import net.sf.json.JSONSerializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author FireHawk2300
 */
public class Html_to_jsonTest {
    
    public Html_to_jsonTest() throws IOException {
        
        String html = Jsoup.connect("https://github.com/egis/handbook/blob/master/Tech-Stack.md").get().html();
        
        Document doc = Jsoup.parse(html);
        
    }
    // Method used to verify JSON format
    // Returns boolean result
    public static boolean isValidJSON(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
    
    @Test
    public void checkConn() throws IOException {
        //Tests connection to url
        Connection conn = Jsoup.connect("https://github.com/egis/handbook/blob/master/Tech-Stack.md"); 
        conn.get(); // would throw exception on error
        assertTrue(true);
    }
    
    
    @Test
    public void checkRedirects() {
        //Tests for stable connection
        boolean conn = true;
        try {
            Document doc = Jsoup.connect("https://github.com/egis/handbook/blob/master/Tech-Stack.md").get();
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("Too many redirects"));
            conn = false;
        }
        assertTrue(conn);
    }
    
    @Test
    public void checkCharset() throws IOException {
        //Tests that we get back text/html in a "UFT8" character set
        String url = "https://github.com/egis/handbook/blob/master/Tech-Stack.md";
        Connection.Response res = Jsoup.connect(url).execute();
        assertEquals("text/html; charset=utf-8", res.header("Content-Type"));
        Document doc = res.parse();
        assertTrue(doc.text().contains("Tech-Stack"));
        assertEquals("UTF-8", res.charset());
    }
    
    @Test
    public void testMain() throws IOException {
        //Tests our Main method in Html_to_json.class
        // Declare boolean vars used throughout test to verify JSON format
        boolean ans;
        boolean val;
        
        //JSOUP connects to URL and gets HTML
        String html = Jsoup.connect("https://github.com/egis/handbook/blob/master/Tech-Stack.md").get().html();
        Document doc = Jsoup.parse(html);
        
        //Check that we have connected to the right page
        assertEquals("handbook/Tech-Stack.md at master · egis/handbook · GitHub", doc.title());
        
        //Gets all <h2> tags
        Element divID = doc.getElementById("readme");
        Elements h2Tags = divID.getElementsByTag("h2");
        
        //Loop through each heading
        for (int a = 0, b = h2Tags.size(); a < b; a++) {
            int headNum = a+1;
            Elements heading = h2Tags.eq(a).select("h2");
            JSONObject jsonHeadObject = new JSONObject();
            jsonHeadObject.put("Heading:"+headNum, heading.text());
            
            //Verify correct JSON conversion
            ans = true;
            val = isValidJSON(jsonHeadObject.toString());
            
            assertEquals(ans,val);
        
            //Verify Correct headings
            if (a == 0) {
                JSONAssert.assertEquals("{\"Heading:1\":\"Programming Stack\"}", jsonHeadObject.toString(), true);
            }
            
            if (a == 1) {
                JSONAssert.assertEquals("{\"Heading:2\":\"Build Stack\"}", jsonHeadObject.toString(), true);
            }
            
            if (a == 2) {
                JSONAssert.assertEquals("{\"Heading:3\":\"Infrastructure\"}", jsonHeadObject.toString(), true); 
            }
            
            if (a == 3) {
                JSONAssert.assertEquals("{\"Heading:4\":\"Monitoring\"}", jsonHeadObject.toString(), true);
            }
            
            Element tBody = doc.select("tbody").get(a);
            Elements row = tBody.getElementsByTag("tr");
            
            //Loop that builds each row
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
                //Verify correct JSON conversion
                ans = true;
                val = isValidJSON(jsonObject.toString());
                
                assertEquals(ans,val);
            }
           
        }
        
        
    }
    
}
