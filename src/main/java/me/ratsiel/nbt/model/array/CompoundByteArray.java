package me.ratsiel.nbt.model.array;

import me.ratsiel.nbt.abstracts.AbstractCompoundValue;

public class CompoundByteArray extends AbstractCompoundValue<Byte[]> {

    public CompoundByteArray(byte[] value) {
        super(toObjects(value));
    }

    /**
     * Converts primitive <code>byte[]</code> to object array <code>Byte[]</code>
     * @param value <code>byte[]</code>
     * @return <code>Byte[]</code>
     */
    public static Byte[] toObjects(byte[] value) {
        Byte[] bytes = new Byte[value.length];
        for (int i = 0; i < value.length; i++) {
            bytes[i] = value[i];
        }
        return bytes;
    }


}
