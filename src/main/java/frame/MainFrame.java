package frame;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import ext.InfiniteProgressPanel;
import util.AliOssUtil;
import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainFrame {
    public static final InfiniteProgressPanel glasspane = new InfiniteProgressPanel();
    public static final JFrame jf = new JFrame("转换工具");

    public static void main(String[] args) {
        JLabel label = new JLabel("选择源文件：");
        JTextField jtf = new JTextField(25);
        JButton button = new JButton("浏览");
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(jtf);
        panel.add(button);
        jf.add(panel);
        jf.pack();    //自动调整大小
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener((k) -> {
            JFileChooser fc = new JFileChooser("C:\\Users\\zhouyuyang\\Desktop");
            int val = fc.showOpenDialog(null);    //文件打开对话框
            if (val == JFileChooser.APPROVE_OPTION) {
                jtf.setText(fc.getSelectedFile().toString());
                String exportPath = new File("src/main/java/output").getAbsolutePath();
                File file = fc.getSelectedFile();
                String filePath = file.getPath();
                String exportFilePath = exportPath + "\\" + file.getName() + ".mp3";
                m4aToMp3(filePath, exportFilePath);
                OSS oss = AliOssUtil.ossClient;
                boolean found = oss.doesObjectExist(AliOssUtil.bucketName, file.getName() + ".mp3");
                if (found)
                    JOptionPane.showMessageDialog(panel, file.getName() + ".mp3已存在！请重新选择", "警告", JOptionPane.WARNING_MESSAGE);
                else {
                    try {
                        oss.putObject(AliOssUtil.bucketName, file.getName() + ".mp3", new File(exportFilePath));
//                        oss.setObjectAcl(AliOssUtil.bucketName, file.getName() + ".mp3", CannedAccessControlList.PublicReadWrite);
                    } catch (OSSException oe) {
                        JOptionPane.showMessageDialog(panel, "文件上传至oss时出现OSS异常!"+oe.getErrorMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Caught an OSSException, which means your request made it to OSS, "
                                + "but was rejected with an error response for some reason.");
                        System.out.println("Error Message: " + oe.getErrorMessage());
                        System.out.println("Error Code:       " + oe.getErrorCode());
                        System.out.println("Request ID:      " + oe.getRequestId());
                        System.out.println("Host ID:           " + oe.getHostId());
                    } catch (ClientException ce) {
                        JOptionPane.showMessageDialog(panel, "文件上传至oss时出现client异常!"+ce.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Caught an ClientException, which means the client encountered "
                                + "a serious internal problem while trying to communicate with OSS, "
                                + "such as not being able to access the network.");
                        System.out.println("Error Message: " + ce.getMessage());
                    }
//                    finally {
//                        if (oss != null) {
//                            oss.shutdown();
//                        }
//                    }
                }
            } else {
                //未正常选择文件，如选择取消按钮
                jtf.setText("未选择文件");
            }
        });
    }

    public static void m4aToMp3(String audioPath, String mp3Path) {
        File source = new File(audioPath);
        File target = new File(mp3Path);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setSamplingRate(16000);
//        audio.setBitRate(new Integer(128000));
//        audio.setChannels(new Integer(2));
//        audio.setSamplingRate(new Integer(44100));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        try {
            encoder.encode(new MultimediaObject(source), target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }
}

