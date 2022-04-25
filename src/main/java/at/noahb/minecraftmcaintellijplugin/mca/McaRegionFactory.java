package at.noahb.minecraftmcaintellijplugin.mca;

import at.noahb.minecraftmcaintellijplugin.mca.model.McaRegionFile;
import com.intellij.openapi.vfs.VirtualFile;
import me.ratsiel.mca.model.RegionFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("DanglingJavadoc")
public class McaRegionFactory {

    /**
     * Loads a {@link McaRegionFile} from {@link File}
     *
     * @param file input of {@link File}
     * @return object of {@link McaRegionFile}
     * @throws IOException if something went wrong
     */
    public static McaRegionFile loadRegion(VirtualFile file) throws IOException {
        McaRegionFile regionFile = new McaRegionFile(file);
        regionFile.load();
        return regionFile;
    }


    /**
     * Loads a {@link List} filled with objects of {@link RegionFile} from {@link File}
     *
     * @param regionsFolder input of {@link File}
     * @return {@link List} filled with objects of {@link RegionFile}
     */
//    public static List<RegionFile> loadRegions(File regionsFolder) {
//
//        if (regionsFolder.length() > 0) {
//
//            List<RegionFile> regionFiles = new ArrayList<>();
//
//            for (File regionFile : Objects.requireNonNull(regionsFolder.listFiles())) {
//                if (regionFile.isFile()) {
//                    try {
//                        regionFiles.add(loadRegion(regionFile));
//                    } catch (IOException ignored) {
//                    }
//                }
//            }
//
//            return regionFiles;
//        }
//
//        return Collections.emptyList();
//
//    }


}
