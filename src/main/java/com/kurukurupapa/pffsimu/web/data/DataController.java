package com.kurukurupapa.pffsimu.web.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * データ管理機能コントローラクラス
 */
@Controller
@RequestMapping("/data")
public class DataController {

	@Autowired
	private DataService dataService;

	@RequestMapping
	public String index() {
		return "redirect:/data/memoria_list";
	}

	@RequestMapping("/memoria_list")
	public String getMemoriaList(Model model) {
		model.addAttribute("memorias", dataService.getMemorias());
		return "data/memoria_list";
	}

	@RequestMapping("/item_list")
	public String getItemList(Model model) {
		model.addAttribute("items", dataService.getItems());
		return "data/item_list";
	}

}
