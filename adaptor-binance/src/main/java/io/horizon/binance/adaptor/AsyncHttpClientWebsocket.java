package io.horizon.binance.adaptor;

import static org.asynchttpclient.Dsl.*;

import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.ws.WebSocket;
import org.asynchttpclient.ws.WebSocketListener;
import org.asynchttpclient.ws.WebSocketUpgradeHandler;

/**
 * Hello world!
 *
 */
public class AsyncHttpClientWebsocket {
	public static void main(String[] args) {

		AsyncHttpClient c = asyncHttpClient();

		try {
			WebSocket websocket = c.prepareGet("ws://demos.kaazing.com/echo")
					.execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(new WebSocketListener() {

						@Override
						public void onOpen(WebSocket websocket) {
							websocket.sendTextFrame("...");
						}

						@Override
						public void onClose(WebSocket websocket, int code, String reason) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextFrame(String payload, boolean finalFragment, int rsv) {
							System.out.println(payload);
						}

						@Override
						public void onError(Throwable t) {
						}

					}).build()).get();

			System.out.println(websocket);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
