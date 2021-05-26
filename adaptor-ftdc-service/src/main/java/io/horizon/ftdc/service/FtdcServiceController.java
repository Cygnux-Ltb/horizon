package io.horizon.ftdc.service;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ftdc")
public class FtdcServiceController {

	private final AtomicBoolean isLogin = new AtomicBoolean(false);
	
	//private final AtomicReference<Ftdcparam>
	

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
