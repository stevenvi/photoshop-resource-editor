package com.skoobalon.photoshop.codec;

import com.skoobalon.photoshop.bean.Datafile;
import com.skoobalon.photoshop.bean.PngData;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.skoobalon.photoshop.util.TestUtil.generateFakePng;
import static com.skoobalon.photoshop.util.TestUtil.randomBytes;
import static org.testng.Assert.assertEquals;

public class DatafileCodecTest {

    private final DatafileCodec codec = new DatafileCodec();
    private final Random random = new Random();

    @Test(invocationCount = 1000)
    public void codecTest() {
        final Datafile a = new Datafile();
        a.setHeader(randomBytes(DatafileCodec.HEADER_LENGTH));

        // Generate between 1k and 2k pngs of 1k-10k each
        int len = 10 + random.nextInt(10);
        final Map<Integer, PngData> pngDataMap = new HashMap<>(len);
        int offset = DatafileCodec.HEADER_LENGTH;
        for (int i = 0; i < len; i++) {
            final PngData png = new PngData();
            png.setData(generateFakePng(1024 + random.nextInt(9*1024)));
            pngDataMap.put(offset, png);
            offset += png.getData().length;
        }
        a.setPngs(pngDataMap);

        // Test it
        final byte[] aBytes = codec.serialize(a);
        final Datafile b = codec.deserialize(aBytes);
        final byte[] bBytes = codec.serialize(b);

        assertEquals(a, b);
        assertEquals(aBytes, bBytes);
    }

}
