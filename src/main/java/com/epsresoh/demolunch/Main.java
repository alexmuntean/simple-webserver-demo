package com.epsresoh.demolunch;

public class Main {

	public static void main(String[] args) throws Exception {
		EsohWebServer server = new EsohWebServer(8080);
		server.run();
	}
}
