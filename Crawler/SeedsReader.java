package Crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Processing.Extractor;
import Processing.PageReader;
import URLSeenTest.BloomFilter;
import URLSeenTest.SeenTest;

public class SeedsReader {
	public static Set<URL> readSeedsFile(String path, BloomFilter bf) throws IOException{
		Set<URL> result = new HashSet();
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line;
		while((line=in.readLine())!=null){
			result.add(new URL(line));
			result.addAll(Extractor.extractSeeds(new PageReader().read(line), line));
		}
		SeenTest seen_test = new SeenTest();
		
		return seen_test.filter(result,bf);
	}
}
