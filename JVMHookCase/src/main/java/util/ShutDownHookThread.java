package util;

public class ShutDownHookThread extends Thread {

    private int delayMillis = 0;

    public ShutDownHookThread(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    @Override
    public void run() {
        System.out.println("==== exec util.ShutDownHookThread ====");

        if (delayMillis > 0) {
            //模拟Shutdown hook线程长时间运行，测试JvmShutdownSafeguard能否强制退出JVM
            try {
                Thread.sleep(10_000);
            } catch (Exception exception) {

            }
            System.out.println("==== exec util.ShutDownHookThread finish ====");
        }

    }
}