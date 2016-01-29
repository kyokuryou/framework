package org.smarty.core.support.net;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 */
public abstract class SocketMonitor {
	private InetAddress inetAddress;
	private int port;

	public SocketMonitor(int port) throws UnknownHostException {
		this.inetAddress = InetAddress.getLocalHost();
		this.port = port;
	}

	public SocketMonitor(String host, int port) throws UnknownHostException {
		this.inetAddress = InetAddress.getByName(host);
		this.port = port;
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public final int getPort() {
		return port;
	}

	public abstract void acceptSocket(Socket socket);
}
