package com.djs.actuator.handler;

import com.djs.actuator.AbstractBaseReplace;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.*;

public class DocReplaceHandler extends AbstractBaseReplace {
    public DocReplaceHandler() {
    }

    public DocReplaceHandler(String fileString, String target, String source) {
        super(fileString, target, source);
    }

    @Override
    public void execute(){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(getFileString());
            HWPFDocument hwpfDocument = new HWPFDocument(inputStream);
            Range range = hwpfDocument.getRange();
            for(int i=0;i<range.numParagraphs();i++){
                Paragraph paragraph = range.getParagraph(i);//段落
                paragraph.replaceText(getTarget(),getSource());
            }
            File file1 = new File(getFileString());
            OutputStream out = new FileOutputStream(file1);
            hwpfDocument.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
