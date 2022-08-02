package cases;

import util.ShutDownHookThread;

/**
 * 模拟程序强制退出(如:kill -9)的场景
 * 该场景并不能触发Jvm Hook
 *
 * @author FelixZh
 */

public class ForceExit {
    public static void main(String[] args) throws Exception {
        //添加钩子
        Runtime.getRuntime().addShutdownHook(new ShutDownHookThread(0));
        //当然如果有场景需要也可以移除ShutdownHook
        //Runtime.getRuntime().removeShutdownHook(new util.ShutDownHookThread());

        //业务工作
        work();

        //模拟常驻
        Thread.sleep(600_000);

    }

    public static void work() {
        System.out.println("==== exec work ====");
    }
}
