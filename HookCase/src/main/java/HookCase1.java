public class HookCase1 {
    public static void main(String[] args) throws Exception {
        Thread shutDownThread = new Thread(() -> {
            System.out.println("HookCase1 start ...");
            System.out.println("HookCase1 stop ... ");
        });
        Runtime.getRuntime().addShutdownHook(shutDownThread);

        boolean flag = true;
        while (flag) {
            System.out.println("main thread is running ...");
            Thread.sleep(300_000);
            flag = false;
        }
    }
}
