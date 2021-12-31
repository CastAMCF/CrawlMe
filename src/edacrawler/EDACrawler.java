/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author pedrodias & paulosantos
 */
public class EDACrawler {

    public EDACrawler() throws IOException {
    }

    public Payload process(String url) throws IOException {
        Payload payload = new Payload();

        /*if (!url.endsWith("/")) {
            url += "/";
        }*/

        Document doc = Jsoup.connect(url).get();

        Elements links = doc.select("a");
        Iterator<Element> aux = links.iterator();

        while (aux.hasNext()) {
            String href = aux.next().attr("abs:href");
            if (href.length() > 1) {
            	if(href.contains("../")) {
            		href = href.replace("../","");
            	}
            	if (!payload.links.contains(href)) {
            		payload.links.add(href);
            	}
            }
        }
        
        Elements imgs = doc.select("img");
        aux = imgs.iterator();

        while (aux.hasNext()) {
        	Element elem = aux.next();
            String src = elem.attr("abs:src");
            String title = elem.attr("abs:title");
            String alt = elem.attr("abs:alt");
            if (src.length() > 1) {
            	if(src.contains("../")) {
            		src = src.replace("../","");
            	}
            	if(src.contains("/./")) {
            		src = src.replace("/./","/");
            	}
            	/*if (!payload.imgs.contains(src)) {
            		System.out.println("src: " + src);
            		if(title.length() > 0) {
            			System.out.println("title: " + title.substring(title.lastIndexOf("/")+1, title.length()));
            		}else {
            			System.out.println("title:");
            		}
            		if(alt.length() > 0) {
            			System.out.println("alt: " + alt.substring(alt.lastIndexOf("/")+1, alt.length()));
            		}else {
            			System.out.println("alt:");
            		}
            		
            		payload.imgs.add(src);
            	}*/
            	if (!payload.imgs.containsKey(src)) {
            		//System.out.println("src: " + src);
            		ArrayList<String> attrs = new ArrayList<>();
            		if(title.length() > 0) {
            			//System.out.println("title: " + title.substring(title.lastIndexOf("/")+1, title.length()));
            			attrs.add(title.substring(title.lastIndexOf("/")+1, title.length()));
            		}else {
            			//System.out.println("title:");
            			attrs.add("");
            		}
            		if(alt.length() > 0) {
            			//System.out.println("alt: " + alt.substring(alt.lastIndexOf("/")+1, alt.length()));
            			attrs.add(alt.substring(alt.lastIndexOf("/")+1, alt.length()));
            		}else {
            			//System.out.println("alt:");
            			attrs.add("");
            		}
            		
            		payload.imgs.put(src, attrs);
            	}
            }
        }

        payload.html = doc.html();

        return payload;
    }

}
