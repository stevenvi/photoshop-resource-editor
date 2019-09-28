package com.skoobalon.photoshop.cli;

import com.skoobalon.photoshop.bean.ImageMetadata;
import com.skoobalon.photoshop.bean.PngData;
import com.skoobalon.photoshop.bean.ResourcePack;
import com.skoobalon.photoshop.codec.PngDataCodec;
import com.skoobalon.photoshop.util.ResourcePackUtil;

import java.io.File;
import java.util.Map;

public class Extract extends AbstractCli {

    public static void main(String[] args) throws Exception {
        final String psPath = promptForPhotoshopPath() + File.separator + RESOURCE_DIR;
        final String outputPath = promptForOutputPath();
        final boolean overwrite = promptForOverwrite();

        // Read all data
        System.out.println("Reading data...");
        final ResourcePack rp = ResourcePackUtil.read(psPath);
        System.out.println(String.format("Read %s low res pngs", rp.getLoresDatafile().getPngs().size()));
        System.out.println(String.format("Read %s high res pngs", rp.getHiresDatafile().getPngs().size()));

        // Create directories if needed
        final File loresPath = new File(outputPath + File.separator + "lores");
        final File hiresPath = new File(outputPath + File.separator + "hires");
        if (!loresPath.exists()) {
            System.out.print("Creating " + loresPath.getAbsolutePath() + "...");
            System.out.println(loresPath.mkdirs() ? " done" : " failed");
        }
        if (!hiresPath.exists()) {
            System.out.print("Creating " + hiresPath.getAbsolutePath() + "...");
            System.out.println(hiresPath.mkdirs() ? " done" : " failed");
        }

        // Export all pngs
        System.out.println("Exporting data...");
        final PngDataCodec pngCodec = new PngDataCodec();
        int loresCount = 0;
        int hiresCount = 0;
        for (Map.Entry<String, ImageMetadata> entry : rp.getIndex().getImageMetadata().entrySet()) {
            final PngData lores = rp.getLoresDatafile().getPngs().get(entry.getValue().getOffsetLo());
            final PngData hires = rp.getHiresDatafile().getPngs().get(entry.getValue().getOffsetHi());
            if (lores != null) {
                final File f = new File(loresPath.getAbsolutePath() + File.separator + entry.getKey() + ".png");
                if (f.exists() && !overwrite) {
                    System.err.println("Skipping " + f.getAbsolutePath() + " because it already exists");
                } else {
                    pngCodec.serializeToFile(lores, f.getAbsolutePath());
                    loresCount++;
                }
            }
            if (hires != null) {
                final File f = new File(hiresPath.getAbsolutePath() + File.separator + entry.getKey() + ".png");
                if (f.exists() && !overwrite) {
                    System.err.println("Skipping " + f.getAbsolutePath() + " because it already exists");
                } else {
                    pngCodec.serializeToFile(hires, f.getAbsolutePath());
                    hiresCount++;
                }
            }
        }

        System.out.println("\nOutput " + loresCount + " lores images and " + hiresCount + " hires images");
        System.out.println("Enjoy!");
    }
}
