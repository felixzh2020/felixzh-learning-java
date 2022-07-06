import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * JDK原生HttpServer
 *
 * @author FelixZh
 */
public class JDKHttpServer {
    public static void main(String[] args) throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName("localhost"), 8080);
        HttpServer httpServer = HttpServer.create(inetSocketAddress, 0);

        //监听上下文.指定匹配url
        httpServer.createContext("/", new MyHttpHandler());
        httpServer.createContext("/hello", new MyHttpHandler());

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        httpServer.setExecutor(executorService);

        //启动服务
        httpServer.start();
        System.out.println("Start...");
    }
}

class MyHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println(httpExchange.getRemoteAddress());
        System.out.println(httpExchange.getProtocol());
        System.out.println(httpExchange.getRequestMethod());
        System.out.println(httpExchange.getRequestURI());

        byte[] resp = "FelixZh".getBytes(StandardCharsets.UTF_8);
        //设置响应头
        httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        //设置响应code和内容长度
        httpExchange.sendResponseHeaders(200, resp.length);
        //设置响应内容
        httpExchange.getResponseBody().write(resp);

        //关闭处理器
        httpExchange.close();
    }
}
