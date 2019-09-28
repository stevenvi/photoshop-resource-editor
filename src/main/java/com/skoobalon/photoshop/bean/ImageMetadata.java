package com.skoobalon.photoshop.bean;

public class ImageMetadata {

    private String name = "";
    private int widthLo;
    private int widthHi;
    private int lengthLo;
    private int lengthHi;
    private int offsetLo;
    private int offsetLoOld;
    private int offsetHi;
    private int offsetHiOld;
    private int sizeLo;
    private int sizeLoOld;
    private int sizeHi;
    private int sizeHiOld;

    public ImageMetadata() {

    }

    // Copy ctor
    public ImageMetadata(ImageMetadata other) {
        this.name = other.getName();
        this.widthLo = other.getWidthLo();
        this.widthHi = other.getWidthHi();
        this.lengthLo = other.getLengthLo();
        this.lengthHi = other.getLengthHi();
        this.offsetLo = other.getOffsetLo();
        this.offsetLoOld = other.getOffsetLoOld();
        this.offsetHi = other.getOffsetHi();
        this.offsetHiOld = other.getOffsetHiOld();
        this.sizeLo = other.getSizeLo();
        this.sizeLoOld = other.getSizeLoOld();
        this.sizeHi = other.getSizeHi();
        this.sizeHiOld = other.getSizeHiOld();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public int getWidthLo() {
        return widthLo;
    }

    public void setWidthLo(int widthLo) {
        this.widthLo = widthLo;
    }

    public int getWidthHi() {
        return widthHi;
    }

    public void setWidthHi(int widthHi) {
        this.widthHi = widthHi;
    }

    public int getLengthLo() {
        return lengthLo;
    }

    public void setLengthLo(int lengthLo) {
        this.lengthLo = lengthLo;
    }

    public int getLengthHi() {
        return lengthHi;
    }

    public void setLengthHi(int lengthHi) {
        this.lengthHi = lengthHi;
    }

    public int getOffsetLo() {
        return offsetLo;
    }

    public void setOffsetLo(int offsetLo) {
        this.offsetLo = offsetLo;
    }

    public int getOffsetLoOld() {
        return offsetLoOld;
    }

    public void setOffsetLoOld(int offsetLoOld) {
        this.offsetLoOld = offsetLoOld;
    }

    public int getOffsetHi() {
        return offsetHi;
    }

    public void setOffsetHi(int offsetHi) {
        this.offsetHi = offsetHi;
    }

    public int getOffsetHiOld() {
        return offsetHiOld;
    }

    public void setOffsetHiOld(int offsetHiOld) {
        this.offsetHiOld = offsetHiOld;
    }

    public int getSizeLo() {
        return sizeLo;
    }

    public void setSizeLo(int sizeLo) {
        this.sizeLo = sizeLo;
    }

    public int getSizeLoOld() {
        return sizeLoOld;
    }

    public void setSizeLoOld(int sizeLoOld) {
        this.sizeLoOld = sizeLoOld;
    }

    public int getSizeHi() {
        return sizeHi;
    }

    public void setSizeHi(int sizeHi) {
        this.sizeHi = sizeHi;
    }

    public int getSizeHiOld() {
        return sizeHiOld;
    }

    public void setSizeHiOld(int sizeHiOld) {
        this.sizeHiOld = sizeHiOld;
    }

    @Override
    public String toString() {
        return "ImageMetadata{" +
                "name='" + name + '\'' +
                ", sizeLo=" + sizeLo +
                ", offsetLo=" + offsetLo +
                ", widthLo=" + widthLo +
                ", lengthLo=" + lengthLo +
                ", sizeHi=" + sizeHi +
                ", offsetHi=" + offsetHi +
                ", widthHi=" + widthHi +
                ", lengthHi=" + lengthHi +
                ", sizeLoOld=" + sizeLoOld +
                ", sizeHiOld=" + sizeHiOld +
                ", offsetLoOld=" + offsetLoOld +
                ", offsetHiOld=" + offsetHiOld +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageMetadata metadata = (ImageMetadata) o;

        if (widthLo != metadata.widthLo) return false;
        if (widthHi != metadata.widthHi) return false;
        if (lengthLo != metadata.lengthLo) return false;
        if (lengthHi != metadata.lengthHi) return false;
        if (offsetLo != metadata.offsetLo) return false;
        if (offsetLoOld != metadata.offsetLoOld) return false;
        if (offsetHi != metadata.offsetHi) return false;
        if (offsetHiOld != metadata.offsetHiOld) return false;
        if (sizeLo != metadata.sizeLo) return false;
        if (sizeLoOld != metadata.sizeLoOld) return false;
        if (sizeHi != metadata.sizeHi) return false;
        if (sizeHiOld != metadata.sizeHiOld) return false;
        return name.equals(metadata.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + widthLo;
        result = 31 * result + widthHi;
        result = 31 * result + lengthLo;
        result = 31 * result + lengthHi;
        result = 31 * result + offsetLo;
        result = 31 * result + offsetLoOld;
        result = 31 * result + offsetHi;
        result = 31 * result + offsetHiOld;
        result = 31 * result + sizeLo;
        result = 31 * result + sizeLoOld;
        result = 31 * result + sizeHi;
        result = 31 * result + sizeHiOld;
        return result;
    }
}
