/**
 * 未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一起加到类中
 * 即：class FruitGenerator<T> implements GenericInterface<T>{
 * 如果不声明泛型，如：class FruitGenerator implements GenericInterface<T>，编译器会报错："Unknown class"
 */
public class GenericInterfaceImplFruitGenerator1<T> implements GenericInterface<T> {
    private T key;

    @Override
    public T next() {
        return key;
    }

    private GenericInterfaceImplFruitGenerator1(T key) {
        this.key = key;
    }

    public static void main(String[] args) {
        GenericInterfaceImplFruitGenerator1 fruitGenerator = new GenericInterfaceImplFruitGenerator1("ss");
        System.out.println(fruitGenerator.next());

        GenericInterfaceImplFruitGenerator1 fruitGenerator1 = new GenericInterfaceImplFruitGenerator1(123);
        System.out.println(fruitGenerator1.next());
    }
}
