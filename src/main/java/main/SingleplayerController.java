package main;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/singleplayer")
public class SingleplayerController {

	@RequestMapping(value = { "/", "", "/home" })
	public String home(HttpSession session, Model model) {
		if (session.getAttribute("wrapper") == null) {
			Wrapper wrapper = new Wrapper(new Board(), Long.toString(System.currentTimeMillis()));
			TicTacToeClient.getInstance().getWrapperList().add(wrapper);
			session.setAttribute("wrapper", wrapper);
			TicTacToeClient.getInstance().sendMessage(wrapper.getIdentifier() + " ");
		}
		TicTacToeClient.getInstance().getWrapper(((Wrapper) session.getAttribute("wrapper"))).readBoard();
		model.addAttribute("board", ((Wrapper) session.getAttribute("wrapper")).getBoard());
		return "singleplayerhome";
	}

	@RequestMapping("/move")
	public String doMove(HttpSession session, Model model, @RequestParam("row") int row, @RequestParam("col") int col) {
		if (session.getAttribute("wrapper") == null)
			return "redirect:/home";
		if (!isLegalInput(((Wrapper) session.getAttribute("wrapper")).getBoard(), row, col)) {
			model.addAttribute("board", ((Wrapper) session.getAttribute("wrapper")).getBoard());
			model.addAttribute("message", "That was not a legal move");
			return "singleplayerhome";
		}
		TicTacToeClient.getInstance().doMove(new Move(row, col), (Wrapper) session.getAttribute("wrapper"));
		((Wrapper) session.getAttribute("wrapper")).readBoard();
		if (!((Wrapper) session.getAttribute("wrapper")).getWinner().equals("")) {
			return "end";
		}
		model.addAttribute("board", ((Wrapper) session.getAttribute("wrapper")).getBoard());
		return "singleplayerhome";
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
