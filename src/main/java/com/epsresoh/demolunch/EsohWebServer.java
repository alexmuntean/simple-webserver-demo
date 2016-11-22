package com.epsresoh.demolunch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			System.out.println("Received from client:" + clientText);
		}

		clientSocket.close();
		serverSocket.close();

		System.out.println("We're closed! Bye!");
	}
}
