package c_apache_curator.service_discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.*;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ProviderService1 {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("felixzh:2181",
                2000, 2000, new ExponentialBackoffRetry(1000, 3));
        client.start();
        client.blockUntilConnected();

        //该服务中所有的接口
        Map<String, InstanceDetails.Service> services = new HashMap<>();
        // 添加订单服务接口
        InstanceDetails.Service addOrderService = new InstanceDetails.Service();
        addOrderService.setDesc("添加订单");
        addOrderService.setMethodName("addOrder");
        ArrayList<String> addOrderParams = new ArrayList<>();
        addOrderParams.add("createTime");
        addOrderParams.add("state");
        addOrderService.setParams(addOrderParams);
        services.put("addOrder", addOrderService);
        //添加删除订单服务接口
        InstanceDetails.Service delOrderService = new InstanceDetails.Service();
        delOrderService.setDesc("删除订单");
        delOrderService.setMethodName("delOrder");
        ArrayList<String> delOrderParams = new ArrayList<>();
        delOrderParams.add("orderId");
        delOrderService.setParams(delOrderParams);
        services.put("delOrder", delOrderService);

        //服务的其他信息
        InstanceDetails payload = new InstanceDetails();
        payload.setServiceDesc("订单服务");
        payload.setServices(services);

        //服务构造器
        ServiceInstanceBuilder<InstanceDetails> serviceInstanceBuilder = ServiceInstance.builder();
        //将订单服务添加到 ServiceInstance
        ServiceInstance<InstanceDetails> orderService = serviceInstanceBuilder.address("127.0.0.1")
                .port(8081)
                .name("OrderService")
                .payload(payload)
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();

        //构建 ServiceDiscovery 用来注册服务
        ServiceDiscovery<InstanceDetails> serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .serializer(new JsonInstanceSerializer<InstanceDetails>(InstanceDetails.class))
                .basePath(InstanceDetails.ROOT_PATH)
                .build();
        //服务注册
        serviceDiscovery.registerService(orderService);
        serviceDiscovery.start();

        System.out.println("第一台服务注册成功......");

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);

        serviceDiscovery.close();
        client.close();
    }
}
