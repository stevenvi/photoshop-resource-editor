package com.skoobalon.photoshop.codec;

import com.skoobalon.photoshop.bean.ImageMetadata;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

public class ImageMetadataCodecTest {
    @Test(invocationCount = 1000)
    public void codecTest() {
        final Random r = new Random();

        final ImageMetadata a = new ImageMetadata();
        a.setName(UUID.randomUUID().toString());
        a.setWidthLo(r.nextInt());
        a.setWidthHi(r.nextInt());
        a.setLengthLo(r.nextInt());
        a.setLengthHi(r.nextInt());
        a.setOffsetLo(r.nextInt());
        a.setOffsetLoOld(r.nextInt());
        a.setOffsetHi(r.nextInt());
        a.setOffsetHiOld(r.nextInt());
        a.setSizeLo(r.nextInt());
        a.setSizeLoOld(r.nextInt());
        a.setSizeHi(r.nextInt());
        a.setSizeHiOld(r.nextInt());

        final ImageMetadataCodec codec = new ImageMetadataCodec();
        final byte[] aBytes = codec.serialize(a);
        final ImageMetadata b = codec.deserialize(aBytes);
        final byte[] bBytes = codec.serialize(b);

        assertEquals(a, b);
        assertEquals(aBytes, bBytes);
    }
}
