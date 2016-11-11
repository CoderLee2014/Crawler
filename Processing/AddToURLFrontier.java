package Processing;

import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import URLSeenTest.SeenTest;
import frontier.URLFrontier;

public class AddToURLFrontier {
	public  void addURLToFrontier(Set<URL> result){
		int num_thread = Math.toIntExact(Thread.currentThread().getId())%100;  
		System.out.println("crawler "+ num_thread+" is adding urls to frontiers");
		Iterator<URL> it = result.iterator();
		while(it.hasNext()){
			URL url = it.next();
			URLFrontier frontier = new URLFrontier();
			frontier.push(url);
		}
		System.out.println("crawler "+ num_thread+"  finished adding urls to frontiers");
	}
}
