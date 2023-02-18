package com.study.effectivejava.item9;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TryWithResources {
    public static void main(String[] args) throws IOException {
        try (FileInputStream fIn = new FileInputStream("");
             FileOutputStream fOut = new FileOutputStream("")) {
            System.out.println("작업");
        }
    }

    /*
     TryWithResources.class 내용

     public static void main(String[] args) throws IOException {
        FileInputStream fIn = new FileInputStream(""); // 1

        try {
            FileOutputStream fOut = new FileOutputStream("");

            try {
                System.out.println("작업");
            } catch (Throwable var7) {
                try {
                    fOut.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6); // 2
                }

                throw var7;
            }

            fOut.close();
        } catch (Throwable var8) {
            try {
                fIn.close();
            } catch (Throwable var5) {
                var8.addSuppressed(var5);
            }

            throw var8;
        }

        fIn.close();
    }

    1. try-with-resources 를 사용하기 위해선 AutoCloseable 인터페이스를 구현해야한다. (FileInputStream은 AutoCloseable의 구현체다)
    2. 여러개의 자원을 해제해야하는 상황에서, 예외처리를 addSuppressed를 사용하여 예외를 숨겨서 전달해준다.
        자바7에서 Throwable에 추가된 getSuppressed 메서드를 이용하면 프로그램 코드에서 가져올 수 있다

     */
}
