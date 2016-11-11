package Protocols;

import java.net.URL;

public class Selector {
	public Protocol selectProtocol(URL url){
		if(url.getProtocol().equals("http")){
			return new HttpProtocol();
		}
		else if(url.getProtocol().equals("https")){
			return new HttpsProtocol();
		}
		else if(url.getProtocol().equals("ftp")){
			return new FtpProtocol();
		}
		else 
			return null;
	}
}
