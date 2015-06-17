package com.kurukurupapa.pff.domain;

import java.util.HashMap;

/**
 * 属性ファクトリークラス
 */
public class AttrFactory {

    private static HashMap<String, Attr> mMap;

    static {
        mMap = new HashMap<String, Attr>();
        mMap.put("なし", Attr.NONE);
        mMap.put("炎", Attr.FIRE);
        mMap.put("氷", Attr.ICE);
        mMap.put("風", Attr.WIND);
        mMap.put("地", Attr.EARTH);
        mMap.put("雷", Attr.THUNDER);
        mMap.put("聖", Attr.HOLINESS);
        mMap.put("水", Attr.WATER);
        mMap.put("飛行", Attr.FLIGHT);
        mMap.put("冷気", Attr.COLD);
        mMap.put("闇", Attr.DARKNESS);
        
        mMap.put("fire", Attr.FIRE);
        mMap.put("ice", Attr.ICE);
        mMap.put("wind", Attr.WIND);
        mMap.put("earth", Attr.EARTH);
        mMap.put("thunder", Attr.THUNDER);
        mMap.put("holiness", Attr.HOLINESS);
        mMap.put("water", Attr.WATER);
        mMap.put("flight", Attr.FLIGHT);
        mMap.put("cold", Attr.COLD);
        mMap.put("darkness", Attr.DARKNESS);
    }

    public static Attr create(String name) {
        Attr attr = mMap.get(name.trim());
        if (attr == null) {
            throw new AppException("不正な引数です。name=" + name);
        }
        return attr;
    }

}
