package com.kurukurupapa.pffsimu.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private AccessoryRankingService accessoryRankingService;

	@RequestMapping
	public String index() {
		return "item/index";
	}

	@RequestMapping("/weapon_ranking")
	public String getWeaponRankingForm(Model model) {
		// TODO
		return "item/index";
	}

	@RequestMapping(value = "/memoria_ranking", params = { "btn" })
	public String calcMemoriaRanking(
			@Valid @ModelAttribute MemoriaRankingForm form,
			BindingResult result, Model model) {
		// TODO
		if (result.hasErrors()) {
			// 入力チェックエラー
		} else {
			// 入力チェックOK
		}
		model.addAttribute("form", form);
		return "item/index";
	}

	@RequestMapping("/magic_ranking")
	public String getMagicRankingForm(Model model) {
		// TODO
		return "item/index";
	}
	
	@RequestMapping("/accessory_ranking")
	public String getAccessoryRankingForm(Model model) {
		AccessoryRankingForm form = new AccessoryRankingForm();
		model.addAttribute("form", form);
		return "item/accessory_ranking";
	}

	@RequestMapping(value = "/accessory_ranking", params = { "btn" })
	public String calcAccessoryRanking(
			@Valid @ModelAttribute AccessoryRankingForm form,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			// 入力チェックエラー
		} else {
			// 入力チェックOK
			accessoryRankingService.run();
			model.addAttribute("fitnesses",
					accessoryRankingService.getFitnesses());
		}
		model.addAttribute("form", form);
		return "item/accessory_ranking";
	}
}
