package com.djs.actuator;

import com.djs.actuator.filter.DocFilter;
import com.djs.actuator.filter.DocxFilter;
import com.djs.actuator.filter.PaperFilter;
import com.djs.actuator.handler.DocReplaceHandler;
import com.djs.actuator.handler.DocxReplaceHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReplaceExecutor {
    /**
     * 换行符
     */
    private static final String newLine = System.getProperty("line.separator");
    /**
     * 修改内容目标
     */
    private String target;
    /**
     * 替换内容
     */
    private String source;
    /**
     * 资源路径
     */
    private String path;
    /**
     * 错误文件
     */
    private List<String> errorFiles;
    private List<String> successFiles;
    public ReplaceExecutor(String path,String target,String source){
        this.target = target;
        this.source = source;
        this.path = path;
    }
    public void execute(){
        errorFiles = new ArrayList<>();
        successFiles = new ArrayList<>();
        recursion(path);
        //只有批量文件才需要记录，单个文件不需要记录
        if(isPaperFile(new File(path))) {
            //记录操作文件
            createInfoFile();
        }
    }

    /**
     * 递归遍历文件替换数据
     * @param path
     */
    public void recursion(String path){
        File file = new File(path);
        //判断是否是文件夹
        if(isPaperFile(file)){
            File[] docFiles = file.listFiles(new DocFilter());
            docReplace(docFiles);
            File[] docxFiles = file.listFiles(new DocxFilter());
            docxReplace(docxFiles);
            File[] paperFiles = file.listFiles(new PaperFilter());
            recursionFile(paperFiles);
            return;
        }
        if(file.getName().endsWith(".doc") && !file.getName().startsWith("~$")){
            docReplace(path);
            return;
        }
        if(file.getName().endsWith(".docx") && !file.getName().startsWith("~$")) {
            docxReplace(path);
        }
    }

    /**
     * 遍历文件夹
     * @param paperFiles
     */
    public void recursionFile(File[] paperFiles){
        if(Objects.isNull(paperFiles)){
            return;
        }
        for (File file : paperFiles){
            recursion(file.getPath());
        }
    }
    /**
     * 替换doc文件
     * @param docFiles
     */
    public void docReplace(File[] docFiles){
        if(Objects.isNull(docFiles)){
            return;
        }
        for(File docFile : docFiles) {
            docReplace(docFile.getPath());
        }
    }

    /**
     * 替换doc文件
     * @param docFile
     */
    public void docReplace(String docFile){
        try {
            DocReplaceHandler docReplaceHandler = new DocReplaceHandler(docFile, target, source);
            docReplaceHandler.execute();
            //记录成功文件
            successFiles.add(docFile);
        }catch (Exception e){
            //记录错误文件
            errorFiles.add(docFile);
        }
    }

    /**
     * 替换doc文件
     * @param docxFiles
     */
    public void docxReplace(File[] docxFiles){
        if(Objects.isNull(docxFiles)){
            return;
        }
        for(File docFile : docxFiles) {
            docxReplace(docFile.getPath());
        }
    }

    /**
     * 替换docx文件
     * @param docxFile
     */
    public void docxReplace(String docxFile){
        try {
            DocxReplaceHandler docxReplaceHandler = new DocxReplaceHandler(docxFile, target, source);
            docxReplaceHandler.execute();
            //记录成功文件
            successFiles.add(docxFile);
        }catch (Exception e){
            //记录错误文件
            errorFiles.add(docxFile);
        }
    }
    /**
     * 判断此file是否是目录 是返回true，反之false
     * @param file
     * @return
     */
    private boolean isPaperFile(File file){
        return file.isDirectory();
    }
    private void createInfoFile(){
        try {
            FileOutputStream fileInput = new FileOutputStream(path+"/"+generateInfoFileName());
            fileInput.write("失败文件：".getBytes());
            fileInput.write(newLine.getBytes());
            for (String info:errorFiles) {
                fileInput.write(info.getBytes());
                fileInput.write(newLine.getBytes());
            }
            fileInput.write("成功文件：".getBytes());
            fileInput.write(newLine.getBytes());
            for (String info:successFiles) {
                fileInput.write(info.getBytes());
                fileInput.write(newLine.getBytes());
            }
            fileInput.flush();
            fileInput.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 生成信息文件
     * @return
     */
    private String generateInfoFileName(){
       return System.currentTimeMillis()+".txt";
    }
}
