package com.study.effectivejava.item22;

import java.io.ObjectStreamConstants;

public class ConstantInterface {
    public static void main(String[] args) {

//        // ObjectStreamConstants 는 아래와같이 다 상수로 구성되어있는 인터페이스
//
//        public interface ObjectStreamConstants {
//
//            /**
//             * Magic number that is written to the stream header.
//             */
//            static final short STREAM_MAGIC = (short)0xaced;
//
//            /**
//             * Version number that is written to the stream header.
//             */
//            static final short STREAM_VERSION = 5;
//
//            // ...
//        }
//
//        // ObjectInputStream은 ObjectStreamConstants를 구현해서 위의 상수를 사용한다..
//        public class ObjectInputStream extends InputStream implements ObjectInput, ObjectStreamConstants {
//            // ...
//            protected void readStreamHeader() throws IOException, StreamCorruptedException {
//                short s0 = bin.readShort();
//                short s1 = bin.readShort();
//                if (s0 != STREAM_MAGIC || s1 != STREAM_VERSION) { // STREAM_MAGIC, STREAM_VERSION
//                    throw new StreamCorruptedException(
//                            String.format("invalid stream header: %04X%04X", s0, s1));
//                }
//            }
//
//            // ...
//
//        }
    }
}
