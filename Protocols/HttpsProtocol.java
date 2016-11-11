package Protocols;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HttpsProtocol extends Protocol{
	@Override
	public StringBuilder fetch(URL url) {
		// TODO Auto-generated method stub
		try {
			int num_thread = Math.toIntExact(Thread.currentThread().getId())%100;
			System.out.println("crawler "+ num_thread +"starting to fecth an url");
			HttpsURLConnection connection =  (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);

			int code = connection.getResponseCode();
		    //System.out.println("code="+code);
			if(code!=200) return null;
		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    
		    StringBuilder result = new StringBuilder();
		    String line = null;
		    while((line = rd.readLine())!=null){
		    	result.append(line);
		    }
		    System.out.println("crawler "+ num_thread +" finished fecth an url");
		    return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	public URL newUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}
}
