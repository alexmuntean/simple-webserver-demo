package com.epsresoh.demolunch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EsohWebServer {

	private final int port;

	public EsohWebServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		ServerSocket serverSocket = new ServerSocket(port);

		Socket clientSocket = serverSocket.accept();
		InputStream clientSocketInputStream = clientSocket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(clientSocketInputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);

		String clientText;
		while ((clientText = reader.readLine()) != null && !clientText.isEmpty()) {
			System.out.println(clientText);
		}

		PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
		writer.println("HTTP/1.1 200 OK");
		writer.println("Date: Tue, 22 Nov 2016 16:21:12 GMT");
		writer.println("Server: Apache");
		writer.println("Expires: Thu, 19 Nov 1981 08:52:00 GMT");
		writer.println("Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
		writer.println("Pragma: no-cache");
		writer.println("Content-Length: 7713");
		writer.println("X-Powered-By: PleskLin");
		writer.println("Connection: close");
		writer.println("Content-Type: text/html; charset=UTF-8");
		writer.println("");
		writer.println("<html>hello world</html>");
		writer.println("");
		writer.flush();
		
		Thread.sleep(1000);
		
		clientSocket.close();
		serverSocket.close();

		System.out.println("We're closed! Bye!");
	}
}
