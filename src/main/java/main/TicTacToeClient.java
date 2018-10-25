package main;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeClient {

	public static String address = "127.0.0.1";
	public static int port = 4444;
	public static String fromServer;
	public static String toServer;
	static Socket socket = null;
	static PrintWriter out = null;
	static BufferedReader in = null;
	static Board b = null;
	static String winner = "";

	private List<Wrapper> wrapperList = new ArrayList<>();

	// public static void main(String[] args) {
	// try {
	// Client();
	// } catch(IOException e) {
	// e.printStackTrace();
	// }
	// }

	OutputManagerThread o = null;
	InputManagerThread i = null;

	public void sendMessage(String message) {
		o.getMessageList().add(message);
	}

//	public void readBoard() {
//		try {
//			System.out.println("trying to read from board");
//			if ((fromServer = in.readLine()) != null) {
//				System.out.println(fromServer);
//				if (fromServer.contains("GAMEOVER")) {
//					b = null;
//					winner = fromServer.substring(fromServer.length() - 1, fromServer.length());
//				} else
//					b = new Board(fromServer);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void doMove(Move move, Wrapper wrapper) {
		o.getMessageList().add(wrapper.getIdentifier() + " " + move.toString());

		// System.out.println(b.printBoard());
		// Scanner scan = new Scanner(System.in);
		// Move move = getInput(b, scan);
		// b.doMove(move.getRow(), move.getCol());
		// System.out.println(b.printBoard());
		// out.println(move.toString());
		// scan.close();
	}

	public Board getBoard() {
		return b;
	}

	public static TicTacToeClient getInstance() {
		if (instance == null) {
			instance = new TicTacToeClient();
		}
		return instance;
	}

	private static TicTacToeClient instance;

	private TicTacToeClient() {
		try {
			socket = new Socket(address, port);

			o = new OutputManagerThread(socket);
			o.start();
			i = new InputManagerThread(socket);
			i.start();

			// out = new PrintWriter(socket.getOutputStream(), true);
			// in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + address);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + address);
			System.exit(1);
		}
	}

	// public static TicTacToeClient instance = null;
	//
	// public static TicTacToeClient getInstance() {
	// if (instance == null) {
	// try {
	// socket = new Socket(address, port);
	// out = new PrintWriter(socket.getOutputStream(), true);
	// in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	// } catch (UnknownHostException e) {
	// System.err.println("Don't know about host: " + address);
	// System.exit(1);
	// } catch (IOException e) {
	// System.err.println("Couldn't get I/O for the connection to: " + address);
	// System.exit(1);
	// }
	// instance = new TicTacToeClient();
	// }
	// return instance;
	// }

	// public static void Client() throws IOException {
	// Socket socket = null;
	// PrintWriter out = null;
	// BufferedReader in = null;
	//
	// try {
	// socket = new Socket(address, port);
	// out = new PrintWriter(socket.getOutputStream(), true);
	// in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	// } catch (UnknownHostException e) {
	// System.err.println("Don't know about host: "+address);
	// System.exit(1);
	// } catch (IOException e) {
	// System.err.println("Couldn't get I/O for the connection to: "+address);
	// System.exit(1);
	// }
	//
	// Scanner scan = new Scanner(System.in);
	//
	// while((fromServer = in.readLine()) != null) {
	//// System.out.println(fromServer);
	// Board b = new Board(fromServer);
	// System.out.println(b.printBoard());
	// Move move = getInput(b, scan);
	// b.doMove(move.getRow(), move.getCol());
	// System.out.println(b.printBoard());
	// out.println(move.toString());
	// }
	// }

	private static Move getInput(Board b, Scanner scan) {
		String input = "";
		do {
			System.out
					.println("Enter the row and column for your move (i.e. 1 9 to go in the top right most position)");
			input = scan.nextLine();
		} while (!isLegalInput(b, input));
		Scanner s = new Scanner(input);
		int first = Integer.parseInt(s.next());
		int second = Integer.parseInt(s.next());
		s.close();
		return new Move(first - 1, second - 1);
	}

	private static boolean isLegalInput(Board b, String input) {
		if (input.length() != 3)
			return false;
		String first = input.substring(0, 1);
		String second = input.substring(2, 3);
		if ((first.equals("1") || first.equals("2") || first.equals("3") || first.equals("4") || first.equals("5")
				|| first.equals("6") || first.equals("7") || first.equals("8") || first.equals("9"))
				&& (second.equals("1") || second.equals("2") || second.equals("3") || second.equals("4")
						|| second.equals("5") || second.equals("6") || second.equals("7") || second.equals("8")
						|| second.equals("9"))) {
			if (b.getSymbolAtPos(Integer.parseInt(first) - 1, Integer.parseInt(second) - 1) == ' ' && b
					.findPossibleMoves().contains(new Move(Integer.parseInt(first) - 1, Integer.parseInt(second) - 1)))
				return true;
		}
		return false;
	}

	public String getWinner() {
		return winner;
	}

	public List<Wrapper> getWrapperList() {
		return wrapperList;
	}

	public Wrapper getWrapper(Wrapper wrapper) {
		for (Wrapper w : wrapperList) {
			if (w.equals(wrapper))
				return w;
		}
		return null;
	}

}
