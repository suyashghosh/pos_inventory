package inventory.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LandingPage {
	
	@GetMapping("/")
	public String landingPage(){
		return "landing";
	}

}
