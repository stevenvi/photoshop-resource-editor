package com.skoobalon.photoshop.bean;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the contents of the IconResources.idx file, which
 * is an codec of all the pngs in the resource files.
 */
public class IndexFile {
    private String loResDatafile = "";
    private String loResHeaderfile = "";
    private String hiResDatafile = "";
    private String hiResHeaderfile = "";

    // Maps the image name to the metadata associated with it
    private Map<String, ImageMetadata> imageMetadata = new HashMap<>();

    public IndexFile() {

    }

    // Copy ctor
    public IndexFile(IndexFile other) {
        this.loResDatafile = other.getLoResDatafile();
        this.loResHeaderfile = other.getLoResHeaderfile();
        this.hiResDatafile = other.getHiResDatafile();
        this.hiResHeaderfile = other.getHiResHeaderfile();
        this.imageMetadata = other.getImageMetadata().values().stream().collect(Collectors.toMap(ImageMetadata::getName, ImageMetadata::new));
    }

    public String getLoResDatafile() {
        return loResDatafile;
    }

    public void setLoResDatafile(String loResDatafile) {
        this.loResDatafile = loResDatafile == null ? "" : loResDatafile;
    }

    public String getLoResHeaderfile() {
        return loResHeaderfile;
    }

    public void setLoResHeaderfile(String loResHeaderfile) {
        this.loResHeaderfile = loResHeaderfile == null ? "" : loResHeaderfile;
    }

    public String getHiResDatafile() {
        return hiResDatafile;
    }

    public void setHiResDatafile(String hiResDatafile) {
        this.hiResDatafile = hiResDatafile == null ? "" : hiResDatafile;
    }

    public String getHiResHeaderfile() {
        return hiResHeaderfile;
    }

    public void setHiResHeaderfile(String hiResHeaderfile) {
        this.hiResHeaderfile = hiResHeaderfile == null ? "" : hiResHeaderfile;
    }

    public Map<String, ImageMetadata> getImageMetadata() {
        return imageMetadata;
    }

    public void setImageMetadata(Map<String, ImageMetadata> imageMetadata) {
        this.imageMetadata = imageMetadata == null ? new HashMap<>() : imageMetadata;
    }

    @Override
    public String toString() {
        return "IndexFile" +
                "{\n loResDatafile='" + loResDatafile + '\'' +
                ",\n loResHeaderfile='" + loResHeaderfile + '\'' +
                ",\n hiResDatafile='" + hiResDatafile + '\'' +
                ",\n hiResHeaderfile='" + hiResHeaderfile + '\'' +
                ",\n imageMetadata={\n\t" + imageMetadata.entrySet().stream()
                        .sorted(Comparator.comparing(a -> a.getValue().getName()))
                        .map(e -> e.getValue().toString())
                        .collect(Collectors.joining(",\n\t")) +
                "}\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexFile indexFile = (IndexFile) o;

        if (!loResDatafile.equals(indexFile.loResDatafile)) return false;
        if (!loResHeaderfile.equals(indexFile.loResHeaderfile)) return false;
        if (!hiResDatafile.equals(indexFile.hiResDatafile)) return false;
        if (!hiResHeaderfile.equals(indexFile.hiResHeaderfile)) return false;
        return imageMetadata.equals(indexFile.imageMetadata);
    }

    @Override
    public int hashCode() {
        int result = loResDatafile.hashCode();
        result = 31 * result + loResHeaderfile.hashCode();
        result = 31 * result + hiResDatafile.hashCode();
        result = 31 * result + hiResHeaderfile.hashCode();
        result = 31 * result + imageMetadata.hashCode();
        return result;
    }

}
