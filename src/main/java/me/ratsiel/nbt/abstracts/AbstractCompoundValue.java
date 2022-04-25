package me.ratsiel.nbt.abstracts;

public abstract class AbstractCompoundValue<T> extends AbstractCompound {

    private final T value;

    public AbstractCompoundValue(T value) {
        this.value = value;
    }

    /**
     * Return value {@link #value}
     * @return null or {@link #value}
     */
    public T getValue() {
        return value;
    }

}
