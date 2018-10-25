package main;

public class Wrapper {
	
	private Board board;
	private String identifier;
	private boolean updatedBoard;
	private boolean okToPlay;
	private String winner;
	private String connection;

	public Wrapper(Board board, String identifier) {
		this.board = board;
		this.identifier = identifier;
		updatedBoard = false;
		winner = "";
		okToPlay = false;
		connection = "";
	}
	
	public synchronized void readBoard() {
		while(!updatedBoard) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		updatedBoard = false;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public boolean isUpdatedBoard() {
		return updatedBoard;
	}

	public void setUpdatedBoard(boolean updatedBoard) {
		this.updatedBoard = updatedBoard;
	}
	
	public String getWinner() {
		return winner;
	}

	public void setWinner(String s) {
		winner = s;
	}

	public boolean isOkToPlay() {
		return okToPlay;
	}

	public void setOkToPlay(boolean okToPlay) {
		this.okToPlay = okToPlay;
	}

	public synchronized String getConnection() {
		while(connection.equals("")) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}
	
}
