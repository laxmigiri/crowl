/**
 * 
 */
package org.crow.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.crow.classes.FeedEntry;
import org.crow.utils.Constants;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

/**
 * @author viksin
 *
 */
public class InsertAndUpdateMongoDb implements InsertAndUpdateOpsInterface {

	@Override
	public boolean multiInsert() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean singleInsert() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateDB() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.crow.data.InsertAndUpdateOpsInterface#insertFeeds(com.sun.syndication.feed.synd.SyndFeed)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean insertFeeds(List<FeedEntry> feedList) {
		try {
			Mongo m = new Mongo(Constants.MongoDBServer,Constants.MongoDBServerPort);
			DB db = m.getDB("test");
			DBCollection coll = db.getCollection("tfeeds");
			

			for (FeedEntry fe : feedList) {
				SyndEntry entry = fe.getFeedEntry();
				DCModule entrydc = (DCModule) entry.getModule(DCModule.URI);
				DBObject feed = new BasicDBObject();
				
				feed.put("source", fe.getSourceTitle());
				feed.put("sourcelink", fe.getSourceLink());
				feed.put("getdate", fe.getFeedGetDateTime());
				feed.put("hashid", fe.getFeedHashid());
				//coll.ensureIndex(feed,"hashid", true);
				DBObject feedData = new BasicDBObject();
				feedData.put("title", entry.getTitle());
				if (entry.getLink() != null) {
					feedData.put("link", entry.getLink());
				} 
				else if (entry.getLinks().size() > 0) {
					Iterator links = entry.getLinks().iterator();
					while (links.hasNext()) {
						SyndLink link = (SyndLink) links.next();
						if (link.getRel().equals("alternate"))
							feedData.put("link", link.toString());
					}
				}

				if (entry.getContents().size() > 0) {
					int i = 1;
					Iterator contents = entry.getContents().iterator();
					while (contents.hasNext()) {
						SyndContent content = (SyndContent) contents.next();
						feedData.put("description" + i, content.getValue());
						i++;
					}
				} else if (entry.getDescription() != null) {
					feedData.put("description1", entry.getDescription().getValue());
				}

				if (entry.getAuthor() != null) {
					feedData.put("author", entry.getAuthor());
				} else if (entrydc.getCreator() != null) {
					feedData.put("author", entrydc.getCreator());
				}
				if (entry.getPublishedDate() != null) {
					feedData.put("publishdate", entry.getPublishedDate().toString());
				} else if (entrydc.getDate() != null) {
					feedData.put("publishdate", entrydc.getDate().toString());
				}
				String categories = "";
				if (entry.getCategories().size() > 0) {
					Iterator cats = entry.getCategories().iterator();
					while (cats.hasNext()) {
						SyndCategory cat = (SyndCategory) cats.next();
						categories += cat.getName() + ",";
					}
				}
				feedData.put("categories", categories);
				feedData.put("updatedate", entry.getUpdatedDate());
				feedData.put("nohtmlcontent", fe.getNoHtmlContent());
				feedData.put("imageurl", fe.getFeedImageUrl());
				feed.put("feeddata", feedData);
				coll.insert(feed);
				//DBCursor cur = coll.find();
				
				//System.out.println(coll.genIndexName(feed));
			}
			DBCursor cur = coll.find();
			while (cur.hasNext()) {
				System.out.println(cur.next());
			}
			System.out.println(coll.getCount());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	

}