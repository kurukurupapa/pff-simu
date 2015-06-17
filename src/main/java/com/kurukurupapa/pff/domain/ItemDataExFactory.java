package com.kurukurupapa.pff.domain;

public class ItemDataExFactory {

    public static ItemDataEx create(ItemData data, ItemType itemType,
            ItemType2 itemType2, Attr attr, int magicCharge, int magicEffect) {
        ItemDataEx instance = null;
        switch (itemType) {
        case WEAPON:
            instance = new WeaponItemDataEx(data, itemType, itemType2, attr);
            break;
        case MAGIC:
            MagicType magicType = MagicTypeFactory.create(itemType2.toString());
            switch (magicType) {
            case WHITE:
                instance = new WhiteMagicItemDataEx(data, itemType, itemType2,
                        attr, magicCharge, magicEffect);
                break;
            case BLACK:
                instance = new BlackMagicItemDataEx(data, itemType, itemType2,
                        attr, magicCharge, magicEffect);
                break;
            case SUMMON:
                instance = new SummonMagicItemDataEx(data, itemType, itemType2,
                        attr, magicCharge, magicEffect);
                break;
            }
            break;
        case ACCESSORY:
            instance = new AccessoryItemDataEx(data, itemType, itemType2, attr);
            break;
        }
        return instance;
    }

}
