package io.horizon.ctp.service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.horizon.ctp.adaptor.CtpConfig;

@Controller
@RequestMapping("/ctp")
public class CtpServiceController {

	@SuppressWarnings("unused")
	private final AtomicBoolean isLogin = new AtomicBoolean(false);

	private final AtomicReference<CtpConfig> config = new AtomicReference<CtpConfig>(new CtpConfig());

	@GetMapping("/config")
	@ResponseBody
	public CtpConfig config() {
		return config.get();
	}

	@PostMapping("/config")
	@ResponseBody
	public int params(@RequestParam CtpConfig params) {
		return 200;
	}

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
