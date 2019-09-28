package com.skoobalon.photoshop.codec;

import com.skoobalon.photoshop.bean.Headerfile;

import java.util.Arrays;

public class HeaderfileCodec implements Codec<Headerfile> {

    @Override
    public byte[] serialize(Headerfile input) {
        return input.getData();
    }

    @Override
    public Headerfile deserialize(byte[] data) {
        Headerfile headerfile = new Headerfile();
        headerfile.setData(Arrays.copyOf(data, data.length));
        return headerfile;
    }
}
