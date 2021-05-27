package io.horizon.ftdc.service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ftdc")
public class FtdcServiceController {

	private final AtomicBoolean isLogin = new AtomicBoolean(false);

	private final AtomicReference<FtdcParams> ftdcParams = new AtomicReference<FtdcParams>(new FtdcParams());

	@GetMapping("/params")
	@ResponseBody
	public FtdcParams params() {
		return ftdcParams.get();
	}

	@PostMapping("/params")
	@ResponseBody
	public int params(FtdcParams params) {

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
