package com.felixzh.JDKVector;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

public class VectorDemo {
    public static void main(String args[]) {
        // 初始容量为3, 每次增量为2
        Vector<Integer> vector = new Vector<>(3, 2);
        System.out.println("Initial size: " + vector.size());
        System.out.println("Initial capacity: " + vector.capacity());
        vector.add(1);
        vector.add(2);
        vector.add(3);
        vector.add(4);
        vector.add(5);
        vector.add(6);
        System.out.println("Capacity after size: " + vector.size());
        System.out.println("Capacity after capacity: " + vector.capacity());

        System.out.println("First element: " + vector.firstElement());
        System.out.println("Remove element: " + vector.remove(0));
        System.out.println("Remove after size: " + vector.size());
        System.out.println("Remove after capacity: " + vector.capacity());
        System.out.println(vector.elementAt(0));
        System.out.println("First element: " + vector.firstElement());

        System.out.println("Last element: " + vector.lastElement());

        System.out.println(vector.contains(3));

        Enumeration vEnum = vector.elements();
        while (vEnum.hasMoreElements())
            System.out.print(vEnum.nextElement() + " ");

        System.out.println("=======================================================================");
        Vector<Integer> vector2 = new Vector<>(3);
        System.out.println("Initial size: " + vector2.size());
        System.out.println("Initial capacity: " + vector2.capacity());
        vector2.add(1);
        vector2.add(2);
        vector2.add(3);
        System.out.println("Capacity after size: " + vector2.size());
        System.out.println("Capacity after capacity: " + vector2.capacity());
        vector2.add(4);
        System.out.println("Capacity after size: " + vector2.size());
        System.out.println("Capacity after capacity: " + vector2.capacity());


        ArrayList<Integer> arrayList = new ArrayList<>(2);

        arrayList.add(1);
        arrayList.add(2);
        System.out.println(arrayList.size());
        arrayList.add(3);
        System.out.println(arrayList.size());
        arrayList.remove(0);
        System.out.println(arrayList.toString());
    }
}
