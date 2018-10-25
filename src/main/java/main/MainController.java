package main;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {
	
	@RequestMapping(value = { "/", "", "/home" })
	public String home() {
		return "frontpage";
	}
	
	@RequestMapping("/playAgain")
	public String playAgain(HttpSession session) {
		TicTacToeClient.getInstance().getWrapperList().remove(((Wrapper)session.getAttribute("wrapper")));
		session.setAttribute("wrapper", null);
		return "redirect:/home";
	}
	
	@RequestMapping("/exit")
	public String exit(HttpSession session) {
		TicTacToeClient.getInstance().getWrapperList().remove(((Wrapper)session.getAttribute("wrapper")));
		session.setAttribute("wrapper", null);
		return "exit";
	}
}
