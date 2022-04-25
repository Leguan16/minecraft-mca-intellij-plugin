package me.ratsiel.nbt.model;

import me.ratsiel.nbt.abstracts.AbstractCompound;
import me.ratsiel.nbt.enums.NBTType;

import java.util.ArrayList;
import java.util.List;

public class CompoundList extends AbstractCompound {

    private NBTType nbtType;
    protected final List<AbstractCompound> values = new ArrayList<>();

    public CompoundList() {
    }

    public CompoundList(NBTType nbtType) {
        this.nbtType = nbtType;
    }

    /**
     * Add a object of class {@link AbstractCompound} into {@link #values}
     * @param value given object of class {@link AbstractCompound}
     */
    public void add(AbstractCompound value) {
        values.add(value);
    }

    /**
     * Get an object of class {@link AbstractCompound} by index from {@link #values}
     * @param index given index
     * @return object of class {@link AbstractCompound}
     */
    public AbstractCompound get(int index) {
        return values.get(index);
    }

    /**
     * Get all values from {@link #values}
     * @return {@link List} with values of class {@link AbstractCompound}
     */
    public List<AbstractCompound> getValues() {
        return values;
    }

    /**
     * Returns {@link List} of values casted to a class of {@link AbstractCompound}
     * @param clazz given class {@link AbstractCompound}
     * @return {@link List} of values casted to a class of {@link AbstractCompound}
     */
    public <T extends AbstractCompound> List<T> getValues(Class<T> clazz) {
        List<T> list = new ArrayList<>();

        for (AbstractCompound value : getValues()) {
            list.add(clazz.cast(value));
        }

        return list;
    }

    /**
     * Get {@link NBTType}
     * @return null or {@link NBTType}
     */
    public NBTType getType() {
        return nbtType;
    }

    /**
     * Set the current {@link #nbtType}
     * @param nbtType given {@link NBTType}
     */
    public void setType(NBTType nbtType) {
        this.nbtType = nbtType;
    }


}
