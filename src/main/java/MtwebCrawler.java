import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class MtwebCrawler {

    public static void main(String[] args) {
        ArrayList<WebCrawler> bot = new ArrayList<>();
        bot.add(new WebCrawler("https://www.honda2wheelersindia.com/campaign-shine-BS-VI?utm_source=smart&utm_medium=cpc&utm_campaign=shine_smart&utm_term=paid&gclid=Cj0KCQjwu-KiBhCsARIsAPztUF0MsiCawWg04f2OXx1QnBOX0FAJ3J8jc5RwTPzwGxhnvah_UvQggLIaAp4nEALw_wcB"));
        bot.add(new WebCrawler("https://www.suzukimotorcycle.co.in/"));

        for(WebCrawler u:bot)
        {
            try{
            u.getThread().join();
           u.getThread_internal().join();
//                Thread internalThread = u.getThread_internal();
//                if (internalThread != null) {
//                    internalThread.join();
//                }
            }catch(InterruptedException e)
            {
                e.printStackTrace();
            }

        }
        //WebCrawler wc= new WebCrawler();
        Set<String> visitedUrl= WebCrawler.visitedUrl;

        try{
            FileWriter fileWriter = new FileWriter("E:\\url.txt");
            for(String url :visitedUrl)
            {
                fileWriter.write(url+"\n");
            }
            fileWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
