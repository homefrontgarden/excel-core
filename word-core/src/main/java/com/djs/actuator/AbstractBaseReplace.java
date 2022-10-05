package com.djs.actuator;

import java.io.IOException;

/**
 * 抽象基本替换类
 */
public abstract class AbstractBaseReplace {
    /**
     * 文件地址
     */
    public String fileString;
    /**
     * 目标数据
     */
    public String target;
    /**
     * 资源数据
     */
    public String source;

    public AbstractBaseReplace() {
    }

    public AbstractBaseReplace(String fileString, String target, String source) {
        this.fileString = fileString;
        this.target = target;
        this.source = source;
    }

    public abstract void execute() throws IOException;
//    public boolean

    public String getFileString() {
        return fileString;
    }

    public void setFileString(String fileString) {
        this.fileString = fileString;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
