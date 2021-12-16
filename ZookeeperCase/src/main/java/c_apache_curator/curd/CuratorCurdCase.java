package c_apache_curator.curd;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author FelixZh
 * 注意：Curator与Zookeeper之间有版本兼容关系
 * <p>
 * 该类主要介绍 Curator 增删改查 基本操作
 **/
public class CuratorCurdCase {
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

        //返回实例状态:LATENT STARTED STOPPED
        System.out.println(cfClient.getState());

        /***
         *  CreateMode包括：PERSISTENT、PERSISTENT_SEQUENTIAL、EPHEMERAL、EPHEMERAL_SEQUENTIAL、CONTAINER、PERSISTENT_WITH_TTL、PERSISTENT_SEQUENTIAL_WITH_TTL
         **/
        //创建Persistence Node
        cfClient.create()
                .orSetData()/*** 如果node已存在，create将变为update **/
                .creatingParentsIfNeeded()/** 如果指定node的父node不存在，自动级联创建父node **/
                .withMode(CreateMode.PERSISTENT).forPath("/PersistentNode", "data".getBytes());
        //创建Persistence Sequential Node
        cfClient.create().orSetData().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/PersistentSequentialNode", "data".getBytes());
        //创建Ephemeral Node
        cfClient.create().orSetData().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/EphemeralNode", "data".getBytes());
        //创建Ephemeral Sequential Node
        cfClient.create().orSetData().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/EphemeralSequentialNode", "data".getBytes());

        //node是否存在：不存在则为null
        Stat stat = cfClient.checkExists().forPath("/PersistentNode");
        System.out.println(stat != null);

        //获取指定node所有children node
        cfClient.getChildren().forPath("/PersistentNode");

        //更新指定node数据：要求指定node必须存在。
        cfClient.setData().forPath("/PersistentNode", "newData".getBytes());

        //删除指定node：要求指定node必须存在
        cfClient.delete()
                .guaranteed()/** 服务端可能删除成功，client端没收到response，Curator后台持续尝试该操作 **/
                .deletingChildrenIfNeeded()/** 如果该node存在子node，自动级联删除子node**/
                .forPath("/PersistentNode");
        cfClient.close();
    }
}
