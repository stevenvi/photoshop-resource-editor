package com.skoobalon.photoshop.codec;

import com.google.common.primitives.Bytes;
import com.skoobalon.photoshop.bean.Datafile;
import com.skoobalon.photoshop.bean.PngData;

import java.util.*;

public class DatafileCodec implements Codec<Datafile> {
    public static final int HEADER_LENGTH = 4;
    public static final byte[] PNG_HEADER = new byte[] {(byte)137, 80, 78, 71, 13, 10, 26, 10};
    public static final byte[] PNG_FOOTER = new byte[] {73, 69, 78, 68};

    @Override
    public byte[] serialize(Datafile input) {
        final int minSize = input.getHeader().length +
                                input.getPngs().values().stream()
                                        .mapToInt(png -> png.getData().length)
                                        .sum();
        final int lastPngOffset = input.getPngs().size() == 0 ? minSize : input.getPngs().keySet().stream().mapToInt(i -> i).max().getAsInt();
        final int maxSize = input.getPngs().get(lastPngOffset).getData().length + lastPngOffset;

        if (minSize > maxSize) {
            throw new IllegalStateException("Unable to correctly establish the size of the datafile");
        }
        final byte[] out = new byte[input.getHeader().length + maxSize];

        // Header
        int offset = input.getHeader().length;
        System.arraycopy(input.getHeader(), 0, out, 0, offset);

        // PNGs
        input.getPngs().forEach((k, v) -> {
            try {
                System.arraycopy(v.getData(), 0, out, k, v.getData().length);
            } catch (Exception e) {
                System.out.println("System.arraycopy(..., 0, [" + out.length + "], " + k + ", " + v.getData().length);
                throw e;
            }
        });

        return out;
    }

    @Override
    public Datafile deserialize(byte[] data) {
        final Datafile df = new Datafile();

        // First four bytes are the header
        df.setHeader(Arrays.copyOfRange(data, 0, 4));

        // Everything else from here on out is png data
        final Map<Integer, PngData> pngs = new HashMap<>();
        df.setPngs(pngs);

        // Rip out pngs
        data = Arrays.copyOfRange(data, 4, data.length);
        int offset = 4;
        while (data.length > 12) {
            // Find the first and next instance of the PNG header
            int start = Bytes.indexOf(data, PNG_HEADER);
            int end = Bytes.indexOf(data, PNG_FOOTER) + PNG_FOOTER.length + 4;

            // Rip it out
            final PngData png = new PngData();
            png.setData(Arrays.copyOfRange(data, start, end));
            pngs.put(offset + start, png);

            // Pull that out of the data array now
            data = Arrays.copyOfRange(data, end, data.length);
            offset += end;
        }

        return df;
    }
}
