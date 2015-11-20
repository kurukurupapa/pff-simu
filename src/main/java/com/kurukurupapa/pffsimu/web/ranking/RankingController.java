package com.kurukurupapa.pffsimu.web.ranking;

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
	@Autowired
	private MagicRankingService magicRankingService;
	@Autowired
	private AccessoryRankingService accessoryRankingService;

	@RequestMapping("/memoria")
	public String getMemoriaRankingForm(Model model) {
		logger.info("getMemoriaRankingForm start");

		MemoriaRankingForm form = new MemoriaRankingForm();
		form.setLeaderSkillFlag("1");
		form.setPremiumSkillFlag("1");
		form.setJobSkillFlag("1");
		model.addAttribute("form", form);
		model.addAttribute("battleTypes", BattleType.values());
		model.addAttribute("attrs", Attr.getValuesWithoutNone());

		logger.info("getMemoriaRankingForm end");
		return "ranking/memoria";
	}

	@RequestMapping(value = "/memoria", params = { "btn" })
	public String calcMemoriaRanking(@Valid @ModelAttribute MemoriaRankingForm form, BindingResult result,
			Model model) {
		logger.info("calcMemoriaRanking start");

		if (result.hasErrors()) {
			// 入力チェックエラー
		} else {
			// 入力チェックOK
			memoriaRankingService.setup(form);
			memoriaRankingService.run();
			model.addAttribute("ranking", memoriaRankingService.getRanking());
		}
		model.addAttribute("form", form);
		model.addAttribute("battleTypes", BattleType.values());
		model.addAttribute("attrs", Attr.getValuesWithoutNone());

		logger.info("calcMemoriaRanking end");
		return "ranking/memoria";
	}

	@RequestMapping("/weapon")
	public String getWeaponRankingForm(Model model) {
		logger.info("getWeaponRankingForm start");

		WeaponRankingForm form = new WeaponRankingForm();
		model.addAttribute("form", form);

		logger.info("getWeaponRankingForm end");
		return "ranking/weapon";
	}

	@RequestMapping(value = "/weapon", params = { "btn" })
	public String calcWeaponRanking(@Valid @ModelAttribute WeaponRankingForm form, BindingResult result, Model model) {
		logger.info("calcWeaponRanking start");

		if (result.hasErrors()) {
			// 入力チェックエラー
		} else {
			// 入力チェックOK
			weaponRankingService.setup();
			weaponRankingService.run();
			model.addAttribute("ranking", weaponRankingService.getRanking());
		}
		model.addAttribute("form", form);

		logger.info("calcWeaponRanking end");
		return "ranking/weapon";
	}

	@RequestMapping("/magic")
	public String getMagicRankingForm(Model model) {
		logger.info("getMagicRankingForm start");

		MagicRankingForm form = new MagicRankingForm();
		model.addAttribute("form", form);

		logger.info("getMagicRankingForm end");
		return "ranking/magic";
	}

	@RequestMapping(value = "/magic", params = { "btn" })
	public String calcMagicRanking(@Valid @ModelAttribute MagicRankingForm form, BindingResult result, Model model) {
		logger.info("calcMagicRanking start");

		if (result.hasErrors()) {
			// 入力チェックエラー
		} else {
			// 入力チェックOK
			magicRankingService.setup();
			magicRankingService.run();
			model.addAttribute("ranking", magicRankingService.getRanking());
		}
		model.addAttribute("form", form);

		logger.info("calcMagicRanking end");
		return "ranking/magic";
	}

	@RequestMapping("/accessory")
	public String getAccessoryRankingForm(Model model) {
		logger.info("getAccessoryRankingForm start");

		AccessoryRankingForm form = new AccessoryRankingForm();
		model.addAttribute("form", form);

		logger.info("getAccessoryRankingForm end");
		return "ranking/accessory";
	}

	@RequestMapping(value = "/accessory", params = { "btn" })
	public String calcAccessoryRanking(@Valid @ModelAttribute AccessoryRankingForm form, BindingResult result,
			Model model) {
		logger.info("calcAccessoryRanking start");

		if (result.hasErrors()) {
			// 入力チェックエラー
		} else {
			// 入力チェックOK
			accessoryRankingService.setup();
			accessoryRankingService.run();
			model.addAttribute("ranking", accessoryRankingService.getFitnesses());
		}
		model.addAttribute("form", form);

		logger.info("calcAccessoryRanking end");
		return "ranking/accessory";
	}

}
