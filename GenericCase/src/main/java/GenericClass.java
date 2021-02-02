/**
 * 泛型类
 * T为任意标识，如T、E、K、V等
 */

public class GenericClass<T> {
    private T key;

    public GenericClass(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public static void main(String[] args) {
        System.out.println("泛型类测试");
        GenericClass<String> genericClassString = new GenericClass<>("string");
        System.out.println(genericClassString.getKey());
        GenericClass<Integer> genericClassInteger = new GenericClass<>(9999);
        System.out.println(genericClassInteger.getKey());

        //定义的泛型类，就一定要传入泛型类型实参么？
        // 并不是这样，在使用泛型的时候如果传入泛型实参，则会根据传入的泛型实参做相应的限制，此时泛型才会起到本应起到的限制作用。
        // 如果不传入泛型类型实参的话，在泛型类中使用泛型的方法或成员变量定义的类型可以为任何的类型。
        System.out.println("------------------------");
        System.out.println(new GenericClass<>(789).getKey());
        System.out.println(new GenericClass<>("ssss").getKey());
        System.out.println(new GenericClass<>(true).getKey());
        System.out.println(new GenericClass<>(3.33).getKey());
        System.out.println(new GenericClass<>(3L).getKey());
    }
}