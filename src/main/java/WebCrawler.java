//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class WebCrawler implements Runnable {
//    private static final int MaxDepth = 1;
//    private Thread thread;
//    private String first_link;
//    public static ArrayList<String> visitedUrl = new ArrayList<>();
//
//    public WebCrawler() {
//    }
//
//    public WebCrawler(String link)
//    {
//        first_link = link;
//        thread= new Thread(this);
//        thread.start();
//    }
//
//    public void run()
//    {
//        crawl(1,first_link);
//    }
//
//    private void crawl(int level,String url)
//    {
//        if (level<=MaxDepth){
//            Document doc = request(url);
//            if(doc!=null){
//                for(Element link:doc.select("a[href]"))
//                {
//                    String next_link= link.absUrl("href");
//                    if(!visitedUrl.contains(next_link))
//                    {
//                        crawl(level++,next_link);
//                    }
//                }
//            }
//        }
//    }
//    private Document request(String url)
//    {
//        try{
//            Connection con= Jsoup.connect(url);
//            Document doc = con.get();
//
//            if(con.response().statusCode()==200)
//            {
//                System.out.println("received wesite at"+url);
//                visitedUrl.add(url);
//
//                return doc;
//            }
//            return null;
//        }catch(IOException e){
//            return null;
//        }
//    }
//
//    public java.lang.Thread getThread() {
//        return thread;
//    }
//}

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class WebCrawler implements Runnable {
    private static final int MaxDepth = 3;
    private Thread thread;
    private Thread thread_internal;
    private String first_link;
    //abcd


   public static Set<String> visitedUrl = ConcurrentHashMap.newKeySet();


    public WebCrawler(String link)
    {
        first_link = link;
        thread= new Thread(this);
        thread.start();
    }

    public void run()
    {
        crawl(1,first_link);
    }

    private void crawl(int level,String url)
    {
        if (level<=MaxDepth){
            thread_internal=  new Thread(()-> {
                Document doc = request(url);
                if (doc != null) {
                    int current_level = level;
                    for (Element link : doc.select("a[href]")) {
                        String next_link = link.absUrl("href");
                        if (!visitedUrl.contains(next_link)) {
                            System.out.println("received wesite at"+next_link);
                            visitedUrl.add(next_link);
                            crawl(current_level++, next_link);
                        }
                    }
                }
            });
            thread_internal.start();
        }
    }



    private Document request(String url)
    {
        try{

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    // Skip URLs that don't start with "http://" or "https://"
                    return null;
                }
            Connection con= Jsoup.connect(url);
            Document doc = con.get();

            if(con.response().statusCode()==200)
            {

                return doc;
            }
            return null;
        }catch(IOException e){
            return null;
        }
    }

    public java.lang.Thread getThread() {
        return thread;
    }

    public java.lang.Thread getThread_internal() {
        return thread_internal;
    }
}
