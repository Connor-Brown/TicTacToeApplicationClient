package main;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/multiplayer")
public class MultiplayerController {
	
	@RequestMapping(value = { "/", "", "/home" })
	public String home() {
		return "multiplayerhome";
	}
	
	@RequestMapping("/{serverNumber}")
	public String connect(HttpSession session, Model model, @PathVariable("serverNumber") int serverNumber) {
		long id = System.currentTimeMillis();
		String identifier = serverNumber+"-"+id;
		Wrapper wrapper = new Wrapper(new Board(), identifier);
		TicTacToeClient.getInstance().getWrapperList().add(wrapper);
		session.setAttribute("wrapper", wrapper);
		TicTacToeClient.getInstance().sendMessage(wrapper.getIdentifier() + " ");
		String con = TicTacToeClient.getInstance().getWrapper(wrapper).getConnection();
		if(con.equals("OK")) {
			return "multiplayerboard";
		} else if(con.equals("WA")) {
			model.addAttribute("message", "Waiting for opponent to connect");
			return "multiplayerboard";
		}
		else {
			TicTacToeClient.getInstance().getWrapperList().remove(wrapper);
			model.addAttribute("message", "That server is full, try another");
			return "multiplayerhome";
		}		
	}
	
//	@RequestMapping("/move")
//	public String doMove(HttpSession session, Model model, @RequestParam("row") int row, @RequestParam("col") int col) {
//		if(session.getAttribute("wrapper") == null) {
//			return "redirect:/home";
//		}
//		System.out.println(((Wrapper)session.getAttribute("wrapper")).isOkToPlay());
//		if(!((Wrapper)session.getAttribute("wrapper")).isOkToPlay()) {
//			model.addAttribute("message", "Its your opponent's turn");
//			return "multiplayerboard";
//		}
//		if(!isLegalInput(((Wrapper) session.getAttribute("wrapper")).getBoard(), row, col)) {
//			model.addAttribute("message", "That was not a legal move");
//			return "multiplayerboard";
//		}
//		TicTacToeClient.getInstance().doMove(new Move(row, col), (Wrapper)session.getAttribute("wrapper"));
//		((Wrapper)session.getAttribute("wrapper")).isOkToPlay();
//		((Wrapper)session.getAttribute("wrapper")).setOkToPlay(false);
//		((Wrapper)session.getAttribute("wrapper")).readBoard();
//		if(!((Wrapper)session.getAttribute("wrapper")).getWinner().equals("")) {
//			model.addAttribute("winner", ((Wrapper) session.getAttribute("wrapper")).getWinner());
//			return "end";
//		}
//		return "multiplayerboard";
//	}
	
	@RequestMapping("/move")
	public String doMove(HttpSession session, Model model, @RequestParam("row") int row, @RequestParam("col") int col) {
		if(session.getAttribute("wrapper") == null) {
			return "redirect:/home";
		}
		if(!((Wrapper)session.getAttribute("wrapper")).isOkToPlay()) {
			return "multiplayerboard";
		}
		((Wrapper)session.getAttribute("wrapper")).setConnection("OK");
		if(!isLegalInput(((Wrapper) session.getAttribute("wrapper")).getBoard(), row, col)) {
			return "multiplayerboard";
		}
		((Wrapper)session.getAttribute("wrapper")).setUpdatedBoard(false);
		TicTacToeClient.getInstance().doMove(new Move(row, col), (Wrapper)session.getAttribute("wrapper"));
		((Wrapper)session.getAttribute("wrapper")).readBoard();
		if(!((Wrapper)session.getAttribute("wrapper")).getWinner().equals("")) {
			return "redirect:/multiplayer/end";
		}
		return "multiplayerboard";
	}
	
	@RequestMapping("/end")
	public String end() {
		return "end";
	}
	
	private static boolean isLegalInput(Board b, int row, int col) {
		if ((row == 1 || row == 2 || row == 3 || row == 4 || row == 5 || row == 6 || row == 7 || row == 8 || row == 0)
				&& (col == 1 || col == 2 || col == 3 || col == 4 || col == 5 || col == 6 || col == 7 || col == 8
						|| col == 0)) {
			if (b.getSymbolAtPos(row, col) == ' ' && b.findPossibleMoves().contains(new Move(row, col)))
				return true;
		}
		return false;
	}
}
