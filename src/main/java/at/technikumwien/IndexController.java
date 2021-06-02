package at.technikumwien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	@Autowired
	private PersonRepository personRepository;
	
	@RequestMapping
	public String index(@RequestParam(defaultValue = "false") boolean all, Model model) {
		var persons = (all ? personRepository.findAll() : personRepository.findAllByActiveTrue());		
		
		model.addAttribute("persons", persons);
		if (all) {
			model.addAttribute("all", true);
		}
		
		return "index";
	}
}
