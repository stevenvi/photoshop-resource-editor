package com.skoobalon.photoshop.cli;

import com.skoobalon.photoshop.bean.ImageMetadata;
import com.skoobalon.photoshop.bean.PngData;
import com.skoobalon.photoshop.bean.ResourcePack;
import com.skoobalon.photoshop.codec.PngDataCodec;
import com.skoobalon.photoshop.util.ResourcePackUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Extract extends AbstractCli {
    private static final PngDataCodec pngCodec = new PngDataCodec();

    public static void main(String[] args) throws Exception {
        final String psPath = promptForPhotoshopPath() + File.separator + RESOURCE_DIR;
        final String outputPath = promptForOutputPath();
        final boolean overwrite = promptForOverwrite();

        // Read all data
        System.out.println("Reading data...");
        final ResourcePack res = ResourcePackUtil.read(psPath);
        System.out.println(String.format("Read %s low res pngs", res.getLoresDatafile().getPngs().size()));
        System.out.println(String.format("Read %s high res pngs", res.getHiresDatafile().getPngs().size()));

        try {
            ResourcePackUtil.verifyConsistency(res);
        } catch (Exception e) {
            System.out.println("Consistency check failed. (This is not critical.)");
        }

        // Create directories if needed
        final File loresPath = new File(outputPath + File.separator + "lores");
        final File hiresPath = new File(outputPath + File.separator + "hires");
        createPath(loresPath);
        createPath(hiresPath);

        // Export all pngs
        System.out.println("Exporting data...");
        final Set<Integer> loresImagesWritten = new HashSet<>();
        final Set<Integer> hiresImagesWritten = new HashSet<>();
        for (Map.Entry<String, ImageMetadata> entry : res.getIndex().getImageMetadata().entrySet()) {
            PngData lores = res.getLoresDatafile().getPngs().get(entry.getValue().getOffsetLo());
            if (lores != null) {
                loresImagesWritten.add(entry.getValue().getOffsetLo());
                final File f = new File(loresPath.getAbsolutePath() + File.separator + entry.getKey() + ".png");
                writePng(f, lores, overwrite);
            }
            PngData hires = res.getHiresDatafile().getPngs().get(entry.getValue().getOffsetHi());
            if (hires != null) {
                hiresImagesWritten.add(entry.getValue().getOffsetHi());
                final File f = new File(hiresPath.getAbsolutePath() + File.separator + entry.getKey() + ".png");
                writePng(f, hires, overwrite);
            }
        }

        // Now export the ones that aren't actually referenced in the index
        final Map<Integer, PngData> loresNotReferenced = new HashMap<>(res.getLoresDatafile().getPngs());
        loresImagesWritten.forEach(loresNotReferenced::remove);
        for (Map.Entry<Integer, PngData> entry : loresNotReferenced.entrySet()) {
            final File f = new File(String.format("%s%s%08d.png", loresPath.getAbsolutePath(), File.separator, entry.getKey()));
            writePng(f, entry.getValue(), overwrite);
        }

        final Map<Integer, PngData> hiresNotReferenced = new HashMap<>(res.getHiresDatafile().getPngs());
        hiresImagesWritten.forEach(hiresNotReferenced::remove);
        for (Map.Entry<Integer, PngData> entry : hiresNotReferenced.entrySet()) {
            final File f = new File(String.format("%s%s%08d.png", hiresPath.getAbsolutePath(), File.separator, entry.getKey()));
            writePng(f, entry.getValue(), overwrite);
        }

        System.out.println("Done!");
    }

    private static void writePng(File f, PngData png, boolean overwrite) throws IOException {
        if (f.exists() && !overwrite) {
            System.err.println("Skipping " + f.getAbsolutePath() + " because it already exists");
        } else {
            pngCodec.serializeToFile(png, f.getAbsolutePath());
        }
    }
}
