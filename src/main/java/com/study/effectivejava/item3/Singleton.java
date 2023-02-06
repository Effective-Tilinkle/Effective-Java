package com.study.effectivejava.item3;

import java.io.Serializable;

public class Singleton {
    static class Elvis { // 정적 멤버 필드로 만드는방식.
        public static final Elvis INSTANCE = new Elvis();
        private Elvis() {}
        public void doSomething() {
            //...
        }
    }

    static class Tom { // 정적 팩토리 메서드를 제공
        private static final Tom INSTANCE = new Tom();
        private Tom() {}

        public static Tom getInstance() { return INSTANCE; }

        public void doSomething() {
            //...
        }
    }

    static class TomSerialization implements Serializable {
        private static final TomSerialization INSTANCE = new TomSerialization();
        private transient String instanceField; //transient필요
        private TomSerialization() {}

        public static TomSerialization getInstance() { return INSTANCE; }

        public void doSomething() {
            //...
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    enum Jeremy {
        INSTANCE;

        public void doSomething() {
            // ...
        }
    }

}
