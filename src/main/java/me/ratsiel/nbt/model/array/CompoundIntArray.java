package me.ratsiel.nbt.model.array;

import me.ratsiel.nbt.abstracts.AbstractCompoundValue;

public class CompoundIntArray extends AbstractCompoundValue<Integer[]> {

    public CompoundIntArray(int[] value) {
        super(toObjects(value));
    }

    /**
     * Converts primitive <code>int[]</code> to object array <code>Integer[]</code>
     * @param value <code>int[]</code>
     * @return <code>Integer[]</code>
     */
    public static Integer[] toObjects(int[] value) {
        Integer[] bytes = new Integer[value.length];
        for (int i = 0; i < value.length; i++) {
            bytes[i] = value[i];
        }
        return bytes;
    }


}
