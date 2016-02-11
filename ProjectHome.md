_The project will focus on creating a scalable crawler with robust data mining as well as machine learning capabilities._
## News ##
**05th July 2011** : Crowl 0.11 Released

**19th May 2011** : Crowl 0.1 Released

## Introduction ##

At Present Crowl can only crawl RSS and ATOM feeds. It does not have any distributed capabilities. Also it is lacking any good machine learning functionality.

_Crowl 0.11 has following features:_

  * It can crawl a single feed url e.g. "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&output=rss"
  * It can crawl a collection of feed urls passed as a List of URLs
  * Gives HTML free content of a feed entry.
  * Gives Image urls in a feed
  * It can store the crawled feeds in MongoDB
  * It can store the image thumbnails found in feeds in a local directory specified through config.properties
  * config.properties file can be used to specify image thumbnail dimensions and mongoDB server properties

## Getting Started ##
[Find It Here](http://code.google.com/p/crowl/wiki/GetStarted)

## Future Work (0.2 release) ##

  * Provide Javadocs
  * Better Exception Handling
  * Implement url revisit policy based on feed change rate
  * Group similar feed items _(partially implemented in 0.11)_
  * Respect robot.txt policy _(partially implemented in 0.11)_
  * Support for focused crawling (topical crawling)
  * Inclusion of jsoup

&lt;wiki:gadget url="http://www.ohloh.net/p/584905/widgets/project\_basic\_stats.xml" height="220" border="1"/&gt;