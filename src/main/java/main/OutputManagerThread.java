package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class OutputManagerThread extends Thread{
	
	private Socket server;
	private List<String> messageList = new ArrayList<>();
	
	public OutputManagerThread(Socket s) {
		server = s;
	}
	
	@Override
	public void run() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(server.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			while(messageList.isEmpty()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			out.println(messageList.remove(0));
		}

	}
	
	public List<String> getMessageList() {
		return messageList;
	}
	
}
