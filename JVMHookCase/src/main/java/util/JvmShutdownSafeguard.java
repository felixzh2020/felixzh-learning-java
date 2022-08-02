package util;

/**
 * 一个用于防止JvmHook线程阻塞JVM导致不能退出的工具类：通过设置超时时间方式实现，超时后使用Jvm Runtime.getRuntime.halt()方法强制退出
 * <p>
 * 详见英文注释：
 * * A utility that guards against blocking shutdown hooks that block JVM shutdown.
 * *
 * * <p>When the JVM shuts down cleanly (<i>SIGTERM</i> or {@link System#exit(int)}) it runs all
 * * installed shutdown hooks. It is possible that any of the shutdown hooks blocks, which causes the
 * * JVM to get stuck and not exit at all.
 * *
 * * <p>This utility installs a shutdown hook that forcibly terminates the JVM if it is still alive a
 * * certain time after clean shutdown was initiated. Even if some shutdown hooks block, the JVM will
 * * terminate within a certain time.
 *
 * @author FelixZh
 */
public class JvmShutdownSafeguard extends Thread {
    //用于强制停止JVM的超时时间，超时强制执行
    private static final long DEFAULT_DELAY = 5000L;

    //通过该类强制停止JVM的退出码
    private static final int EXIT_CODE = -17;

    //用于强制停止JVM的线程
    private final Thread terminator;

    public JvmShutdownSafeguard(long delayMillis) {
        setName("JVM Terminator Launcher");

        this.terminator = new Thread(new DelayedTerminator(delayMillis), "JVM Terminator");
        this.terminator.setDaemon(true);
    }

    @Override
    public void run() {
        /**
         *  因为JvmShutdownSafeguard线程注册为JVM Shutdown hook，所以该线程不能等待的同时还要求线程终止。
         *  那样的话，JVM将一直等待而不退出。
         *  考虑上述因素：该线程run方法在启动一个daemon类型的线程，新启动的线程并不会组织JVM的退出。
         * */
        terminator.start();
    }

    //真正的用于执行Shutdown的线程
    private static class DelayedTerminator implements Runnable {

        private final long delayMillis;

        private DelayedTerminator(long delayMillis) {
            this.delayMillis = delayMillis;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(delayMillis);
                System.out.println(String.format("delayMillis %s timeout", delayMillis));
            } catch (Throwable t) {
                //catch all, including thread death, etc
            }

            //强制退出JVM
            Runtime.getRuntime().halt(EXIT_CODE);
            System.out.println("Force exit JVM.");
        }
    }
}
