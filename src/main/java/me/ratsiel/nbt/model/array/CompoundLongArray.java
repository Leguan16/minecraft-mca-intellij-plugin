package me.ratsiel.nbt.model.array;

import me.ratsiel.nbt.abstracts.AbstractCompoundValue;

public class CompoundLongArray extends AbstractCompoundValue<Long[]> {

    public CompoundLongArray(long[] value) {
        super(toObjects(value));
    }

    /**
     * Converts primitive <code>long[]</code> to object array <code>Long[]</code>
     * @param value <code>long[]</code>
     * @return <code>Long[]</code>
     */
    public static Long[] toObjects(long[] value) {
        Long[] bytes = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            bytes[i] = value[i];
        }
        return bytes;
    }


}
