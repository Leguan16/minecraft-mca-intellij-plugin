package me.ratsiel.nbt.model;

import me.ratsiel.nbt.abstracts.AbstractCompound;

import java.util.HashMap;

public class CompoundTag extends AbstractCompound {

    protected final HashMap<String, AbstractCompound> values = new HashMap<>();

    /**
     * Check if the given key exists in {@link #values}
     *
     * @param key given key
     * @return true or false
     */
    public boolean hasKey(String key) {
        return values.containsKey(key);
    }

    /**
     * Inserts an object of class {@link AbstractCompound} with a key into {@link #values}
     *
     * @param key   given key
     * @param value given object of class {@link AbstractCompound}
     */
    public void put(String key, AbstractCompound value) {
        values.put(key, value);
    }

    /**
     * Get an object by key from {@link #values}
     *
     * @param key given key
     * @return null or object of class {@link AbstractCompound}
     */
    public <T extends AbstractCompound> T get(String key) {
        return hasKey(key) ? (T) values.get(key) : null;
    }

    /**
     * Get an object by key from {@link #values} and cast ist to given class of {@link AbstractCompound}
     *
     * @param key   given key
     * @param clazz given class of {@link AbstractCompound}
     * @return null or object casted to class of {@link AbstractCompound}
     */
    public <T extends AbstractCompound> T get(String key, Class<T> clazz) {
        return hasKey(key) ? clazz.cast(values.get(key)) : null;
    }

    /**
     * Get all values from {@link #values}
     *
     * @return {@link HashMap} with values of class {@link AbstractCompound}
     */
    public HashMap<String, AbstractCompound> getValues() {
        return values;
    }

}
