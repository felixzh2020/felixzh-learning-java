package c_apache_curator.transaction;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author FelixZh
 * 注意：Curator与Zookeeper之间有版本兼容关系
 * <p>
 * 该类主要介绍 Curator 事务管理 操作
 **/
public class CuratorTransactionCache {
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

        //定义基本操作：create、set、delete、check
        CuratorOp createOp = cfClient.transactionOp().create().forPath("/test", "data".getBytes());
        CuratorOp setDataOp = cfClient.transactionOp().setData().forPath("/test", "data1".getBytes());
        CuratorOp deleteOp = cfClient.transactionOp().delete().forPath("/test");
        //人为制造异常，观察事务回滚
        //CuratorOp deleteOp = cfClient.transactionOp().delete().forPath("/test1");

        //将上述基本操作封装程一个事务
        List<CuratorTransactionResult> results = cfClient.transaction().forOperations(createOp, setDataOp, deleteOp);

        //遍历事务输出结果
        for (CuratorTransactionResult result : results) {
            System.out.println(result.getForPath() + ":" + result.getType());
        }
        cfClient.close();
    }
}
