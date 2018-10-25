package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class InputManagerThread extends Thread{
	
	private Socket server;
	
	public InputManagerThread(Socket s) {
		server = s;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = "";
		try {
			while((message = in.readLine()) != null) {
				String identifier = getIdentifier(message);
				message = message.substring(identifier.length() + 1, message.length());
				Wrapper w = searchForWrapper(TicTacToeClient.getInstance().getWrapperList(), identifier);
				if(isSingleplayer(identifier)) {
					if(message.contains("GAMEOVER")) {
						w.setBoard(null);
						w.setWinner(message.substring(message.length()-1, message.length()));
					} else {
						w.setBoard(new Board(message));
					}
					w.setOkToPlay(true);
					w.setUpdatedBoard(true);
				} else {
					if(message.contains("CONNECT")) {
						String connectionStatus = message.substring(8, 10);
						w.setConnection(connectionStatus);
						if(connectionStatus.equals("OK")) {
							w.setBoard(new Board(message.substring(11, message.length()-2)));
							if(message.substring(message.length()-2, message.length()).equals("OK"))
								w.setOkToPlay(true);
							else if(message.substring(message.length()-2, message.length()).equals("WA")) {
								w.setOkToPlay(false);
								w.setConnection("WA");
							}
							else
								w.setOkToPlay(false);
						}
					} 
					else if(message.contains("GAMEOVER")) {
						w.setBoard(null);
						w.setWinner(message.substring(message.length()-1, message.length()));
					} else {
						w.setBoard(new Board(message.substring(0, message.length()-2)));
						System.out.println(w.getBoard());
						if(message.substring(message.length()-2, message.length()).equals("OK"))
							w.setOkToPlay(true);
						else
							w.setOkToPlay(false);
					}
					w.setUpdatedBoard(true);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean isSingleplayer(String identifier) {
		if(identifier.contains("-"))
			return false;
		return true;
	}

	private String getIdentifier(String inputLine) {
		Scanner scan = new Scanner(inputLine);
		String identifier = scan.next();
		scan.close();
		return identifier;
	}

	private Wrapper searchForWrapper(List<Wrapper> wrapperList, String identifier) {
		for (Wrapper w : wrapperList) {
			if (w.getIdentifier().equals(identifier))
				return w;
		}
		return null;
	}
	
}
