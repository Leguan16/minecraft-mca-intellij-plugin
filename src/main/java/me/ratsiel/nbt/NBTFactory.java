package me.ratsiel.nbt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.ratsiel.nbt.model.CompoundTag;

import java.io.*;

public class NBTFactory {

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Load root {@link CompoundTag} from {@link File}
     * @param file given {@link File}
     * @return {@link CompoundTag} or throws {@link FileNotFoundException} or {@link IOException}
     */
    public static CompoundTag loadNBT(File file) {
        try {
            NBTInputStream inputStream = new NBTInputStream(new FileInputStream(file));
            return inputStream.readRoot();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return null;
        } catch (IOException exception) {
            return null;
        }
    }

    /**
     * Load root {@link CompoundTag} from {@link InputStream}
     * @param inputStream given {@link InputStream}
     * @return {@link CompoundTag} or throws {@link FileNotFoundException} or {@link IOException}
     */
    public static CompoundTag loadNBT(InputStream inputStream) {
        try {
            NBTInputStream nbtInputStream = new NBTInputStream(inputStream);
            return nbtInputStream.readRoot();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return null;
        } catch (IOException exception) {
            return null;
        }
    }



    /**
     * Write given {@link CompoundTag} to given {@link File}
     * @param compoundTag given {@link CompoundTag}
     * @param file given {@link File}
     * @param compressed determine if file is compressed or not
     */
    public static void saveNBT(CompoundTag compoundTag, File file, boolean compressed) {
        try {
            NBTOutputStream nbtOutputStream = new NBTOutputStream(new FileOutputStream(file), compressed);
            nbtOutputStream.writeNBT(compoundTag);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
