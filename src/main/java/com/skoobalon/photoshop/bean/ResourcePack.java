package com.skoobalon.photoshop.bean;

/**
 * Represents the entire resource package for an installation of Adobe Photoshop CC
 */
public class ResourcePack {

    private IndexFile index;
    private Datafile loresDatafile;
    private Datafile hiresDatafile;
    private Headerfile loresHeaderfile;
    private Headerfile hiresHeaderfile;

    public ResourcePack() {

    }

    public ResourcePack(ResourcePack other) {
        this.index = new IndexFile(other.getIndex());
        this.loresDatafile = new Datafile(other.getLoresDatafile());
        this.hiresDatafile = new Datafile(other.getHiresDatafile());
        this.loresHeaderfile = new Headerfile(other.getLoresHeaderfile());
        this.hiresHeaderfile = new Headerfile(other.getHiresHeaderfile());
    }

    public IndexFile getIndex() {
        return index;
    }

    public void setIndex(IndexFile index) {
        this.index = index;
    }

    public Datafile getLoresDatafile() {
        return loresDatafile;
    }

    public void setLoresDatafile(Datafile loresDatafile) {
        this.loresDatafile = loresDatafile;
    }

    public Datafile getHiresDatafile() {
        return hiresDatafile;
    }

    public void setHiresDatafile(Datafile hiresDatafile) {
        this.hiresDatafile = hiresDatafile;
    }

    public Headerfile getLoresHeaderfile() {
        return loresHeaderfile;
    }

    public void setLoresHeaderfile(Headerfile loresHeaderfile) {
        this.loresHeaderfile = loresHeaderfile;
    }

    public Headerfile getHiresHeaderfile() {
        return hiresHeaderfile;
    }

    public void setHiresHeaderfile(Headerfile hiresHeaderfile) {
        this.hiresHeaderfile = hiresHeaderfile;
    }

    @Override
    public String toString() {
        return "ResourcePack" +
                "{\n index=" + index +
                ",\n loresDatafile=" + loresDatafile +
                ",\n hiresDatafile=" + hiresDatafile +
                ",\n loresHeaderfile=" + loresHeaderfile +
                ",\n hiresHeaderfile=" + hiresHeaderfile +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourcePack that = (ResourcePack) o;

        if (!index.equals(that.index)) return false;
        if (!loresDatafile.equals(that.loresDatafile)) return false;
        if (!hiresDatafile.equals(that.hiresDatafile)) return false;
        if (!loresHeaderfile.equals(that.loresHeaderfile)) return false;
        return hiresHeaderfile.equals(that.hiresHeaderfile);
    }

    @Override
    public int hashCode() {
        int result = index.hashCode();
        result = 31 * result + loresDatafile.hashCode();
        result = 31 * result + hiresDatafile.hashCode();
        result = 31 * result + loresHeaderfile.hashCode();
        result = 31 * result + hiresHeaderfile.hashCode();
        return result;
    }
}
