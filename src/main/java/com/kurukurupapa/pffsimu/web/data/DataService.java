package com.kurukurupapa.pffsimu.web.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

/**
 * データ管理機能サービスクラス
 */
@Service
public class DataService {

	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;

	public DataService() {
		// データ読み込み
		itemDataSet = new ItemDataSet();
		itemDataSet.readUserFile();
		memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.readUserFile();
	}

	public List<MemoriaData> getMemorias() {
		return memoriaDataSet.getMemoriaDataList();
	}

	public List<ItemData> getItems() {
		return itemDataSet.makeAllItemDataList();
	}
}
