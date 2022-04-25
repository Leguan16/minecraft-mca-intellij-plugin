package me.ratsiel.mca;

import me.ratsiel.mca.model.RegionFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RegionFactory {

    /**
     * Loads a {@link RegionFile} from {@link File}
     *
     * @param file input of {@link File}
     * @return object of {@link RegionFile}
     * @throws IOException if something went wrong
     */
    public static RegionFile loadRegion(File file) throws IOException {
        RegionFile regionFile = new RegionFile(file);
        regionFile.load();
        return regionFile;
    }


    /**
     * Loads a {@link List} filled with objects of {@link RegionFile} from {@link File}
     *
     * @param regionsFolder input of {@link File}
     * @return {@link List} filled with objects of {@link RegionFile}
     */
    public static List<RegionFile> loadRegions(File regionsFolder) {

        if (regionsFolder.length() > 0) {

            List<RegionFile> regionFiles = new ArrayList<>();

            for (File regionFile : Objects.requireNonNull(regionsFolder.listFiles())) {
                if (regionFile.isFile()) {
                    try {
                        regionFiles.add(loadRegion(regionFile));
                    } catch (IOException ignored) {
                    }
                }
            }

            return regionFiles;
        }

        return Collections.emptyList();

    }


}
