package com.kurukurupapa.pffsimu.web.partyfinder;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.BattleType;
import com.kurukurupapa.pffsimu.domain.party.Party;

/**
 * パーティ検討機能 コントローラクラス
 */
@Controller
@RequestMapping("/partyfinder")
public class PartyFinderController {
	private Logger logger = Logger.getLogger(PartyFinderController.class);

	@Autowired
	private PartyFinderService partyFinderService;

	@RequestMapping
	public String index() {
		return "redirect:/partyfinder/find";
	}

	@RequestMapping("/find")
	public String getFindForm(Model model) {
		PartyFinderForm form = new PartyFinderForm();
		model.addAttribute("form", form);
		model.addAttribute("battleTypes", BattleType.values());
		model.addAttribute("attrs", Attr.getValuesWithoutNone());
		return "partyfinder/find";
	}

	@RequestMapping(value = "/find", params = { "btn" })
	public String find(@Valid @ModelAttribute PartyFinderForm form,
			BindingResult result, Model model) {
		logger.trace("Start");
		logger.info("リクエストパラメータ=" + form.toString());

		if (result.hasErrors()) {
			// 入力チェックエラーあり
			// 何もしない
		} else {
			partyFinderService.set(form);
			partyFinderService.run();
			Party party = partyFinderService.getParty();
			model.addAttribute("party", party);
			logger.debug("Party=" + party);
		}
		model.addAttribute("form", form);
		model.addAttribute("battleTypes", BattleType.values());
		model.addAttribute("attrs", Attr.getValuesWithoutNone());

		logger.trace("End");
		return "partyfinder/find";
	}

}
