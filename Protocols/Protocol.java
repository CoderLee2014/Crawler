package Protocols;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

public abstract class Protocol {
	public abstract StringBuilder fetch(URL url);
	public  abstract URL newUrl(String url);
}
