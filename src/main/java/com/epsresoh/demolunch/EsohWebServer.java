package com.epsresoh.demolunch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.stream.Collectors;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.epsresoh.demolunch.exception.PageNotFoundException;

public class EsohWebServer {
	private ScriptEngineManager factory = new ScriptEngineManager();

	private final int port;

	public EsohWebServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		@SuppressWarnings("resource")
		final ServerSocket serverSocket = new ServerSocket(port);

		while (true) {
			final Socket clientSocket = serverSocket.accept();
			InputStream clientSocketInputStream = clientSocket.getInputStream();

			new Thread(() -> {
				try {
					InputStreamReader inputStreamReader = new InputStreamReader(clientSocketInputStream);
					BufferedReader reader = new BufferedReader(inputStreamReader);

					String uriRequested = null;
					String clientText;
					while ((clientText = reader.readLine()) != null && !clientText.isEmpty()) {
						if (clientText.startsWith("GET")) {
							String[] clientTextArray = clientText.split(" ");
							uriRequested = clientTextArray[1];
						}
					}

					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

					try {
						String response = getResponse(uriRequested);

						writer.println("HTTP/1.1 200 OK");
						writer.println("Date: Tue, 22 Nov 2016 16:21:12 GMT");
						writer.println("Server: Apache");
						writer.println("Expires: Thu, 19 Nov 1981 08:52:00 GMT");
						writer.println("Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
						writer.println("Pragma: no-cache");
						writer.println("Content-Length: " + response.getBytes().length);
						writer.println("X-Powered-By: PleskLin");
						writer.println("Connection: close");
						writer.println("Content-Type: text/html; charset=UTF-8");
						writer.println("");
						writer.println(response);
						writer.flush();

					} catch (PageNotFoundException e) {
						String pageContent = getPageAsString("/404.html");

						writer.println("HTTP/1.1 404 Not Found");
						writer.println("Date: Tue, 22 Nov 2016 16:21:12 GMT");
						writer.println("Server: Apache");
						writer.println("Expires: Thu, 19 Nov 1981 08:52:00 GMT");
						writer.println("Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
						writer.println("Pragma: no-cache");
						writer.println("Content-Length: " + pageContent.getBytes().length);
						writer.println("X-Powered-By: PleskLin");
						writer.println("Connection: close");
						writer.println("Content-Type: text/html; charset=UTF-8");
						writer.println("");
						writer.println(pageContent);
						writer.flush();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}

	private String getPageAsString(String path) throws IOException {
		try (InputStream resourceStream = getClass().getResourceAsStream(path)) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));

			return reader.lines().collect(Collectors.joining("\n"));
		}
	}

	private String getResponse(String uriRequested) throws IOException {
		if (uriRequested == null) {
			return "Nothing requested";
		}

		switch (uriRequested) {
		case "/":
			return "<html>This is index page</html>";
		case "/greeting":
			return "<html>Greetings my friend. All we know is time " + Calendar.getInstance().getTime()
					+ ". (: <b>(Pfeiffer's Smile)</b></html>";
		case "/ruby":
			return executeRuby(getPageAsString("/ruby.rb"));
		case "/js":
			return executeJavascript(getPageAsString("/javascript.js"));
		default:
			throw new PageNotFoundException(uriRequested);
		}
	}

	private String executeJavascript(String jsCode) {
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		ScriptContext js = engine.getContext();

		StringWriter writer = new StringWriter();
		js.setWriter(writer);
		try {
			engine.eval(jsCode);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		return writer.toString();
	}

	private String executeRuby(String pageAsString) {
		ScriptEngine engine = factory.getEngineByName("jruby");
		ScriptContext jruby = engine.getContext();

		StringWriter writer = new StringWriter();
		jruby.setWriter(writer);
		try {
			engine.eval(pageAsString);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
}
