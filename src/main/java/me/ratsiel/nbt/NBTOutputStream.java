package me.ratsiel.nbt;

import me.ratsiel.nbt.abstracts.AbstractCompound;
import me.ratsiel.nbt.enums.NBTType;
import me.ratsiel.nbt.model.CompoundList;
import me.ratsiel.nbt.model.CompoundTag;
import me.ratsiel.nbt.model.array.CompoundByteArray;
import me.ratsiel.nbt.model.array.CompoundIntArray;
import me.ratsiel.nbt.model.array.CompoundLongArray;
import me.ratsiel.nbt.model.values.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class NBTOutputStream extends DataOutputStream {

    public NBTOutputStream(OutputStream out, boolean compressed) throws IOException {
        super(compressed ? new GZIPOutputStream(out) : out);
    }

    /**
     * Writing root {@link CompoundTag} to {@link #out}
     * @param compoundTag given root {@link CompoundTag}
     * @throws IOException throws exception if something is wrong when writing values
     */
    public void writeNBT(CompoundTag compoundTag) throws IOException {
        if (compoundTag == null) {
            writeNBTType(NBTType.TAG_END);
        } else {
            writeNBTType(NBTType.TAG_COMPOUND);
            writeCompoundString(new CompoundString(""));
            writeCompoundTag(compoundTag);
        }

        if (out instanceof GZIPOutputStream) {
            ((GZIPOutputStream) out).finish();
        }

        out.flush();
        out.close();
    }


    /**
     * Write {@link NBTType} before a value is going to be written in {@link #out}
     * @param nbtType given {@link NBTType}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeNBTType(NBTType nbtType) throws IOException {
        if (nbtType == null) {
            writeNBTType(NBTType.TAG_END);
            return;
        }

        writeCompoundByte(new CompoundByte((byte) nbtType.getId()));
    }

    /**
     * Writing {@link CompoundInt} and {@link CompoundString} to {@link #out}
     * @param compoundString given {@link CompoundInt}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundString(CompoundString compoundString) throws IOException {
        if (compoundString == null || compoundString.getValue() == null) {
            writeCompoundInt(new CompoundInt(0));
            return;
        }

        byte[] bytes = compoundString.getValue().getBytes(StandardCharsets.UTF_8);
        writeChar(bytes.length);
        write(bytes);
    }

    /**
     * Writing {@link CompoundTag} to {@link #out}
     * @param compoundTag given {@link CompoundTag}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundTag(CompoundTag compoundTag) throws IOException {
        for (Map.Entry<String, AbstractCompound> entry : compoundTag.getValues().entrySet()) {
            String key = entry.getKey();
            AbstractCompound compound = entry.getValue();
            NBTType nbtType = NBTType.toType(compound);

            writeNBTType(nbtType);
            writeCompoundString(new CompoundString(key));
            writeCompoundValue(compound);
        }

        writeNBTType(NBTType.TAG_END);
    }

    /**
     * Writing {@link NBTType}, {@link CompoundInt} and values from {@link CompoundList} to {@link #out}
     * @param compoundList given {@link CompoundList}
     * @throws IOException throws exception if something is wrong when writing values
     */
    public void writeCompoundList(CompoundList compoundList) throws IOException {
        if (compoundList == null) {
            writeNBTType(NBTType.TAG_END);
            writeCompoundInt(new CompoundInt(0));
            return;
        }

        writeNBTType(compoundList.getType());
        writeCompoundInt(new CompoundInt(compoundList.getValues().size()));

        for (AbstractCompound compound : compoundList.getValues()) {
            writeCompoundValue(compound);
        }
    }

    /**
     * Writing {@link CompoundInt} and <code>Byte[]</code> to {@link #out}
     * @param compoundByteArray given {@link CompoundByteArray}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundByteArray(CompoundByteArray compoundByteArray) throws IOException {
        if (compoundByteArray == null || compoundByteArray.getValue() == null) {
            writeCompoundInt(new CompoundInt(0));
            return;
        }

        Byte[] bytes = compoundByteArray.getValue();

        writeCompoundInt(new CompoundInt(bytes.length));
        for (Byte value : bytes) {
            writeCompoundByte(new CompoundByte(value));
        }
    }

    /**
     * Writing {@link CompoundInt} and <code>Integer[]</code> to {@link #out}
     * @param compoundIntArray given {@link CompoundIntArray}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundIntArray(CompoundIntArray compoundIntArray) throws IOException {
        if (compoundIntArray == null || compoundIntArray.getValue() == null) {
            writeCompoundInt(new CompoundInt(0));
            return;
        }

        Integer[] integers = compoundIntArray.getValue();

        writeCompoundInt(new CompoundInt(integers.length));
        for (Integer value : integers) {
            writeCompoundInt(new CompoundInt(value));
        }
    }

    /**
     * Writing {@link CompoundInt} and <code>Long[]</code> to {@link #out}
     * @param compoundLongArray given {@link CompoundLongArray}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundLongArray(CompoundLongArray compoundLongArray) throws IOException {
        if (compoundLongArray == null || compoundLongArray.getValue() == null) {
            writeCompoundInt(new CompoundInt(0));
            return;
        }

        Long[] integers = compoundLongArray.getValue();

        writeCompoundInt(new CompoundInt(integers.length));
        for (Long value : integers) {
            writeCompoundLong(new CompoundLong(value));
        }
    }


    /**
     * Writing {@link CompoundByte} to {@link #out}
     * @param compoundByte given {@link CompoundByte}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundByte(CompoundByte compoundByte) throws IOException {
        writeByte(compoundByte.getValue());
    }

    /**
     * Writing {@link CompoundDouble} to {@link #out}
     * @param compoundDouble given {@link CompoundDouble}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundDouble(CompoundDouble compoundDouble) throws IOException {
        writeDouble(compoundDouble.getValue());
    }

    /**
     * Writing {@link CompoundFloat} to {@link #out}
     * @param compoundFloat given {@link CompoundFloat}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundFloat(CompoundFloat compoundFloat) throws IOException {
        writeFloat(compoundFloat.getValue());
    }

    /**
     * Writing {@link CompoundInt} to {@link #out}
     * @param compoundInt given {@link CompoundInt}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundInt(CompoundInt compoundInt) throws IOException {
        writeInt(compoundInt.getValue());
    }

    /**
     * Writing {@link CompoundLong} to {@link #out}
     * @param compoundLong given {@link CompoundLong}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundLong(CompoundLong compoundLong) throws IOException {
        writeLong(compoundLong.getValue());
    }

    /**
     * Writing {@link CompoundShort} to {@link #out}
     * @param compoundShort given {@link CompoundShort}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundShort(CompoundShort compoundShort) throws IOException {
        writeShort(compoundShort.getValue());
    }

    /**
     * Writing using writing methods to {@link #out} or throw {@link IOException}
     * @param compound given {@link AbstractCompound}
     * @throws IOException throws exception if something is wrong when writing values
     */
    private void writeCompoundValue(AbstractCompound compound) throws IOException {
        NBTType nbtType = NBTType.toType(compound);

        switch (nbtType) {
            case TAG_BYTE:
                writeCompoundByte((CompoundByte) compound);
                break;
            case TAG_SHORT:
                writeCompoundShort((CompoundShort) compound);
                break;
            case TAG_INT:
                writeCompoundInt((CompoundInt) compound);
                break;
            case TAG_LONG:
                writeCompoundLong((CompoundLong) compound);
                break;
            case TAG_FLOAT:
                writeCompoundFloat((CompoundFloat) compound);
                break;
            case TAG_DOUBLE:
                writeCompoundDouble((CompoundDouble) compound);
                break;
            case TAG_STRING:
                writeCompoundString((CompoundString) compound);
                break;
            case TAG_BYTE_ARRAY:
                writeCompoundByteArray((CompoundByteArray) compound);
                break;
            case TAG_INT_ARRAY:
                writeCompoundIntArray((CompoundIntArray) compound);
                break;
            case TAG_LONG_ARRAY:
                writeCompoundLongArray((CompoundLongArray) compound);
                break;
            case TAG_LIST:
                writeCompoundList((CompoundList) compound);
                break;
            case TAG_COMPOUND:
                writeCompoundTag((CompoundTag) compound);
                break;
            default:
                throw new IOException("NBTTag '" + nbtType.name() + "' is not allowed");
        }
    }


}
