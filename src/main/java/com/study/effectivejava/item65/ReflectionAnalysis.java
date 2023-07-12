package com.study.effectivejava.item65;

import org.springframework.util.StopWatch;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ReflectionAnalysis {
    public static void main(String[] args) {
        String name = HashSet.class.getName();
        Set<String> set = makeSetWithReflection(name);
        set.add("hi");
        System.out.println(set);

        invokeMethod();

        ////////////////////////////////////

        // 리플렉션은 성능이 중요한 어플리케이션에선 사용하지말자
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("리플렉션사용");
        for (int i = 0; i < 100; i++) {
            makeSetWithReflection(name);
        }
        stopWatch.stop();

        stopWatch.start("일반생성자사용");
        for (int i = 0; i < 100; i++) {
            makeSetNormal();
        }
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());

        ////////////////////

//        Set<String> s = makeSetWithReflection(ArrayList.class.getName()); // ArrayList로 인스턴스 만드는거에선 에러나지않는다.. 제네릭은 런타임에 다 소거되기때문.. 다만, ArrayList를 만들었으니, Set<String> 캐스팅할때 런타임에러가 나게된다




    }

    private static void invokeMethod() {
        Set<String> set = new HashSet<>();
        set.add("bye");

        System.out.println("bye 하나 있음: "+set);
        try {
            Method remove = set.getClass().getDeclaredMethod("remove", Object.class);
            remove.invoke(set, "bye");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("암것도없음: "+set);
    }

    private static Set<String> makeSetNormal() {
        return new HashSet<>();
    }

    private static Set<String> makeSetWithReflection(String clazzName) {
        Class<? extends Set<String>> clazz = null;
        try {
            clazz = (Class<? extends Set<String>>) Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Constructor<? extends Set<String>> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            return constructor.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
