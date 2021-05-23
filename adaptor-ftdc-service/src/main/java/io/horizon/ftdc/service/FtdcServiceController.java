package io.horizon.ftdc.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FtdcServiceController {

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("welcome", "hello ftdc");
		model.addAttribute("hj", "index");
		return "index";
	}

	@RequestMapping("login")
	public String login(Model model) {
		model.addAttribute("welcome", "hello ftdc");
		model.addAttribute("hj", "index");
		return "index";
	}

}
