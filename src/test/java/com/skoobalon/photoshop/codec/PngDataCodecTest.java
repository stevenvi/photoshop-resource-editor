package com.skoobalon.photoshop.codec;

import com.skoobalon.photoshop.bean.PngData;
import org.testng.annotations.Test;

import java.util.Random;

import static com.skoobalon.photoshop.util.TestUtil.randomBytes;
import static org.testng.Assert.assertEquals;

public class PngDataCodecTest {

    private final PngDataCodec codec = new PngDataCodec();
    private final Random random = new Random();

    @Test(invocationCount = 1000)
    public void codecTest() {
        final PngData a = new PngData();
        a.setData(randomBytes(1024 * random.nextInt(128)));

        final byte[] aBytes = codec.serialize(a);
        final PngData b = codec.deserialize(aBytes);
        final byte[] bBytes = codec.serialize(b);

        assertEquals(a, b);
        assertEquals(aBytes, bBytes);
    }
}
