package com.skoobalon.photoshop.codec;

import com.skoobalon.photoshop.bean.Headerfile;
import org.testng.annotations.Test;

import java.util.Random;

import static com.skoobalon.photoshop.util.TestUtil.randomBytes;
import static org.testng.Assert.assertEquals;

public class HeaderfileCodecTest {

    private final HeaderfileCodec codec = new HeaderfileCodec();
    private final Random random = new Random();

    @Test(invocationCount = 1000)
    public void codecTest() {
        final Headerfile a = new Headerfile();
        a.setData(randomBytes(128 + random.nextInt(1024 - 128)));

        final byte[] aBytes = codec.serialize(a);
        final Headerfile b = codec.deserialize(aBytes);
        final byte[] bBytes = codec.serialize(b);

        assertEquals(a, b);
        assertEquals(aBytes, bBytes);
    }
}
