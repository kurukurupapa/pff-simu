package com.kurukurupapa.pffsimu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
	@RequestMapping
	public String index() {
		// "/index"とすると、JARから起動したとき、Thymeleafがテンプレートを見つけられなくなった。
		return "index";
	}
}
