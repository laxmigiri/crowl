# Usage #
Following code snippet parses a single rss feed:
```
 public static void main(String[] args){
        ICrawler crawler = new FeedCrawler();
        try {
            List<FeedEntry> lis =crawler.crawlSingleUrl(new URL("http://feeds.feedburner.com/basho/knDR"));
            for(FeedEntry fe: lis)
            {                
                SyndEntry se = fe.getFeedEntry();                            
                System.out.println("Title: "+se.getTitle()+"\n"+"Content: "+fe.getNoHtmlContent()+"\n"+"Image URL: "+fe.getFeedImageUrl());
            }
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    }
```

SyndEntry is a [ROME](http://java.net/projects/rome) interface and contains main information related to a feed entry. More detail can be found [here](http://java.net/projects/rome/sources/svn/content/trunk/src/java/com/sun/syndication/feed/synd/SyndEntry.java?rev=840)

For parsing a list of urls use:

```
ICrawler crawler = new FeedCrawler();
ArrayList<String> urlList = new ArrayList<String>();
ConcurrentHashMap<String, List<FeedEntry>> feedMap=crawler.crawlUrls(urlList);
```

## OR ##

```
ICrawler crawler = new FeedCrawler();
ArrayList<URL> urlList = new ArrayList<URL>();
ConcurrentHashMap<String, List<FeedEntry>> feedMap=crawler.crawlUrls(urlList);
```

## Below is a simple example ##

```

public static void main(String[] args){
        ICrawler crawler = new FeedCrawler();
        try {
            ArrayList<String> urlList = new ArrayList<String>();
            urlList.add("http://feeds.feedburner.com/basho/knDR");
            urlList.add("http://feeds.feedburner.com/nosql");
            urlList.add("http://www.java.net/blog/120355/feed");
            ConcurrentHashMap<String, List<FeedEntry>> feedMap =crawler.crawlUrls(urlList);
            Iterator itr = feedMap.keySet().iterator();
            while(itr.hasNext()){
                String url = itr.next().toString();
                List<FeedEntry> feed = feedMap.get(url);
                System.out.println("Feed(RSS/ATOM) URL: "+url);
                for(FeedEntry fe: feed)
                {                
                    SyndEntry se = fe.getFeedEntry();                            
                    System.out.println("Title: "+se.getTitle()+"\n"+"Content: "+fe.getNoHtmlContent()+"\n"+"Image URL: "+fe.getFeedImageUrl());
                }
            }
            
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    }

```