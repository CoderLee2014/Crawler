package Crawler;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

import Processing.AddToURLFrontier;
import Processing.Extractor;
import Processing.Storage;
import Protocols.*;
import URLSeenTest.BloomFilter;
import URLSeenTest.SeenTest;
import frontier.HeapNode;
import frontier.URLFrontier;


public class Crawler implements Runnable {
	
	//Nombre de crawlers, value defaute est 100, indique qu'il y a 100 threads
	public static int num_crawlers = 100;

	
	public static long sleep_time = 5000;
	
	//RewindInputStream, RIS per-thread
	public static RewindInputStream ris;
	
	//La liste d'urls à visiter
	public static List<URL> urls_toVisit;
	
	//Paramètres pour le BloomFilter
	public static double falsePositiveProbability = 0.5;
	public static int expectedNumberOfElements = 5000;
	public static BloomFilter bf = new BloomFilter(falsePositiveProbability,expectedNumberOfElements);
	
	public void run() {
		// TODO Auto-generated method stub
		try {
				System.out.println("crawler "+Thread.currentThread().getId()%num_crawlers+" start crawling");
			    crawling();
	
			System.out.println("crawler "+Thread.currentThread().getId()%num_crawlers+" finished his work.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean crawling() throws IOException{
		

		int num_thread = Math.toIntExact(Thread.currentThread().getId())%num_crawlers; 
		System.out.println("Crawler "+ num_thread + " is crowling");
		URLFrontier frontier = new URLFrontier();
		
		URL url = null;
		while((url=frontier.pop())==null);
		
		//System.out.println(url.toString());
		StringBuilder is = null;
		Protocol pro = new Selector().selectProtocol(url);
		System.out.println("Crawler " + num_thread + " is fetching");
		is = pro.fetch(url);
		if(is==null){ 
			System.out.println("can't get "+url.toString());
			return false;
		}
		Extractor extractor = new Extractor();
		
		SeenTest seen_test = new SeenTest();
		Set<URL> result = seen_test.filter(extractor.extract(is.toString(), url.toString()), bf);
		
		AddToURLFrontier additor = new AddToURLFrontier();
		additor.addURLToFrontier(result);
		System.out.println("Crawler " + num_thread + " has added "+result.size()+" urls to frontier");
		
		Storage storage = new Storage();
		storage.write_html(Thread.currentThread().getName(), is.toString());
		System.out.println("Crawler " + num_thread + " has written a doc.");
		return true;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException{
		
		//initialiser le URLFrontier 
		System.out.println("start to crawl");
		URLFrontier frontier = new URLFrontier();
		frontier.seed_urls.addAll(SeedsReader.readSeedsFile("seeds.txt", bf));
		System.out.println(frontier.seed_urls.size());
		frontier.createPriorityQueue();
		//frontier.createTimeHeap();
		
//		new Test().main(null);
		ExecutorService executor = Executors.newFixedThreadPool(num_crawlers);
		for(int i=0; i <100000000; i++){
			executor.submit(new Crawler());
        }
		executor.shutdown();
		System.out.println("end");
	}
	
}
