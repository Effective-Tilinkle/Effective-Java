package com.study.effectivejava.item46;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.function.BinaryOperator.maxBy;

public class CollectorsAnalysis {
    public static void main(String[] args) {
//        Collectors.groupingBy();
//        Collectors.joining()
    }

    void toMap_예제_앨범들중에서_음악가의_베스트앨범() {
        List<Album> albums = null;

        Map<Artist, Album> collect = albums.stream()
                .collect(Collectors.toMap(Album::getArtist, v -> v, maxBy(comparingInt(Album::getSales))));


        // TODO: 2023/05/21 왜 이건 Optional로 리턴할까?
        Map<Artist, Optional<Album>> collect2 = albums.stream()
                .collect(Collectors.groupingBy(Album::getArtist, Collectors.maxBy(comparingInt(Album::getSales))));

    }

    static class Artist {}
    static class Album {
        private final Artist artist;
        private final int sales;

        public Album(Artist artist, int sales) {
            this.artist = artist;
            this.sales = sales;
        }

        public Artist getArtist() {
            return artist;
        }

        public int getSales() {
            return sales;
        }
    }
}
