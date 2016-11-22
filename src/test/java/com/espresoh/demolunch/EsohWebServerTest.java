package com.espresoh.demolunch;

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
}
