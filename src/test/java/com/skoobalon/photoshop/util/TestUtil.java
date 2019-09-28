package com.skoobalon.photoshop.util;

import com.google.common.primitives.Bytes;
import com.skoobalon.photoshop.codec.DatafileCodec;

import java.util.Random;

public final class TestUtil {
    private static final Random random = new Random();

    public static byte[] randomBytes(int numBytes) {
        final byte[] data = new byte[numBytes];
        random.nextBytes(data);
        return data;
    }

    public static byte[] generateFakePng(int length) {
        final byte[] data = randomBytes(length);
        // Safety check: we don't want the footer to end up randomly generated in our random array, so break them all
        while (true) {
            int idx = Bytes.indexOf(data, DatafileCodec.PNG_FOOTER);
            if (idx == -1) {
                break;
            }
            data[idx] = data[idx]++;
        }

        System.arraycopy(DatafileCodec.PNG_HEADER, 0, data, 0, DatafileCodec.PNG_HEADER.length);
        System.arraycopy(DatafileCodec.PNG_FOOTER, 0, data, data.length - DatafileCodec.PNG_FOOTER.length, DatafileCodec.PNG_FOOTER.length);
        return data;
    }

}
