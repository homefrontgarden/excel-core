package com.djs.actuator.filter;

import java.io.File;
import java.io.FilenameFilter;

public class DocxFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        System.out.println("位置:"+dir.getName());
        System.out.println("文件名:"+name);
        return name.endsWith(".docx") && !name.startsWith("~$");
    }
}