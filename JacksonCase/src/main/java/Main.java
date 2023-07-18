import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author FelixZh
 * <p>
 * Jackson示例
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        byte[] jsonData = "{\"name\":\"FelixZh\", \"age\":30}".getBytes();
        Map<String, Object> map = objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
        });

        System.out.println(map);
    }
}
