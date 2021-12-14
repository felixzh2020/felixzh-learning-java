package c_apache_curator.service_discovery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceDetails {

    public static final String ROOT_PATH = "/service";

    /**
     * 该服务拥有哪些方法
     */
    public Map<String, Service> services = new HashMap<>();

    /**
     * 服务描述
     */
    private String serviceDesc;

    public InstanceDetails() {
        this.serviceDesc = "";
    }

    public InstanceDetails(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public Map<String, Service> getServices() {
        return services;
    }

    public void setServices(Map<String, Service> services) {
        this.services = services;
    }

    public static class Service {
        /**
         * 方法名称
         */
        private String methodName;

        /**
         * 方法描述
         */
        private String desc;

        /**
         * 方法所需要的参数列表
         */
        private List<String> params;

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<String> getParams() {
            return params;
        }

        public void setParams(List<String> params) {
            this.params = params;
        }
    }
}
