import java.util.ArrayList;
import java.util.List;

/**
 * @author felixzh
 * */
public class OOMTest {
    public static void main(String[] args) {
        int size = 1024 * 1024;
        List<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0; i < 1024; i++) {
            System.out.println("JVM写入数据" + (i + 1) + "M");
            list.add(new byte[size]);

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
