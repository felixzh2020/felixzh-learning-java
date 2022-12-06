package com.felixzh.OperatingSystem;

import java.util.*;

public class HashTest {


    // 真实节点列表
    private static List<Machine> realNodes = new ArrayList<Machine>();

    // 虚拟节点，key是Hash值，value是虚拟节点信息
    private static SortedMap<Integer, String> shards = new TreeMap<Integer, String>();

    static {
        realNodes.add(new Machine("192.168.1.1", LoadFactor.Memory8G));
        realNodes.add(new Machine("192.168.1.2", LoadFactor.Memory16G));
        realNodes.add(new Machine("192.168.1.3", LoadFactor.Memory32G));
        realNodes.add(new Machine("192.168.1.4", LoadFactor.Memory16G));
        for (Machine node : realNodes) {
            for (int i = 0; i < node.getMemory().getVrNum(); i++) {
                String server = node.getHost();
                String virtualNode = server + "&&VN" + i;
                int hash = getHash(virtualNode);
                shards.put(hash, virtualNode);
            }
        }
    }

    /**
     * 获取被分配的节点名
     *
     * @param node
     * @return
     */
    public static Machine getServer(String node) {
        int hash = getHash(node);
        Integer key = null;
        SortedMap<Integer, String> subMap = shards.tailMap(hash);
        if (subMap.isEmpty()) {
            key = shards.lastKey();
        } else {
            key = subMap.firstKey();
        }
        String virtualNode = shards.get(key);
        String realNodeName = virtualNode.substring(0, virtualNode.indexOf("&&"));
        for (Machine machine : realNodes) {
            if (machine.getHost().equals(realNodeName)) {
                return machine;
            }
        }
        return null;
    }

    /**
     * 添加节点
     *
     * @param node
     */
    public static void addNode(Machine node) {
        if (!realNodes.contains(node)) {
            realNodes.add(node);
            System.out.println("真实节点[" + node + "] 上线添加");
            for (int i = 0; i < node.getMemory().getVrNum(); i++) {
                String virtualNode = node.getHost() + "&&VN" + i;
                int hash = getHash(virtualNode);
                shards.put(hash, virtualNode);
                System.out.println("虚拟节点[" + virtualNode + "] hash:" + hash + "，被添加");
            }
        }
    }

    /**
     * 删除节点
     *
     * @param node
     */
    public static void delNode(Machine node) {
        String host = node.getHost();
        Iterator<Machine> it = realNodes.iterator();
        while (it.hasNext()) {
            Machine machine = it.next();
            if (machine.getHost().equals(host)) {
                it.remove();
                System.out.println("真实节点[" + node + "] 下线移除");
                for (int i = 0; i < node.getMemory().getVrNum(); i++) {
                    String virtualNode = node.getHost() + "&&VN" + i;
                    int hash = getHash(virtualNode);
                    shards.remove(hash);
                    System.out.println("虚拟节点[" + virtualNode + "] hash:" + hash + "，被移除");
                }
            }
        }
    }

    /**
     * FNV1_32_HASH算法
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    public static void main(String[] args) {

        // 模拟客户端的请求
        String[] nodes = {"127.0.0.1", "10.9.3.253", "192.168.10.1"};

        for (String node : nodes) {
            System.out.println("[" + node + "]的hash值为" + getHash(node) + ", 被路由到结点[" + getServer(node) + "]");
        }

        // 添加一个节点(模拟服务器上线)
        addNode(new Machine("192.168.1.7", LoadFactor.Memory16G));
        addNode(new Machine("192.168.1.9", LoadFactor.Memory16G));
        // 删除一个节点（模拟服务器下线）
        delNode(new Machine("192.168.1.1", LoadFactor.Memory8G));

        for (String node : nodes) {
            System.out.println("[" + node + "]的hash值为" + getHash(node) + ", 被路由到结点[" + getServer(node) + "]");
        }
    }
}

/**
 * 机器类
 *
 * @author yangkuanjun
 */
class Machine {

    private String host;

    private LoadFactor memory;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public LoadFactor getMemory() {
        return memory;
    }

    public void setMemory(LoadFactor memory) {
        this.memory = memory;
    }

    public Machine(String host, LoadFactor memory) {
        super();
        this.host = host;
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Machine [host=" + host + ", memory=" + memory + "]";
    }
}

/**
 * 负载因子
 *
 * @author yangkuanjun
 */
enum LoadFactor {

    Memory8G(5), Memory16G(10), Memory32G(20);

    private int vrNum;

    private LoadFactor(int vrNum) {
        this.vrNum = vrNum;
    }

    public int getVrNum() {
        return vrNum;
    }

}
