package URLSeenTest;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SeenTest {

	public  Set<URL> filter(Set<URL> urls, BloomFilter bf) {
		// TODO Auto-generated method stub
		Set<URL> result = new HashSet();
		Iterator<URL> it =urls.iterator();
		System.out.println("crawler "+Thread.currentThread().getId() +" Seen Test start");
		while(it.hasNext()){
			URL url = it.next();
			if(bf.contains(url)){
				continue;
			}
			else{
				bf.add(url);
				result.add(url);
			}
		}
		System.out.println("crawler "+Thread.currentThread().getId() +" Seen Test end");
		return result;
	}
	
}
