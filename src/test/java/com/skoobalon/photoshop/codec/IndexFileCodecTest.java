package com.skoobalon.photoshop.codec;

import com.skoobalon.photoshop.bean.ImageMetadata;
import com.skoobalon.photoshop.bean.IndexFile;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

public class IndexFileCodecTest {
    @Test(invocationCount = 1000)
    public void codecTest() {
        final IndexFile a = new IndexFile();
        a.setLoResDatafile(UUID.randomUUID().toString());
        a.setHiResDatafile(UUID.randomUUID().toString());
        a.setLoResHeaderfile(UUID.randomUUID().toString());
        a.setHiResHeaderfile(UUID.randomUUID().toString());
        a.setImageMetadata(Map.of("a", new ImageMetadata(), "b", new ImageMetadata()));

        final IndexFileCodec codec = new IndexFileCodec();
        final byte[] aBytes = codec.serialize(a);
        final IndexFile b = codec.deserialize(aBytes);
        final byte[] bBytes = codec.serialize(b);

        assertEquals(a, b);
        assertEquals(aBytes, bBytes);
    }
}
