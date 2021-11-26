package movieproject.server;

import movieproject.server.datacommunication.ServerHandler;

public class ServerLaunch {

	public static void main(String[] args) {

		ServerHandler serverHandler = new ServerHandler();
		serverHandler.startServer();

	}

}
