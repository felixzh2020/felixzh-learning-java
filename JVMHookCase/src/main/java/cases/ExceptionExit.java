package cases;

import util.ShutDownHookThread;

/**
 * 模拟程序异常退出(如:逻辑错误、连接失败等等)的场景
 * 该场景可以触发Jvm Hook
 *
 * @author FelixZh
 */
public class ExceptionExit {
    public static void main(String[] args) {
        //添加钩子
        Runtime.getRuntime().addShutdownHook(new ShutDownHookThread(0));
        //当然如果有场景需要也可以移除ShutdownHook
        //Runtime.getRuntime().removeShutdownHook(new util.ShutDownHookThread());

        //业务工作
        work();

        //异常退出
        throw new RuntimeException("exception");
    }

    public static void work() {
        System.out.println("==== exec work ====");
    }
}
