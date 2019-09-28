package com.skoobalon.photoshop.cli;

import com.skoobalon.photoshop.bean.IndexFile;
import com.skoobalon.photoshop.codec.IndexFileCodec;

import java.io.File;

public class IndexReader extends AbstractCli {

    public static void main(String[] args) throws Exception {
        final String psPath = promptForPhotoshopPath();
        final IndexFile indexFile = new IndexFileCodec().deserialize(psPath + File.separator + RESOURCE_DIR + File.separator + INDEX_FILE);
        System.out.println(indexFile.toString());
    }
}
