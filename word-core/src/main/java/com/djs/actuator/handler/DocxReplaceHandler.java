package com.djs.actuator.handler;

import com.djs.actuator.AbstractBaseReplace;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class DocxReplaceHandler extends AbstractBaseReplace {

    public DocxReplaceHandler(String fileString, String target, String source) {
        super(fileString, target, source);
    }

    @Override
    public void execute() {
        InputStream is = null;
        try {
            is = new FileInputStream(getFileString());
            XWPFDocument doc = new XWPFDocument(is);
            //获取所有段落
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            paragraphsReplace(paragraphs);
            OutputStream os = new FileOutputStream(getFileString());
            //把doc输出到输出流
            doc.write(os);
            close(is);
            close(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void paragraphsReplace(List<XWPFParagraph> paragraphs){
        if(Objects.isNull(paragraphs)){
            return;
        }
        for (XWPFParagraph paragraph : paragraphs){
            paragraphReplace(paragraph);
        }
    }
    public void paragraphReplace(XWPFParagraph paragraph){
//        获取一个段落中的所有Runs：
        List<XWPFRun> xwpfRuns = paragraph.getRuns();
        runsReplace(xwpfRuns);
    }
    public void runsReplace(List<XWPFRun> xwpfRuns){
        //        获取一个Runs中的一个Run：
        if(Objects.isNull(xwpfRuns)){
            return;
        }
        for(XWPFRun xwpfRun : xwpfRuns){
            runReplace(xwpfRun);
        }
    }
    public void runReplace(XWPFRun xwpfRun){
        //        获取一个Runs中的一个Run：
        String text = xwpfRun.getText(0);
        if (Objects.isNull(text)){
            return;
        }
        text = replaceContext(text,target,source);
        xwpfRun.setText(text,0);
    }

    /**
     *
     * @param context 需要替换的内容
     * @param target 替换目标
     * @param source 替换资源
     */
    public String replaceContext(String context,String target,String source){
        return context.replace(target,source);
    }

    /**
     * 关闭输入流
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 关闭输出流
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
