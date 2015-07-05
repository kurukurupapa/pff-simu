package com.kurukurupapa.pffsimu.web.partymaker;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.BattleType;
import com.kurukurupapa.pff.dp01.Party;

/**
 * パーティメーカー機能 コントローラクラス
 */
@Controller
@RequestMapping("/partymaker")
public class PartyMakerController {
	private Logger logger = Logger.getLogger(PartyMakerController.class);

	@Autowired
	private PartyMakerService partyMakerService;

	@RequestMapping
	public String getIndex(HttpSession session) {
		logger.info("getIndex start");

		// 当機能の初期化
		SessionHelper sessionHelper = new SessionHelper(session);
		sessionHelper.setParty(new Party());
		sessionHelper.setCondition(new ConditionForm());
		sessionHelper.setTarget(new TargetForm());

		logger.info("getIndex end");
		return "redirect:/partymaker/condition";
	}

	@RequestMapping(value = "/condition")
	public String getConditionForm(HttpSession session, Model model) {
		logger.info("getConditionForm start");

		SessionHelper sessionHelper = new SessionHelper(session);
		model.addAttribute("form", sessionHelper.getCondition());
		model.addAttribute("battleTypes", BattleType.values());
		model.addAttribute("attrs", Attr.getValuesWithoutNone());

		logger.info("getConditionForm end");
		return "partymaker/condition";
	}

	@RequestMapping(value = "/condition", params = { "nextBtn" })
	public String postCondition(HttpSession session,
			@Valid @ModelAttribute ConditionForm form, BindingResult result,
			Model model) {
		logger.info("postCondition start");

		SessionHelper sessionHelper = new SessionHelper(session);
		String view;
		if (result.hasErrors()) {
			view = getConditionForm(session, model);
		} else {
			sessionHelper.setCondition(form);
			view = getTargetForm(session, model);
		}

		logger.info("postCondition end");
		return view;
	}

	public String getTargetForm(HttpSession session, Model model) {
		logger.info("getTargetForm start");

		SessionHelper sessionHelper = new SessionHelper(session);
		model.addAttribute("conditionForm", sessionHelper.getCondition());
		model.addAttribute("form", sessionHelper.getTarget());
		model.addAttribute("party", sessionHelper.getParty());

		logger.info("getTargetForm end");
		return "partymaker/target";
	}

	@RequestMapping(value = "/target", params = { "backBtn" })
	public String backFromTarget(HttpSession session, Model model) {
		return getConditionForm(session, model);
	}

	@RequestMapping(value = "/target", params = { "btn" })
	public String postTarget(HttpSession session,
			@Valid @ModelAttribute TargetForm form, Model model) {
		logger.info("postTarget start");

		SessionHelper sessionHelper = new SessionHelper(session);
		form.parse();
		sessionHelper.setTarget(form);
		String view;
		if (form.getOperation().equals(TargetForm.Operation.DELETE)) {
			ElementForm elementForm = new ElementForm();
			elementForm.setBtn(-1);
			partyMakerService.postElement(session, elementForm, model);
			view = getTargetForm(session, model);
		} else {
			view = getElement(session, model);
		}

		logger.info("postTarget end");
		return view;
	}

	public String getElement(HttpSession session, Model model) {
		logger.info("getElement start");

		SessionHelper sessionHelper = new SessionHelper(session);
		partyMakerService.makeElement(session, model);
		model.addAttribute("conditionForm", sessionHelper.getCondition());

		logger.info("getElement end");
		return "partymaker/element";
	}

	@RequestMapping(value = "/element", params = { "backBtn" })
	public String backFromElement(HttpSession session, Model model) {
		return getTargetForm(session, model);
	}

	@RequestMapping(value = "/element", params = { "btn" })
	public String postElement(HttpSession session,
			@Valid @ModelAttribute ElementForm form, Model model) {
		logger.info("postElement start");

		partyMakerService.postElement(session, form, model);

		logger.info("postElement end");
		return getTargetForm(session, model);
	}
}
