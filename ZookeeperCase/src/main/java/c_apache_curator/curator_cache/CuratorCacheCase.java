package c_apache_curator.curator_cache;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author FelixZh
 * 注意：Curator与Zookeeper之间有版本兼容关系
 * Curator提供如下Watcher(cache)来监听node变化：
 * Curator Cache - A utility that attempts to keep the data from a node locally cached. Optionally the entire tree of children below the node can also be cached. Will respond to update/create/delete events, pull down the data, etc. You can register listeners that will get notified when changes occur.
 * Path Cache - (For preZooKeeper 3.6.x) A Path Cache is used to watch a ZNode. Whenever a child is added, updated or removed, the Path Cache will change its state to contain the current set of children, the children's data and the children's state. Path caches in the Curator Framework are provided by the PathChildrenCache class. Changes to the path are passed to registered PathChildrenCacheListener instances.
 * Node Cache - (For preZooKeeper 3.6.x) A utility that attempts to keep the data from a node locally cached. This class will watch the node, respond to update/create/delete events, pull down the data, etc. You can register a listener that will get notified when changes occur.
 * Tree Cache - (For preZooKeeper 3.6.x) A utility that attempts to keep all data from all children of a ZK path locally cached. This class will watch the ZK path, respond to update/create/delete events, pull down the data, etc. You can register a listener that will get notified when changes occur.
 * 上述介绍就是说：3.6.x之后，Path Cache、Node Cache、Tree Cache均标记为废弃，建议使用Curator Cache。
 * <p>
 * 该类主要介绍 Curator Cache 使用方法。 可以支持Node Cache需要显示设置withOptions(CuratorCache.Options.SINGLE_NODE_CACHE)、Tree Cache(默认支持)
 */
public class CuratorCacheCase {
    public static String NodeCache = null;

    public static void main(String[] args) throws Exception {
        //连接超时
        int CONNECTION_TIMEOUT_MS = 15_000;
        //会话超时
        int SESSION_TIMEOUT_MS = 60_000;
        //Zookeeper IP:PORT
        String CONNECT_STRING = "felixzh:2181";
        //重试次数
        int MAX_RETRIES = 3;
        //重试时间间隔
        int BASE_SLEEP_TIME_MS = 1000;
        //重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRIES);
        //通过工厂创建Zookeeper framework-style client
        CuratorFramework cfClient = CuratorFrameworkFactory.builder()
                .connectionTimeoutMs(CONNECTION_TIMEOUT_MS)
                .sessionTimeoutMs(SESSION_TIMEOUT_MS)
                .connectString(CONNECT_STRING)
                .retryPolicy(retryPolicy)
                .build();
        //Start the client. Most mutator methods will not work until the client is started
        cfClient.start();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        //主线程需要等待线程池执行完成
        final CountDownLatch countDownLatch = new CountDownLatch(500);

        CuratorCache curatorCache = CuratorCache.builder(cfClient, "/")
                /*.withOptions(CuratorCache.Options.SINGLE_NODE_CACHE)*/
                /*.withOptions(CuratorCache.Options.COMPRESSED_DATA)*/
                /*.withOptions(CuratorCache.Options.DO_NOT_CLEAR_ON_CLOSE)*/
                .build();

        curatorCache.listenable().addListener(new CuratorCacheListener() {
            @Override
            public void event(Type type, ChildData oldData, ChildData data) {
                switch (type.name()) {
                    case "NODE_CREATED":
                        System.out.println("NODE_CREATED: " + oldData + " : " + data);
                        break;
                    case "NODE_CHANGED":
                        System.out.println("NODE_CHANGED: " + oldData + " : " + data);
                        break;
                    case "NODE_DELETED":
                        System.out.println("NODE_DELETED: " + oldData + " : " + data);
                        break;
                    default:
                        break;
                }

                System.out.println("currentData: " + countDownLatch.getCount());
                countDownLatch.countDown();
            }
        }, executorService);

        curatorCache.start();

        //模拟业务，从数据缓存中读取数据。测试的时候为了能读取到数据，用countDownLatch阻塞，真实业务场景不会阻塞。
        countDownLatch.await();

        //释放资源
        curatorCache.close();
        executorService.shutdown();
        cfClient.close();
    }
}
