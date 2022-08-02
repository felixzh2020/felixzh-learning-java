package cases;

import util.JvmShutdownSafeguard;
import util.ShutDownHookThread;

/**
 * 模拟程序正常退出(所有逻辑执行完成)的场景
 * 该场景可以触发Jvm Hook
 *
 * @author FelixZh
 */
public class NormalExit {
    public static void main(String[] args) {
        //添加钩子
        Runtime.getRuntime().addShutdownHook(new ShutDownHookThread(0));
        //Runtime.getRuntime().addShutdownHook(new ShutDownHookThread(10));
        //当然如果有场景需要也可以移除ShutdownHook
        //Runtime.getRuntime().removeShutdownHook(new util.ShutDownHookThread());

        //增加超时保护
        Runtime.getRuntime().addShutdownHook(new JvmShutdownSafeguard(5));

        //业务工作
        work();

        //正常退出
        System.out.println("==== normal exit ====");
    }

    public static void work() {
        System.out.println("==== exec work ====");
    }
}


