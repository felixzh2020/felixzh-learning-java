/**
 * 泛型通配符
 */
public class GenericWildcard {
    public static void showKeyValue(GenericClass<Number> obj) {
        System.out.println("showKeyValue: " + obj.getKey());
    }

    public static void main(String[] args) {
        GenericClass<Integer> gInteger = new GenericClass<>(123);
        GenericClass<Number> gNumber = new GenericClass<>(456);

        showKeyValue(gNumber);

        // 报错：Generic<java.lang.Integer> cannot be applied to Generic<java.lang.Number>
        //showKeyValue(gInteger);
        //通过提示信息我们可以看到Generic<Integer>不能被看作为`Generic<Number>的子类。
        // 由此可以看出:同一种泛型可以对应多个版本（因为参数类型是不确定的），不同版本的泛型类实例是不兼容的。
        //回到上面的例子，如何解决上面的问题？总不能为了定义一个新的方法来处理Generic<Integer>类型的类，
        // 这显然与java中的多台理念相违背。因此我们需要一个在逻辑上可以表示同时是Generic<Integer>和Generic<Number>父类的引用类型。
        // 由此类型通配符应运而生。我们可以将showKeyValue改一下,如showKeyValue1：

        showKeyValue1(gNumber);
        showKeyValue1(gInteger);
        showKeyValue1(new GenericClass<>(8.8));
        showKeyValue1(new GenericClass<>("字符串"));
    }

    public static void showKeyValue1(GenericClass<?> obj) {
        System.out.println("showKeyValue1: " + obj.getKey());
    }
}
