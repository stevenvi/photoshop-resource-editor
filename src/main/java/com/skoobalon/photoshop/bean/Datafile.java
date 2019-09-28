package com.skoobalon.photoshop.bean;

import java.util.*;
import java.util.stream.Collectors;

public class Datafile {
    private byte[] header = new byte[4];

    // Maps the datafile offset in bytes from the start position to the png data at that location
    private Map<Integer, PngData> pngs = new HashMap<>();

    public Datafile() {

    }

    public Datafile(Datafile other) {
        this.header = Arrays.copyOf(other.getHeader(), other.getHeader().length);
        this.pngs = other.getPngs().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new PngData(e.getValue())));
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header == null ? new byte[4] : header;
    }

    public Map<Integer, PngData> getPngs() {
        return pngs;
    }

    public void setPngs(Map<Integer, PngData> pngs) {
        this.pngs = pngs == null ? new HashMap<>() : pngs;
    }

    @Override
    public String toString() {
        return "Datafile" +
                "{\nheader=" + Arrays.toString(header) +
                ",\n pngs={\n\t" + pngs.entrySet().stream()
                        .sorted(Comparator.comparingInt(Map.Entry::getKey))
                        .map(e -> e.getKey() + " -> " + e.getValue())
                        .collect(Collectors.joining(",\n\t")) +
                "}\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Datafile datafile = (Datafile) o;

        if (!Arrays.equals(header, datafile.header)) return false;
        return pngs.equals(datafile.pngs);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(header);
        result = 31 * result + pngs.hashCode();
        return result;
    }
}
