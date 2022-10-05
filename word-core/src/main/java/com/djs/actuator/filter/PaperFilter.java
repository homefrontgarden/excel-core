package com.djs.actuator.filter;

import java.io.File;
import java.io.FilenameFilter;

public class PaperFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        return new File(dir,name).isDirectory();
    }
}
