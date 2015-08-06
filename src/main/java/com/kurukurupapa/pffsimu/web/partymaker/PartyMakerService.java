package com.kurukurupapa.pffsimu.web.partymaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kurukurupapa.pff.dp01.ItemFitness;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pffsimu.web.ranking.AccessoryRankingService;
import com.kurukurupapa.pffsimu.web.ranking.MagicRankingService;
import com.kurukurupapa.pffsimu.web.ranking.MemoriaRankingService;
import com.kurukurupapa.pffsimu.web.ranking.WeaponRankingService;

/**
 * パーティメーカー機能 サービスクラス
 */
@Service
public class PartyMakerService {

	@Autowired
	private MemoriaRankingService memoriaRankingService;
	@Autowired
	private WeaponRankingService weaponRankingService;
	@Autowired
	private MagicRankingService magicRankingService;
	@Autowired
	private AccessoryRankingService accessoryRankingService;

	public void makeElement(HttpSession session, Model model) {
		SessionHelper sessionHelper = new SessionHelper(session);
		TargetForm targetForm = sessionHelper.getTarget();
		switch (targetForm.getKind()) {
		case MEMORIA:
			makeMemoriaElement(sessionHelper, model);
			break;
		case WEAPON:
			makeWeaponElement(sessionHelper, model);
			break;
		case ACCESSORY1:
		case ACCESSORY2:
			makeAccessoryElement(sessionHelper, model);
			break;
		}
		model.addAttribute("targetForm", targetForm);
	}

	private void makeMemoriaElement(SessionHelper sessionHelper, Model model) {
		memoriaRankingService.setup(sessionHelper.getCondition()
				.getFitnessCalculator(), getPartyForRanking(sessionHelper));
		memoriaRankingService.run();
		model.addAttribute("ranking", memoriaRankingService.getRanking());
		sessionHelper.setMemoriaRanking(memoriaRankingService.getRanking());
	}

	private void makeWeaponElement(SessionHelper sessionHelper, Model model) {
		weaponRankingService.setup(sessionHelper.getCondition()
				.getFitnessCalculator(), getPartyForRanking(sessionHelper),
				sessionHelper.getTarget().getIndex());
		weaponRankingService.run();
		model.addAttribute("ranking", weaponRankingService.getRanking());
		sessionHelper.setWeaponRanking(weaponRankingService.getRanking());
	}

	private void makeAccessoryElement(SessionHelper sessionHelper, Model model) {
		// 魔法ランキング
		magicRankingService.setup(sessionHelper.getCondition()
				.getFitnessCalculator(), getPartyForRanking(sessionHelper),
				sessionHelper.getTarget().getIndex());
		magicRankingService.run();

		// アクセサリランキング
		accessoryRankingService.setup(sessionHelper.getCondition()
				.getFitnessCalculator(), getPartyForRanking(sessionHelper),
				sessionHelper.getTarget().getIndex());
		accessoryRankingService.run();

		// マージ
		List<ItemFitness> fitnesses = new ArrayList<ItemFitness>();
		fitnesses.addAll(magicRankingService.getRanking());
		fitnesses.addAll(accessoryRankingService.getFitnesses());
		// 評価値の降順でソート
		Collections.sort(fitnesses, new Comparator<ItemFitness>() {
			@Override
			public int compare(ItemFitness arg0, ItemFitness arg1) {
				// 降順
				return arg1.getFitness() - arg0.getFitness();
			}
		});

		// ビューへ
		model.addAttribute("ranking", fitnesses);
		sessionHelper.setMagicAccessoryRanking(fitnesses);
	}

	public void postElement(HttpSession session, ElementForm form, Model model) {
		SessionHelper sessionHelper = new SessionHelper(session);
		TargetForm targetForm = sessionHelper.getTarget();
		TargetForm.Operation operation = targetForm.getOperation();
		int memoriaIndex = targetForm.getIndex();
		int rankingIndex = form.getBtn();
		Party party = sessionHelper.getParty();
		switch (targetForm.getKind()) {
		case MEMORIA:
			postMemoria(sessionHelper, operation, memoriaIndex, rankingIndex,
					party);
			break;
		case WEAPON:
			postWeapon(sessionHelper, operation, memoriaIndex, rankingIndex,
					party);
			break;
		case ACCESSORY1:
			postMagicAccessory(sessionHelper, operation, memoriaIndex, 0,
					rankingIndex, party);
			break;
		case ACCESSORY2:
			postMagicAccessory(sessionHelper, operation, memoriaIndex, 1,
					rankingIndex, party);
			break;
		}
		party.calcFitness(sessionHelper.getCondition().getFitnessCalculator());
	}

	public void postMemoria(SessionHelper sessionHelper,
			TargetForm.Operation operation, int memoriaIndex, int rankingIndex,
			Party party) {
		// 編集対象の削除
		if (operation.equals(TargetForm.Operation.EDIT)
				|| operation.equals(TargetForm.Operation.DELETE)) {
			party.remove(memoriaIndex);
		}
		// 選択されていたら追加
		if ((operation.equals(TargetForm.Operation.ADD) || operation
				.equals(TargetForm.Operation.EDIT)) && rankingIndex >= 0) {
			party.add(sessionHelper.getMemoriaRanking().get(rankingIndex)
					.getMemoria());
		}
		// 不要なセッション情報を片付け
		sessionHelper.setMemoriaRanking(null);
	}

	public void postWeapon(SessionHelper sessionHelper,
			TargetForm.Operation operation, int memoriaIndex, int rankingIndex,
			Party party) {
		// 編集対象の削除
		if (operation.equals(TargetForm.Operation.EDIT)
				|| operation.equals(TargetForm.Operation.DELETE)) {
			party.getMemoria(memoriaIndex).clearWeapon();
		}
		// 選択されていたら追加
		if ((operation.equals(TargetForm.Operation.ADD) || operation
				.equals(TargetForm.Operation.EDIT)) && rankingIndex >= 0) {
			party.getMemoria(memoriaIndex).setWeapon(
					sessionHelper.getWeaponRanking().get(rankingIndex)
							.getItem());
		}
		// 不要なセッション情報を片付け
		sessionHelper.setWeaponRanking(null);
	}

	public void postMagicAccessory(SessionHelper sessionHelper,
			TargetForm.Operation operation, int memoriaIndex,
			int magicAccessoryIndex, int rankingIndex, Party party) {
		// 編集対象の削除
		if (operation.equals(TargetForm.Operation.EDIT)
				|| operation.equals(TargetForm.Operation.DELETE)) {
			party.getMemoria(memoriaIndex).removeAccessory(magicAccessoryIndex);
		}
		// 選択されていたら追加
		if ((operation.equals(TargetForm.Operation.ADD) || operation
				.equals(TargetForm.Operation.EDIT)) && rankingIndex >= 0) {
			party.getMemoria(memoriaIndex).addAccessory(
					sessionHelper.getMagicAccessoryRanking().get(rankingIndex)
							.getItem());
		}
		// 不要なセッション情報を片付け
		sessionHelper.setMagicAccessoryRanking(null);
	}

	private Party getPartyForRanking(SessionHelper sessionHelper) {
		Party party = sessionHelper.getParty().clone();
		TargetForm targetForm = sessionHelper.getTarget();
		if (targetForm.getOperation().equals(TargetForm.Operation.EDIT)
				|| targetForm.getOperation()
						.equals(TargetForm.Operation.DELETE)) {
			int memoriaIndex = targetForm.getIndex();
			switch (targetForm.getKind()) {
			case MEMORIA:
				party.remove(memoriaIndex);
				break;
			case WEAPON:
				party.getMemoria(memoriaIndex).clearWeapon();
				break;
			case ACCESSORY1:
				party.getMemoria(memoriaIndex).removeAccessory(0);
				break;
			case ACCESSORY2:
				party.getMemoria(memoriaIndex).removeAccessory(1);
				break;
			}
		}
		return party;
	}

}
