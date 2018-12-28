import com.google.common.collect.Lists;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyCrawler extends WebCrawler {
    /**
     * 正则表达式匹配指定的后缀文件
     */
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    /**
     * 这个方法主要是决定哪些url我们需要抓取，返回true表示是我们需要的，返回false表示不是我们需要的Url
     * 第一个参数referringPage封装了当前爬取的页面信息 第二个参数url封装了当前爬取的页面url信息
     * 在这个例子中，我们指定爬虫忽略具有css，js，git，...扩展名的url，只接受以“http://www.ics.uci.edu/”开头的url。
     * 在这种情况下，我们不需要referringPage参数来做出决定
     */

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return href.startsWith("https://ces19.mapyourshow.com/7_0/exhibitor/exhibitor-details.cfm?");
    }

    /**
     * 当一个页面被提取并准备好被你的程序处理时，这个函数被调用。
     */

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            if(page.getWebURL().getURL().startsWith("https://ces19.mapyourshow.com/7_0/exhibitor/exhibitor-details.cfm?ExhID="))
            {
                HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                String html = htmlParseData.getHtml();
                Document document = Jsoup.parse(html);

                Elements exhibitorAddress = document.getElementsByClass("sc-Exhibitor_Address");

                Element companyNameElement = document.getElementById("jq-sc-Mobile-ExhName");
                Elements companyName = companyNameElement.getElementsByTag("h1");

                Elements phoneNum = document.getElementsByClass("sc-Exhibitor_PhoneFax");

                Elements webSite = document.getElementsByClass("sc-Exhibitor_Url");

                Elements floorPlanLocation = document.getElementsByClass("mys-floorPlanLink");

                Elements briefDescription = document.getElementsByClass("mys-taper-measure");

                Elements detailInfo = document.getElementsByClass("mys-toggle");

                for (Element item : detailInfo) {
                    if(item.getElementsByTag("a").text().contains("Company Contacts")){
                        System.out.println("Company Contacts:" + item.getElementById("ul").text());
                    }
                }
                System.out.println("company name: " + companyName.text());
                System.out.println("phone num:" + phoneNum.text().replace("P:",""));
                System.out.println("web site:" + webSite.text());
                System.out.println("floor plan:" + floorPlanLocation.get(0).text());
                System.out.println("briefDescription:" + briefDescription.text());
                System.out.println("exhibitorAddress:" + exhibitorAddress.text());

                Map<String, String> dataMap=new HashMap<String, String>();
                dataMap.put("Company_Name",companyName.text());
                dataMap.put("Exhibitor_Address",exhibitorAddress.text());
                dataMap.put("Phone_Number",phoneNum.text());
                dataMap.put("WebSite",webSite.text());
                dataMap.put("FloorPlan_location",floorPlanLocation.text());
                dataMap.put("Company_Contacts",detailInfo.text());
                dataMap.put("Company_Brief_Description",briefDescription.text());
                dataMap.put("Email_Address",exhibitorAddress.text());

                //ExcelUtil.writeExcel(dataMap);
                SqlUtil.executeUpdate(dataMap);
            }
        }
    }
}
