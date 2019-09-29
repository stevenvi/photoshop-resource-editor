package com.skoobalon.photoshop.cli;

import com.skoobalon.photoshop.bean.PngData;
import com.skoobalon.photoshop.bean.ResourcePack;
import com.skoobalon.photoshop.codec.PngDataCodec;
import com.skoobalon.photoshop.util.ResourcePackUtil;

import java.io.File;

public class ReplaceSingleResource extends AbstractCli {
    private static final String DEFAULT_RESOURCE = "Splash1080Background";

    private static final PngDataCodec pngDataCodec = new PngDataCodec();

    public static void main(String[] args) throws Exception {
        final String psPath = promptForPhotoshopPath();
        final String resourceToReplace = prompt("Resource to replace", DEFAULT_RESOURCE);
        final String loresImage = prompt("LoRes Replacement File", DEFAULT_EXTRACTED_DATA_PATH + File.separator + "lores" + File.separator + resourceToReplace + ".png");
        final String hiresImage = prompt("HiRes Replacement File", DEFAULT_EXTRACTED_DATA_PATH + File.separator + "hires" + File.separator + resourceToReplace + ".png");

        System.out.println("NOTE: It is recommended that you not overwrite your Photoshop resource files with this script.");
        System.out.println("Instead, make a backup first and then move these files over the originals.");
        final String outputPath = promptForOutputPath();

        System.out.println("Reading data...");
        final ResourcePack res = ResourcePackUtil.read(psPath + File.separator + RESOURCE_DIR);
        final PngData lores = pngDataCodec.deserialize(loresImage);
        final PngData hires = pngDataCodec.deserialize(hiresImage);

        System.out.println("Replacing images...");
        ResourcePackUtil.replaceLoResPngWithSmallerCopy(res, resourceToReplace, lores);
        ResourcePackUtil.replaceHiResPngWithSmallerCopy(res, resourceToReplace, hires);

        System.out.println("Outputting data...");
        ResourcePackUtil.write(res, outputPath);

        System.out.println("Done!");
    }
}
