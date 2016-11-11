package frontier;

import java.io.IOException;
import java.net.URL;

import Crawler.Crawler;
import Crawler.SeedsReader;
import URLSeenTest.BloomFilter;

public class TestFrontier {
	public static void main(String[] args) throws IOException{
		//initialiser le URLFrontier 
				System.out.println("start to crawl");
				URLFrontier frontier = new URLFrontier();
				BloomFilter bf = new BloomFilter(0.1,5000);
				frontier.seed_urls.addAll(SeedsReader.readSeedsFile("seeds.txt", bf));
				System.out.println(frontier.seed_urls.size());
				frontier.createPriorityQueue();
				
				Crawler crawler = new Crawler();
				while(true)
				crawler.crawling();
	}
}
