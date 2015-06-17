package com.kurukurupapa.pff.domain;

import java.util.List;

/**
 * 属性enum
 */
public enum Attr {
    /** 属性なし */
    NONE,
    /** 炎属性 */
    FIRE,
    /** 氷属性 */
    ICE,
    /** 風属性 */
    WIND,
    /** 地属性 */
    EARTH,
    /** 雷属性 */
    THUNDER,
    /** 聖属性 */
    HOLINESS,
    /** 水属性 */
    WATER,
    /** 飛行 */
    FLIGHT,
    /** 冷気属性 */
    COLD,
    /** 闇 */
    DARKNESS,
    //
    ;

    public boolean isNone() {
        return this == NONE;
    }

    public boolean isFlight() {
        return this == FLIGHT;
    }

    public boolean isAttrWithoutFlight(List<Attr> weakList) {
        if (this == NONE || isFlight()) {
            return false;
        }
        for (Attr e : weakList) {
            if (this == e) {
                return true;
            }
        }
        return false;
    }
}
