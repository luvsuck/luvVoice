import org.junit.Test;
import util.FileTransJavaDemo;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;

public class Test1 {
    @Test
    public void codec() {
        Encoder encoder = new Encoder();
        try {
            for (int i = 0; i < encoder.getAudioEncoders().length; i++) {
                System.out.println(encoder.getAudioEncoders()[i].toString());
            }
        } catch (EncoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void generate() {
        try {
            FileTransJavaDemo.main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
