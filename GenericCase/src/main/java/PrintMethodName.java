import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintMethodName {
    private static final Logger logger = LoggerFactory.getLogger(PrintMethodName.class);

    public static void main(String[] args) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Current method is {}", methodName);
    }
}
