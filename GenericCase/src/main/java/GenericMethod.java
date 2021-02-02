/**
 * 泛型方法
 */
public class GenericMethod {
    public static void main(String[] args) {
        GenerateT<Fruit> generateTest = new GenerateT<Fruit>();
        generateTest.show_1(new Fruit());
        generateTest.show_2(new Fruit());
        generateTest.show_3(new Fruit());
        generateTest.show_1(new Apple());
        generateTest.show_2(new Apple());
        generateTest.show_3(new Apple());
    }
}

class GenerateT<T> {
    public void show_1(T t) {
        System.out.println(t.toString());
    }

    public <T> void show_2(T t) {
        System.out.println(t.toString());
    }

    public <E> void show_3(E t) {
        System.out.println(t.toString());
    }
}

class Fruit {
    @Override
    public String toString() {
        return "fruit";
    }
}

class Apple extends Fruit {
    @Override
    public String toString() {
        return "apple";
    }
}

class Person {
    @Override
    public String toString() {
        return "Person";
    }
}
