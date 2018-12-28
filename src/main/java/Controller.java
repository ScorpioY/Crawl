import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 3;
        int maxDepthOfCrawling = 2;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(maxDepthOfCrawling);
        config.setMaxDownloadSize(10485760);
        config.setMaxOutgoingLinksToFollow(10000);

        //config.setProxyHost("127.0.0.l");
        //config.setProxyPort(7070);

        /*
         * 实例化爬虫控制器
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("http://www.ubiloc.cn/english/aaa.html");
        controller.addSeed("http://www.ubiloc.cn/english/bbb.html");
        controller.addSeed("http://www.ubiloc.cn/english/ccc.html");
        //controller.addSeed("http://www.ubiloc.cn/english/zzz.html");
        //controller.addSeed("http://www.ubiloc.cn/english/###.html");
        //controller.addSeed("http://www.ubiloc.cn/english/uuu.html");
        //controller.addSeed("http://www.ubiloc.cn/english/vvv.html");


        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MyCrawler.class, numberOfCrawlers);


    }
}
