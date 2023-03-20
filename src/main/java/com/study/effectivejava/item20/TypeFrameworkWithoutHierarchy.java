package com.study.effectivejava.item20;

public class TypeFrameworkWithoutHierarchy {
    interface Singer {
        AudioClip sing(Song s);
        void dance();
    }
    interface Songwriter {
        Song compose(int chartPosition);
    }

    interface SingerSongwriter extends Singer, Songwriter {
        void actSensitive();
    }

    // 위의 인터페이스를 클래스로 만들면

    static abstract class SingerAbs {
        abstract AudioClip sing(Song s);
        abstract void dance();
    }

    static abstract class SongwriterAbs {
        abstract Song compose(int chartPosition);
    }

    static abstract class SingerSongwriterAbs {
        abstract AudioClip sing(Song s); // 중복
        abstract void dance(); // 중복
        abstract Song compose(int chartPosition); // 중복
        abstract void actSensitive();
    }

    static abstract class SingerSongwriterAbsWithoutDance {
        abstract AudioClip sing(Song s); // 중복
        abstract Song compose(int chartPosition); // 중복
        abstract void actSensitive();
    }

    static abstract class SingerSongwriterAbsWithoutSing {
        abstract void dance();; // 중복
        abstract Song compose(int chartPosition); // 중복
        abstract void actSensitive();
    }


    private static class AudioClip {}
    private static class Song {}
}
