package com.djs;

import com.djs.actuator.ReplaceExecutor;
import org.apache.poi.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        new Fr();
    }
}
class Fr extends JFrame implements ActionListener{
    JTextField f1=new JTextField(10);
    JTextField f2=new JTextField(10);
    JLabel target=new JLabel("目标数据:");
    JLabel address=new JLabel("文件路径:");
    JTextField f3=new JTextField(10);
    JLabel source=new JLabel("替换为:");
//    JButton gen=new JButton("生成随机数");
    JButton confirm=new JButton("确定");
    JPanel p=new JPanel();
    JLabel tips=new JLabel();
    Fr(){
        this.setLayout(new FlowLayout());
        this.add(address);
        this.add(f1);
        this.add(target);
        this.add(f2);
        this.add(source);
        this.add(f3);
        this.add(p);
        p.add(confirm);
        this.add(tips);
        confirm.addActionListener(this);
        this.setSize(400,250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String path = f1.getText();
            String target = f2.getText();
            String source = f3.getText();
            if(StringUtil.isBlank(path)){
                tips.setText("文件路径不能为空");
                return;
            }
            if(StringUtil.isBlank(target)){
                tips.setText("目标数据不能为空");
                return;
            }
            if(StringUtil.isBlank(source)){
                tips.setText("替换为不能为空");
                return;
            }
            if(target.equals(source)){
                tips.setText("目标数据与替换为数据不能相同，除非你有病");
                return;
            }
            new ReplaceExecutor(path, target, source).execute();
            tips.setText("替换完成！");
        }catch (Exception exception){
            tips.setText("替换失败！请联系邓警司");
        }
    }
}

