package me.ratsiel.nbt;

import me.ratsiel.nbt.abstracts.AbstractCompound;
import me.ratsiel.nbt.enums.NBTType;
import me.ratsiel.nbt.exception.NBTParseException;
import me.ratsiel.nbt.model.CompoundList;
import me.ratsiel.nbt.model.CompoundTag;
import me.ratsiel.nbt.model.array.CompoundByteArray;
import me.ratsiel.nbt.model.array.CompoundIntArray;
import me.ratsiel.nbt.model.array.CompoundLongArray;
import me.ratsiel.nbt.model.values.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.GZIPInputStream;

public class NBTInputStream extends DataInputStream {


    public NBTInputStream(InputStream in) {
        super(in);
    }

    /**
     * Reading root {@link CompoundTag} from {@link #in} because NBT Data starts with a {@link CompoundTag}
     * @return {@link CompoundTag}
     * @throws IOException throws exception if something is wrong when reading values
     */
    public CompoundTag readRoot() throws IOException {
        checkCompression();
        NBTType rootType = readTagType();

        if (rootType == NBTType.TAG_BYTE) {
            return new CompoundTag();
        }

        if (rootType != NBTType.TAG_COMPOUND) {
            throw new NBTParseException("Expected CompoundTag at root from NBT data.");
        }

        readCompoundString();
        return readCompoundTag();
    }

    /**
     * Reading {@link CompoundTag} with name and add {@link AbstractCompound} to {@link CompoundTag#getValues()}
     * @return {@link CompoundTag}
     * @throws IOException throws exception if something is wrong when reading {@link CompoundTag}
     */
    private CompoundTag readCompoundTag() throws IOException {
        CompoundTag compoundTag = new CompoundTag();

        boolean finished = false;
        while (!finished) {
            NBTType nbtType = readTagType();

            if (nbtType == null) throw new NBTParseException("NBTType is null given data is not a valid NBTType");

            if (nbtType == NBTType.TAG_END) {
                finished = true;
                continue;
            }

            String compoundName = readCompoundString().getValue();
            AbstractCompound compoundData = readData(nbtType);
            compoundTag.put(compoundName, compoundData);
        }

        return compoundTag;
    }

    /**
     * Iterate through {@link NBTType#values()} with given {@link Integer} from {@link #readByte()} method
     * @return {@link NBTType} or null
     * @throws IOException throws exception if something is wrong in {@link NBTType}
     */
    private NBTType readTagType() throws IOException {
        return NBTType.getNBTType(read());
    }

    /**
     * Read data from given {@link NBTType}
     * @param nbtType given {@link NBTType}
     * @return read {@link Object} of class {@link AbstractCompound} or null
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private AbstractCompound readData(NBTType nbtType) throws IOException {
        switch (nbtType) {
            case TAG_BYTE:
                return readCompoundByte();
            case TAG_SHORT:
                return readCompoundShort();
            case TAG_INT:
                return readCompoundInt();
            case TAG_LONG:
                return readCompoundLong();
            case TAG_FLOAT:
                return readCompoundFloat();
            case TAG_DOUBLE:
                return readCompoundDouble();
            case TAG_BYTE_ARRAY:
                return readCompoundByteArray();
            case TAG_STRING:
                return readCompoundString();
            case TAG_LIST:
                return readCompoundList();
            case TAG_COMPOUND:
                return readCompoundTag();
            case TAG_INT_ARRAY:
                return readCompoundIntArray();
            case TAG_LONG_ARRAY:
                return readCompoundLongArray();
            default:
                return null;
        }
    }


    /**
     * Method to read a {@link CompoundList} from {@link #in}
     * @return {@link CompoundList} filled with {@link AbstractCompound}
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundList readCompoundList() throws IOException {
        CompoundList compoundList = new CompoundList();
        NBTType nbtType = readTagType();

        if (nbtType == null) {
            throw new NBTParseException("NBTType is null given data is not a valid NBTType");
        }


        int length = readInt();

        if (length <= 0) {
            return new CompoundList(nbtType);
        }

        compoundList.setType(nbtType);

        for (int i = 0; i < length; i++) {
            compoundList.add(readData(nbtType));
        }

        return compoundList;
    }


    /**
     *
     * Method to read a {@link CompoundString} from {@link #in}
     * @return {@link CompoundString} with value of class {@link String}
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundString readCompoundString() throws IOException {
        return new CompoundString(readUTF());
    }

    /**
     *
     * Method to read a {@link CompoundByte} from {@link #in}
     * @return {@link CompoundByte} with value of class {@link Byte}
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundByte readCompoundByte() throws IOException {
        return new CompoundByte(readByte());
    }

    /**
     *
     * Method to read a {@link CompoundShort} from {@link #in}
     * @return {@link CompoundShort} with value of class {@link Short}
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundShort readCompoundShort() throws IOException {
        return new CompoundShort(readShort());
    }

    /**
     *
     * Method to read a {@link CompoundInt} from {@link #in}
     * @return {@link CompoundInt} with value of class {@link Integer}
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundInt readCompoundInt() throws IOException {
        return new CompoundInt(readInt());
    }

    /**
     *
     * Method to read a {@link CompoundLong} from {@link #in}
     * @return {@link CompoundLong} with value of class {@link Long}
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundLong readCompoundLong() throws IOException {
        return new CompoundLong(readLong());
    }

    /**
     *
     * Method to read a {@link CompoundFloat} from {@link #in}
     * @return {@link CompoundFloat} with value of class {@link Float}
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundFloat readCompoundFloat() throws IOException {
        return new CompoundFloat(readFloat());
    }

    /**
     *
     * Method to read a {@link CompoundDouble} from {@link #in}
     * @return {@link CompoundDouble} with value of class {@link Double}
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundDouble readCompoundDouble() throws IOException {
        return new CompoundDouble(readDouble());
    }

    /**
     *
     * Method to read a {@link CompoundByteArray} from {@link #in}
     * @return {@link CompoundByteArray} with value of class <code>Byte[]</code>
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundByteArray readCompoundByteArray() throws IOException {
        int arrayLength = readInt();
        byte[] array = new byte[arrayLength];
        readFully(array);
        return new CompoundByteArray(array);
    }

    /**
     *
     * Method to read a {@link CompoundIntArray} from {@link #in}
     * @return {@link CompoundIntArray} with value of class <code>Integer[]</code>
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundIntArray readCompoundIntArray() throws IOException {
        int arrayLength = readInt();
        int[] array = new int[arrayLength];
        for (int i = 0; i < arrayLength; ++i) {
            array[i] = readInt();
        }
        return new CompoundIntArray(array);
    }

    /**
     *
     * Method to read a {@link CompoundLongArray} from {@link #in}
     * @return {@link CompoundLongArray} with value of class <code>Long[]</code>
     * @throws IOException throws exception if something is wrong in the read methods
     */
    private CompoundLongArray readCompoundLongArray() throws IOException {
        int arrayLength = readInt();
        long[] array = new long[arrayLength];
        for (int i = 0; i < arrayLength; ++i) {
            array[i] = readLong();
        }
        return new CompoundLongArray(array);
    }

    /**
     * Reads the first 2 bytes from {@link #in} and check if it is equals to {@link GZIPInputStream#GZIP_MAGIC}.
     * Throws an {@link NBTParseException} if something went wrong.
     */
    private void checkCompression() {
        this.in = new PushbackInputStream(this.in, 2);

        try {

            byte firstByte = readByte();
            byte secondByte = readByte();

            ((PushbackInputStream) this.in).unread(new byte[]{firstByte, secondByte});

            int header = (firstByte & 0xff | ((secondByte << 8) & 0xff00));


            if (header == GZIPInputStream.GZIP_MAGIC) {
                this.in = new GZIPInputStream(this.in);
            }

        } catch (IOException exception) {
            throw new NBTParseException("Could not determine if file is compressed or not!");
        }
    }

}
