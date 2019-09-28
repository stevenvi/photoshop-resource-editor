package com.skoobalon.photoshop.codec;

import com.google.common.primitives.Bytes;
import com.skoobalon.photoshop.bean.ImageMetadata;

/**
 * Each image data block is 368 bytes. All unspecified bytes are zeroed out
 *
 * 0000-002F: Filename, zero-terminated string
 * 0030-0033: width of lores image
 * 0034-0037: width of hires image
 * 0040-0043: height of lores image
 * 0044-0047: height of hires image
 * 0070-0073: byte offset of lores image
 * 0088-008B: ?? something similar to lores image offset
 * 0090-0093: byte offset of hires image
 * 00A8-00AB: ?? something similar to hires image offset
 * 00F0-00F3: size of lores image in bytes
 * 0108-010B: ?? something similar to lores image in bytes
 * 0110-0113: size of hires image in bytes
 * 0128-012B: ?? something similar to hires image in bytes
 */
public class ImageMetadataCodec implements Codec<ImageMetadata> {

    public static final int IMAGE_DATA_BLOCK_LENGTH = 368;

    @Override
    public byte[] serialize(ImageMetadata input) {
        final byte[] data = new byte[IMAGE_DATA_BLOCK_LENGTH];
        final byte[] name = input.getName().getBytes();
        System.arraycopy(name, 0, data, 0, Math.min(name.length, 0x30));
        writeInt(data, 0x30, input.getWidthLo());
        writeInt(data, 0x34, input.getWidthHi());
        writeInt(data, 0x40, input.getLengthLo());
        writeInt(data, 0x44, input.getLengthHi());
        writeInt(data, 0x70, input.getOffsetLo());
        writeInt(data, 0x88, input.getOffsetLoOld());
        writeInt(data, 0x90, input.getOffsetHi());
        writeInt(data, 0xA8, input.getOffsetHiOld());
        writeInt(data, 0xF0, input.getSizeLo());
        writeInt(data, 0x108, input.getSizeLoOld());
        writeInt(data, 0x110, input.getSizeHi());
        writeInt(data, 0x128, input.getSizeHiOld());
        return data;
    }

    @Override
    public ImageMetadata deserialize(byte[] data) {
        final ImageMetadata imageMetadata = new ImageMetadata();

        int zeroIdx = Bytes.indexOf(data, (byte)0);
        if (zeroIdx == -1) {
            zeroIdx = 0x30;
        }

        imageMetadata.setName(new String(data, 0, zeroIdx));
        imageMetadata.setWidthLo(readInt(data, 0x30));
        imageMetadata.setWidthHi(readInt(data, 0x34));
        imageMetadata.setLengthLo(readInt(data, 0x40));
        imageMetadata.setLengthHi(readInt(data, 0x44));
        imageMetadata.setOffsetLo(readInt(data, 0x70));
        imageMetadata.setOffsetLoOld(readInt(data, 0x88));
        imageMetadata.setOffsetHi(readInt(data, 0x90));
        imageMetadata.setOffsetHiOld(readInt(data, 0xA8));
        imageMetadata.setSizeLo(readInt(data, 0xF0));
        imageMetadata.setSizeLoOld(readInt(data, 0x108));
        imageMetadata.setSizeHi(readInt(data, 0x110));
        imageMetadata.setSizeHiOld(readInt(data, 0x128));
        return imageMetadata;
    }

    private static int readInt(byte[] data, int offset) {
        return ((data[offset + 0] << 0)  & 0xFF) +
               ((data[offset + 1] << 8)  & 0xFF00) +
               ((data[offset + 2] << 16) & 0xFF0000) +
               ((data[offset + 3] << 24) & 0xFF000000);
    }

    private static void writeInt(byte[] data, int offset, int value) {
        data[offset + 0] = (byte)(value >> 0);
        data[offset + 1] = (byte)(value >> 8);
        data[offset + 2] = (byte)(value >> 16);
        data[offset + 3] = (byte)(value >> 24);
    }

}
