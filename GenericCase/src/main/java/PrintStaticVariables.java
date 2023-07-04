import java.lang.reflect.Field;

/**
 * 通用方法打印类的所有静态变量
 *
 * @author FelixZh
 */
public class PrintStaticVariables {
    private static final int staticVariable1 = 10;
    private static final String staticVariable2 = "Hello FelixZh";
    private static final boolean staticVariable3 = true;

    public static void main(String[] args) {
        printStaticVariables(PrintStaticVariables.class);
    }

    private static void printStaticVariables(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                try {
                    field.setAccessible(true);
                    System.out.println(String.format("%s=%s", field.getName(), field.get(null)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
