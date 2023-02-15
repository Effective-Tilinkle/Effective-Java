package com.study.effectivejava.item5;

import java.util.function.Supplier;

public class DependencyInjection {

    static class Mosaic {
        private Tile tile;

        private Mosaic(Tile tile) {
            this.tile = tile;
        }

        static Mosaic create(Supplier<Tile> supplier) { // 여기서 supplier는 Tile의 Factory
            return new Mosaic(supplier.get());
        }
    }

    interface Tile { }

    static class 박판타일 implements Tile { }

    static class 인테리어타일 implements Tile { }

    static class 세라믹타일 implements Tile { }

    public static void main(String[] args) {
        Mosaic.create(세라믹타일::new);
    }


}
