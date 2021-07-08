import java.util.ArrayList;
import java.util.List;

/**
 * @author FelixZh
 * <p>
 * 多线程场景下，非主线程oom,进程并不会退出！！
 * 设置堆大小： VM args: -Xms10m -Xmx10m
 */
public class OOMButDaemonNotQuit {
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            List<OOMButDaemonNotQuit> list = new ArrayList<>();
            while (true) {
                list.add(new OOMButDaemonNotQuit());
            }
        }).start();

        while (true) {
            System.out.println(Thread.currentThread().getName() + " continuing......");
            Thread.sleep(1000);
        }
    }
}
