package io.horizon.adaptor.binance;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpProxy;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * 
 * 
 * 
 * @author yellow013
 *
 */
public class JettyWebsocket {

	public static void main(String[] args)  {

		// Instantiate and configure HttpClient.
		HttpClient httpClient = new HttpClient();
		// For example, configure a proxy.
		httpClient.getProxyConfiguration().getProxies().add(new HttpProxy("localhost", 8888));

		// Instantiate WebSocketClient, passing HttpClient to the constructor.
		WebSocketClient webSocketClient = new WebSocketClient(httpClient);
		// Configure WebSocketClient, for example:
		webSocketClient.setMaxTextMessageBufferSize(8 * 1024);

		try {
			// Start WebSocketClient; this implicitly starts also HttpClient.
			webSocketClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
