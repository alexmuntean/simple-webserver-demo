package com.espresoh.demolunch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.Test;

public class EsohWebServerTest {

	@Test
	/**
	 * This is not an actual test. This is used only to prove visually that
	 * server is working
	 * 
	 * @throws Exception
	 */
	public void testMessagesReceived() throws Exception {
		Socket socket = new Socket("localhost", 8080);
		PrintWriter writer = new PrintWriter(socket.getOutputStream());

		writer.println("salut");
		writer.println("uite aici un mesaj");
		writer.flush();

		writer.close();
		socket.close();
	}

	@Test
	public void testCheckWhatEsohResponds() throws Exception {
		Socket socket = new Socket("e-spres-oh.com", 80);
		PrintWriter writer = new PrintWriter(socket.getOutputStream());

		writer.println("GET / HTTP/1.1");
		writer.println("Host: e-spres-oh.com");
		writer.println("Connection: keep-alive");
		writer.println("Upgrade-Insecure-Requests: 1");
		writer.println(
				"User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36");
		writer.println("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		writer.println("Accept-Encoding: gzip, deflate, sdch, br");
		writer.println("Accept-Language: en-US,en;q=0.8,ro;q=0.6,de;q=0.4");
		writer.println("Cookie: PHPSESSID=eqpvvhtmrq0oc0546livu854a3");
		writer.println("");
		writer.flush();
		

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		socket.close();
		/* RESPONDS WITH
		    HTTP/1.1 200 OK
			Date: Tue, 22 Nov 2016 16:21:12 GMT
			Server: Apache
			Expires: Thu, 19 Nov 1981 08:52:00 GMT
			Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0
			Pragma: no-cache
			Content-Length: 7713
			X-Powered-By: PleskLin
			Connection: close
			Content-Type: text/html; charset=UTF-8
		 */
	}
}
