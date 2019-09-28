package com.skoobalon.photoshop.codec;

import com.skoobalon.photoshop.bean.PngData;

import java.util.Arrays;

public class PngDataCodec implements Codec<PngData> {
    @Override
    public byte[] serialize(PngData input) {
        return Arrays.copyOf(input.getData(), input.getData().length);
    }

    @Override
    public PngData deserialize(byte[] data) {
        PngData pd = new PngData();
        pd.setData(Arrays.copyOf(data, data.length));
        return pd;
    }
}
