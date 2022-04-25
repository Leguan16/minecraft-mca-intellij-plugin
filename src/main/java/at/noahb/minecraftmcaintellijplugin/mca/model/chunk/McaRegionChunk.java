package at.noahb.minecraftmcaintellijplugin.mca.model.chunk;

import com.github.tth05.minecraftnbtintellijplugin.NBTTagTreeNode;
import me.ratsiel.nbt.model.CompoundTag;
import me.ratsiel.nbt.model.values.CompoundInt;

public class McaRegionChunk {

    private final int x;
    private final int z;
    private final NBTTagTreeNode levelData;

    public McaRegionChunk(NBTTagTreeNode levelData, int x, int z) {
        this.levelData = levelData;
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public NBTTagTreeNode getLevelData() {
        return levelData;
    }

}
