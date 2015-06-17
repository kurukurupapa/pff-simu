package com.kurukurupapa.pffsimu.web;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kurukurupapa.pff.dp01.Party;

@Controller
@RequestMapping("/party")
public class PartyController {
	private Logger logger = Logger.getLogger(PartyController.class);

	@Autowired
	private PartyFindService partyFindService;

	@RequestMapping
	public String index() {
		return "redirect:/party/find";
	}

	@RequestMapping("/find")
	public String getFindForm(Model model) {
		PartyFindForm form = new PartyFindForm();
		model.addAttribute("form", form);
		return "party/find";
	}

	@RequestMapping(value = "/find", params = { "btn" })
	public String find(@Valid @ModelAttribute PartyFindForm form,
			BindingResult result, Model model) {
		logger.trace("Start");
		logger.info("リクエストパラメータ=" + form.toString());

		if (result.hasErrors()) {
			// 入力チェックエラーあり
			// 何もしない
		} else {
			partyFindService.set(form);
			partyFindService.run();
			Party party = partyFindService.getParty();
			model.addAttribute("party", party);
			logger.debug("Party=" + party);
		}
		model.addAttribute("form", form);

		logger.trace("End");
		return "party/find";
	}

}
