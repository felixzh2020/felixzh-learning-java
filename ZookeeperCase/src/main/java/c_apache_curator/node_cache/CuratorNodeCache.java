package c_apache_curator.node_cache;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * 该类主要介绍 Curator Node Cache 使用方法  监听数据节点的变化
 */
public class CuratorNodeCache {
    //数据缓存
    public static String nodeCacheData = null;

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

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        //主线程需要等待线程池执行完成
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        //NodeCache：监听数据节点的变化
        final NodeCache nodeCache = new NodeCache(cfClient, "/zookeeper", false);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                String newData = new String(nodeCache.getCurrentData().getData());
                System.out.println("new Data: " + newData);
                //将新数据更新到数据缓存
                nodeCacheData = newData;
                countDownLatch.countDown();
            }
        }, executorService);
        //测试，重置/zookeeper节点数据
        cfClient.setData().forPath("/zookeeper", new Date().toString().getBytes());

        //模拟业务，从数据缓存中读取数据。测试的时候为了能读取到数据，用countDownLatch阻塞，真实业务场景不会阻塞。
        countDownLatch.await();
        System.out.println("getNodeCache : " + nodeCacheData);

        //释放资源
        nodeCache.close();
        cfClient.close();
        executorService.shutdown();
    }
}
