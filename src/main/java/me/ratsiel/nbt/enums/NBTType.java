package me.ratsiel.nbt.enums;

import me.ratsiel.nbt.abstracts.AbstractCompound;
import me.ratsiel.nbt.model.CompoundList;
import me.ratsiel.nbt.model.CompoundTag;
import me.ratsiel.nbt.model.array.CompoundByteArray;
import me.ratsiel.nbt.model.array.CompoundIntArray;
import me.ratsiel.nbt.model.array.CompoundLongArray;
import me.ratsiel.nbt.model.values.*;

public enum NBTType {

    TAG_END(0),
    TAG_BYTE(1, 0),
    TAG_SHORT(2, 0),
    TAG_INT(3, 0),
    TAG_LONG(4, 0),
    TAG_FLOAT(5, 0.0),
    TAG_DOUBLE(6, 0.0),
    TAG_BYTE_ARRAY(7),
    TAG_STRING(8, ""),
    TAG_LIST(9),
    TAG_COMPOUND(10),
    TAG_INT_ARRAY(11),
    TAG_LONG_ARRAY(12);


    private final int id;
    private final Object defaultValue;

    NBTType(int id, Object defaultValue) {
        this.id = id;
        this.defaultValue = defaultValue;
    }

    NBTType(int id) {
        this(id, null);
    }

    /**
     * Get id from {@link #values}
     * @return id
     */
    public int getId() {
        return id;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * Return {@link NBTType} from object of {@link AbstractCompound}
     * @param compound given {@link AbstractCompound}
     * @return {@link NBTType} or null
     */
    public static NBTType toType(AbstractCompound compound) {
        if(compound instanceof CompoundByte) {
            return NBTType.TAG_BYTE;
        } else if(compound instanceof CompoundDouble) {
            return NBTType.TAG_DOUBLE;
        } else if(compound instanceof CompoundFloat) {
            return NBTType.TAG_FLOAT;
        } else if(compound instanceof CompoundInt) {
            return NBTType.TAG_INT;
        } else if(compound instanceof CompoundLong) {
            return NBTType.TAG_LONG;
        } else if(compound instanceof CompoundShort) {
            return NBTType.TAG_SHORT;
        } else if(compound instanceof CompoundString) {
            return NBTType.TAG_STRING;
        } else if(compound instanceof CompoundByteArray) {
            return NBTType.TAG_BYTE_ARRAY;
        } else if(compound instanceof CompoundIntArray) {
            return NBTType.TAG_INT_ARRAY;
        } else if(compound instanceof CompoundLongArray) {
            return NBTType.TAG_LONG_ARRAY;
        } else if(compound instanceof CompoundList) {
            return NBTType.TAG_LIST;
        } else if(compound instanceof CompoundTag) {
            return NBTType.TAG_COMPOUND;
        }
        return null;
    }


    /**
     * Get {@link NBTType} from typeId
     * @param typeId given typeId
     * @return null or {@link NBTType}
     */
    public static NBTType getNBTType(int typeId) {
        if(typeId < 0 || typeId > 12) return null;
        return values()[typeId];
    }

}
