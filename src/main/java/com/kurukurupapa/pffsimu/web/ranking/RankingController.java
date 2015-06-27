package com.kurukurupapa.pffsimu.web.ranking;

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

/**
 * ランキング機能コントローラクラス
 */
@Controller
@RequestMapping("/ranking")
public class RankingController {
	/** ロガー */
	private Logger logger = Logger.getLogger(RankingController.class);

	@Autowired
	private MemoriaRankingService memoriaRankingService;
	@Autowired
	private WeaponRankingService weaponRankingService;

	@RequestMapping("/memoria")
	public String getMemoriaRankingForm(Model model) {
		logger.info("Start");

		MemoriaRankingForm form = new MemoriaRankingForm();
		model.addAttribute("form", form);
		model.addAttribute("battleTypes", BattleType.values());
		model.addAttribute("attrs", Attr.getValuesWithoutNone());

		logger.info("End");
		return "ranking/memoria";
	}

	@RequestMapping(value = "/memoria", params = { "btn" })
	public String calcMemoriaRanking(
			@Valid @ModelAttribute MemoriaRankingForm form,
			BindingResult result, Model model) {
		logger.info("Start");

		if (result.hasErrors()) {
			// 入力チェックエラー
		} else {
			// 入力チェックOK
			memoriaRankingService.setup(form.getFitness());
			memoriaRankingService.run();
			model.addAttribute("ranking", memoriaRankingService.getRanking());
		}
		model.addAttribute("form", form);
		model.addAttribute("battleTypes", BattleType.values());
		model.addAttribute("attrs", Attr.getValuesWithoutNone());

		logger.info("End");
		return "ranking/memoria";
	}

	@RequestMapping("/weapon")
	public String getWeaponRankingForm(Model model) {
		logger.info("Start");

		WeaponRankingForm form = new WeaponRankingForm();
		model.addAttribute("form", form);

		logger.info("End");
		return "ranking/weapon";
	}

	@RequestMapping(value = "/weapon", params = { "btn" })
	public String calcWeaponRanking(
			@Valid @ModelAttribute WeaponRankingForm form,
			BindingResult result, Model model) {
		logger.info("Start");

		if (result.hasErrors()) {
			// 入力チェックエラー
		} else {
			// 入力チェックOK
			weaponRankingService.run();
			model.addAttribute("ranking", weaponRankingService.getRanking());
		}
		model.addAttribute("form", form);

		logger.info("End");
		return "ranking/weapon";
	}

}
