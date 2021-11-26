package io.horizon.ftdc.launch;

import io.mercury.transport.zmq.ZmqPublisher;

public class FtdcZmqModule implements Runnable {

	@SuppressWarnings("unused")
	private ZmqPublisher<String> publisher;

	public FtdcZmqModule() {

	}

	@Override
	public void run() {

	}

}
