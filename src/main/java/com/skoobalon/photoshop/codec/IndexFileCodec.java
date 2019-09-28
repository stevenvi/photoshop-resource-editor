package com.skoobalon.photoshop.codec;

import com.google.common.primitives.Bytes;
import com.skoobalon.photoshop.bean.ImageMetadata;
import com.skoobalon.photoshop.bean.IndexFile;

import java.util.*;

import static com.skoobalon.photoshop.codec.ImageMetadataCodec.IMAGE_DATA_BLOCK_LENGTH;

/**
 * 0000-0021: Header ("Photoshop Icon Resource Index 1.0\n")
 * 0022-00a1: Filename of LoRes icons, zero-terminated string with newline ("\n") as the final byte in the range
 * 00a2-0121: Filename of HiRes icons
 * 0122-01a1: Filename of LoRes data header file
 * 01a2-0221: Filename of HiRes data header file
 * 0222-EOF:      Image data
 */
public class IndexFileCodec implements Codec<IndexFile> {
    private static final String HEADER = "Photoshop Icon Resource Index 1.0\n";
    private static final byte[] HEADER_BYTES = HEADER.getBytes();

    private static final int DATAFILE_NAME_LENGTH = 128;

    private final ImageMetadataCodec imageDataCodec = new ImageMetadataCodec();


    @Override
    public byte[] serialize(IndexFile input) {
        final byte[] data = new byte[0x222 + (input.getImageMetadata().size() * IMAGE_DATA_BLOCK_LENGTH)];
        System.arraycopy(HEADER_BYTES, 0, data, 0, HEADER_BYTES.length);
        writeDatafileName(data, 0x22, input.getLoResDatafile());
        writeDatafileName(data, 0xa2, input.getHiResDatafile());
        writeDatafileName(data, 0x122, input.getLoResHeaderfile());
        writeDatafileName(data, 0x1a2, input.getHiResHeaderfile());

        int idx = 0x222;
        for (ImageMetadata id : input.getImageMetadata().values()) {
            final byte[] idBytes = imageDataCodec.serialize(id);
            System.arraycopy(idBytes, 0, data, idx, idBytes.length);
            idx += IMAGE_DATA_BLOCK_LENGTH;
        }

        return data;
    }

    @Override
    public IndexFile deserialize(byte[] data) {
        final IndexFile out = new IndexFile();
        // Verify header
        final String actualHeader = new String(Arrays.copyOfRange(data, 0, 0x22));
        if (!HEADER.equals(actualHeader)) {
            throw new RuntimeException("Unexpected header: \"" + actualHeader + "\"");
        }

        // Read filenames
        out.setLoResDatafile(readDatafileName(data, 0x22));
        out.setHiResDatafile(readDatafileName(data, 0xa2));
        out.setLoResHeaderfile(readDatafileName(data, 0x122));
        out.setHiResHeaderfile(readDatafileName(data, 0x1a2));

        // Read images
        final Map<String, ImageMetadata> imageMetadata = new HashMap<>();
        for (int idx = 0x222; idx < data.length; idx += 368) {
            ImageMetadata md = imageDataCodec.deserialize(Arrays.copyOfRange(data, idx, idx + IMAGE_DATA_BLOCK_LENGTH));
            imageMetadata.put(md.getName(), md);
        }
        out.setImageMetadata(imageMetadata);

        return out;
    }

    private static String readDatafileName(byte[] data, int offset) {
        final byte[] buf = Arrays.copyOfRange(data, offset, offset + DATAFILE_NAME_LENGTH);
        int zeroIdx = Bytes.indexOf(buf, (byte)0);
        if (zeroIdx == -1) {
            zeroIdx = DATAFILE_NAME_LENGTH;
        }
        return new String(Arrays.copyOfRange(buf, 0, zeroIdx));
    }

    private static void writeDatafileName(byte[] data, int offset, String name) {
        final byte[] nameBytes = name.getBytes();
        System.arraycopy(nameBytes, 0, data, offset, Math.min(nameBytes.length, DATAFILE_NAME_LENGTH));
        data[offset + DATAFILE_NAME_LENGTH - 1] = '\n';
    }
}
