package com.skoobalon.photoshop.util;

import com.skoobalon.photoshop.bean.*;
import com.skoobalon.photoshop.codec.DatafileCodec;
import com.skoobalon.photoshop.codec.HeaderfileCodec;
import com.skoobalon.photoshop.codec.IndexFileCodec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public final class ResourcePackUtil {

    private static final IndexFileCodec indexFileCodec = new IndexFileCodec();
    private static final DatafileCodec datafileCodec = new DatafileCodec();
    private static final HeaderfileCodec headerfileCodec = new HeaderfileCodec();

    public static ResourcePack read(String resourcePath) throws IOException {
        ResourcePack res = new ResourcePack();

        // Read the codec file
        res.setIndex(indexFileCodec.deserialize(resourcePath + File.separator + "IconResources.idx"));

        // Read the data/headerfiles
        res.setLoresDatafile(datafileCodec.deserialize(resourcePath + File.separator + res.getIndex().getLoResDatafile()));
        res.setHiresDatafile(datafileCodec.deserialize(resourcePath + File.separator + res.getIndex().getHiResDatafile()));
        res.setLoresHeaderfile(headerfileCodec.deserialize(resourcePath + File.separator + res.getIndex().getLoResHeaderfile()));
        res.setHiresHeaderfile(headerfileCodec.deserialize(resourcePath + File.separator + res.getIndex().getHiResHeaderfile()));

        return res;
    }

    public static void write(ResourcePack rp, String path) throws IOException {
        indexFileCodec.serializeToFile(rp.getIndex(), path + File.separator + "IconResources.idx");
        datafileCodec.serializeToFile(rp.getLoresDatafile(), path + File.separator + rp.getIndex().getLoResDatafile());
        datafileCodec.serializeToFile(rp.getHiresDatafile(), path + File.separator + rp.getIndex().getHiResDatafile());
        headerfileCodec.serializeToFile(rp.getLoresHeaderfile(), path + File.separator + rp.getIndex().getLoResHeaderfile());
        headerfileCodec.serializeToFile(rp.getHiresHeaderfile(), path + File.separator + rp.getIndex().getHiResHeaderfile());
    }

    public static void replaceLoResPngWithSmallerCopy(ResourcePack res, String pngName, PngData data) throws FileNotFoundException {
        // Verify a resource with this name exists in the codec
        final ImageMetadata metadata = res.getIndex().getImageMetadata().get(pngName);
        if (metadata == null) {
            throw new FileNotFoundException("No resource found with the name \"" + pngName + "\"");
        }

        // Verify the new data is smaller or equal in size
        if (data.getData().length > metadata.getSizeLo()) {
            throw new IllegalArgumentException("Input png data is larger than current png data in specified position");
        }

        // Slip the new data in
        res.getLoresDatafile().getPngs().get(metadata.getOffsetLo()).setData(data.getData());
        metadata.setSizeLo(data.getData().length);
    }

    /**
     * Currently this is not functioning correctly. Do not use it until bugs can be fixed. Use the "smaller" version above instead.
     * @param res
     * @param pngName
     * @param data
     * @throws FileNotFoundException
     */
    public static void replaceLoResPng(ResourcePack res, String pngName, PngData data) throws FileNotFoundException {
        // Verify a resource with this name exists in the codec
        final ImageMetadata metadata = res.getIndex().getImageMetadata().get(pngName);
        if (metadata == null) {
            throw new FileNotFoundException("No resource found with the name \"" + pngName + "\"");
        }

        // Swap the resource with the new one
        final Map<Integer, PngData> pngs = res.getLoresDatafile().getPngs();
        pngs.put(metadata.getOffsetLo(), data);
        int sizeDiff = data.getData().length - metadata.getSizeLo();
        metadata.setSizeLo(data.getData().length);

        // Rebase the subsequent elements in the datafile, moving forwards or backwards based on the size difference to prevent clobbering
        pngs.keySet().stream()
                .filter(offset -> offset > metadata.getOffsetLo())
                .sorted((a,b) -> sizeDiff > 0 ? b - a : a - b)
                .forEach(oldOffset -> {
                    pngs.put(oldOffset + sizeDiff, pngs.remove(oldOffset));
                    // Not at all efficient, but ehh
                    res.getIndex().getImageMetadata().entrySet().stream()
                            .filter(e -> e.getValue().getOffsetLo() == oldOffset)
                            .findAny()
                            .ifPresent(e -> e.getValue().setOffsetLo(oldOffset + sizeDiff));
                });
    }

    public static void replaceHiResPngWithSmallerCopy(ResourcePack res, String pngName, PngData data) throws FileNotFoundException {
        // Verify a resource with this name exists in the codec
        final ImageMetadata metadata = res.getIndex().getImageMetadata().get(pngName);
        if (metadata == null) {
            throw new FileNotFoundException("No resource found with the name \"" + pngName + "\"");
        }

        // Verify the new data is smaller or equal in size
        if (data.getData().length > metadata.getSizeHi()) {
            throw new IllegalArgumentException("Input png data is larger than current png data in specified position");
        }

        // Slip the new data in
        res.getHiresDatafile().getPngs().get(metadata.getOffsetHi()).setData(data.getData());
        metadata.setSizeHi(data.getData().length);
    }

    /**
     * Currently this is not functioning correctly. Do not use it until bugs can be fixed. Use the "smaller" version above instead.
     * @param res
     * @param pngName
     * @param data
     * @throws FileNotFoundException
     */
    public static void replaceHiResPng(ResourcePack res, String pngName, PngData data) throws FileNotFoundException {
        // Verify a resource with this name exists in the codec
        final ImageMetadata metadata = res.getIndex().getImageMetadata().get(pngName);
        if (metadata == null) {
            throw new FileNotFoundException("No resource found with the name \"" + pngName + "\"");
        }

        // Swap the resource with the new one
        final Map<Integer, PngData> pngs = res.getHiresDatafile().getPngs();
        pngs.put(metadata.getOffsetHi(), data);
        int sizeDiff = data.getData().length - metadata.getSizeHi();
        metadata.setSizeHi(data.getData().length);

        // Rebase the subsequent elements in the datafile, moving forwards or backwards based on the size difference to prevent clobbering
        pngs.keySet().stream()
                .filter(offset -> offset > metadata.getOffsetHi())
                .sorted((a,b) -> sizeDiff > 0 ? b - a : a - b)
                .forEach(oldOffset -> {
                    pngs.put(oldOffset + sizeDiff, pngs.remove(oldOffset));
                    // Not at all efficient, but ehh
                    res.getIndex().getImageMetadata().entrySet().stream()
                            .filter(e -> e.getValue().getOffsetHi() == oldOffset)
                            .findAny()
                            .ifPresent(e -> e.getValue().setOffsetHi(oldOffset + sizeDiff));
                });
    }

    /**
     * Checks if the index and datafiles are consistent with each other
     * @param res
     */
    public static void verifyConsistency(ResourcePack res) {
        final List<Integer> offsetsLo = res.getLoresDatafile().getPngs().keySet().stream().sorted().collect(Collectors.toList());
        final List<Integer> offsetsHi = res.getHiresDatafile().getPngs().keySet().stream().sorted().collect(Collectors.toList());
        final List<ImageMetadata> metaLo = res.getIndex().getImageMetadata().values().stream()
                .filter(x -> offsetsLo.contains(x.getOffsetLo()))
                .sorted(Comparator.comparingInt(ImageMetadata::getOffsetLo))
                .collect(Collectors.toList());
        final List<ImageMetadata> metaHi = res.getIndex().getImageMetadata().values().stream()
                .filter(x -> offsetsHi.contains(x.getOffsetHi()))
                .sorted(Comparator.comparingInt(ImageMetadata::getOffsetHi))
                .collect(Collectors.toList());

        checkState(offsetsLo.size() == metaLo.size());
        checkState(offsetsHi.size() == metaHi.size());

        for (int i = 0; i < offsetsLo.size() - 1; i++) {
            final ImageMetadata metaA = metaLo.get(i);
            final ImageMetadata metaB = metaLo.get(i + 1);
            final PngData png = res.getLoresDatafile().getPngs().get(offsetsLo.get(i));
            checkNotNull(metaA);
            checkNotNull(metaB);
            checkNotNull(png);
            checkState(metaA.getSizeLo() == png.getData().length);
            checkState(metaA.getOffsetLo() + metaA.getSizeLo() == metaB.getOffsetLo());
        }

        // TODO: HiRes too
        // need to refactor how the data is stored so that code doesn't need to be duplicated
    }

}
