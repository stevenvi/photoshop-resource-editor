package com.skoobalon.photoshop.util;

import com.skoobalon.photoshop.bean.*;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;

import static com.skoobalon.photoshop.util.ResourcePackUtil.verifyConsistency;
import static com.skoobalon.photoshop.util.TestUtil.generateFakePng;
import static org.testng.Assert.assertEquals;

public class ResourcePackUtilTest {

    private final Random random = new Random();

    private ResourcePack generateResourcePack() {
        // Create empty resource pack
        final ResourcePack res = new ResourcePack();
        res.setIndex(new IndexFile());
        res.setLoresDatafile(new Datafile());
        res.setLoresHeaderfile(new Headerfile());
        res.setHiresDatafile(new Datafile());
        res.setHiresHeaderfile(new Headerfile());

        // Add some pngs to the lores data
        int offset = random.nextInt(12);
        for (int i = 0; i < 25; i++) {
            final PngData png = new PngData();
            png.setData(generateFakePng(16 * (1 + random.nextInt(1024))));
            res.getLoresDatafile().getPngs().put(offset, png);

            final ImageMetadata im = new ImageMetadata();
            im.setSizeLo(png.getData().length);
            im.setSizeLoOld(random.nextInt(im.getSizeLo()));
            im.setName("png" + i);
            im.setWidthLo(1 + random.nextInt(1920));
            im.setLengthLo(1 + random.nextInt(1080));
            im.setOffsetLo(offset);
            im.setOffsetLoOld(random.nextInt(Math.max(1, offset)));
            res.getIndex().getImageMetadata().put(im.getName(), im);

            offset += png.getData().length;
        }

        verifyConsistency(res);
        return res;
    }

    @Test(invocationCount = 1000)
    public void replaceLoResPngTestIdentical() throws Exception {
        // Replace each element with itself
        final ResourcePack res = generateResourcePack();
        final ResourcePack resOriginal = new ResourcePack(res);
        assertEquals(res, resOriginal);

        for (Map.Entry<String, ImageMetadata> entry : res.getIndex().getImageMetadata().entrySet()) {
            ResourcePackUtil.replaceLoResPng(res, entry.getKey(), res.getLoresDatafile().getPngs().get(entry.getValue().getOffsetLo()));
            assertEquals(res, resOriginal, "Swapping " + entry.getKey() + " failed");
        }
    }

    @Test(invocationCount = 1000)
    public void replaceLoResPngTestDifferent() throws Exception {
        // Replace an element with one that is smaller than it
        final ResourcePack res = generateResourcePack();
        final ResourcePack resOriginal = new ResourcePack(res);
        assertEquals(res, resOriginal);

        // Find the smallest and largest elements, swap them, then swap them back
        final ImageMetadata smallestMetadata = res.getIndex().getImageMetadata().values().stream().min(Comparator.comparingInt(ImageMetadata::getSizeLo)).get();
        final ImageMetadata largestMetadata = res.getIndex().getImageMetadata().values().stream().max(Comparator.comparingInt(ImageMetadata::getSizeLo)).get();
        for (int i = 0; i < 2; i++) {
            final PngData small = res.getLoresDatafile().getPngs().get(smallestMetadata.getOffsetLo());
            final PngData large = res.getLoresDatafile().getPngs().get(largestMetadata.getOffsetLo());
            ResourcePackUtil.replaceLoResPng(res, largestMetadata.getName(), small);
            ResourcePackUtil.replaceLoResPng(res, smallestMetadata.getName(), large);
        }

        assertEquals(res, resOriginal);
    }

    @Test
    public void replaceHiResPngTest() {
        // TODO:
    }
}
