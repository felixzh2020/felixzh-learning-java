package a_sell_tickets;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

public class ExampleClientThatLocks {

    /** 锁 */
    private final InterProcessMutex lock;
    /** 共享资源 */
    private final FakeLimitedResource resource;
    /** 客户端名称 */
    private final String clientName;

    public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName) {
        this.resource = resource;
        this.clientName = clientName;
        lock = new InterProcessMutex(client, lockPath);
    }

    public void doWork(long time, TimeUnit unit) throws Exception {
        if ( !lock.acquire(time, unit) ) {
            throw new IllegalStateException(clientName + " could not acquire the lock");
        }
        try {
            System.out.println(clientName + " has the lock");
            //操作资源
            resource.use();
        } finally {
            System.out.println(clientName + " releasing the lock");
            lock.release(); //总是在Final块中释放锁。
        }
    }
}
