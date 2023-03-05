package com.study.effectivejava.item14;

import java.util.Comparator;

public class ComparatorConstructionMethod {
    public static void main(String[] args) {
        Comparator<PhoneNumber> phoneNumberComparator = Comparator.comparingInt((PhoneNumber pn) -> pn.getAreaCode()) // 비교자 생성메서드 (정적 메서드) 키 추출자 function을 기반으로 Comparator를 생성해줌
                .thenComparingInt(pn -> pn.getPrefix()) // 비교자 생성메서드(인스턴스 메서드)
                .thenComparingInt(pn -> pn.getLineNumber());

        /*
        Comparator 인터페이스

         public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) { // 비교자를 생성 (정적 메서드!)
            Objects.requireNonNull(keyExtractor);
            return (Comparator<T> & Serializable)
                (c1, c2) -> Integer.compare(keyExtractor.applyAsInt(c1), keyExtractor.applyAsInt(c2)); // Integer의 compare를 활용하여 Comparator를 생성
        }

        default Comparator<T> thenComparingInt(ToIntFunction<? super T> keyExtractor) { // 비교자를 생성 (인스턴스메서드!)
           return thenComparing(comparingInt(keyExtractor));              // 새로 전달받은 KeyExtractor를 기반으로 Comparator를 생성. 반환할 Comparator는 thenComparing에서 생성
        }

        default Comparator<T> thenComparing(Comparator<? super T> other) {
            Objects.requireNonNull(other);
            return (Comparator<T> & Serializable) (c1, c2) -> {
                int res = compare(c1, c2);                        // 기존 인스턴스에 정의된 compare로 비교한 뒤,
                return (res != 0) ? res : other.compare(c1, c2);  // 두개가 동일하다면, 방금 새로 생성한 comparator로 비교
            };                                                    // 하는 Comparator를 생성해준다!
        }

         */
        PhoneNumber phoneNumber = new PhoneNumber(123, 222, 333);
        PhoneNumber downPhoneNumber = new PhoneNumber(123, 222, 331);

        int compare = phoneNumberComparator.compare(phoneNumber, downPhoneNumber);
        System.out.println(compare > 0);
    }

    static class PhoneNumber {
        int areaCode; // 핵심필드 1
        int prefix; // 핵심필드 2
        int lineNumber; // 핵심필드 3

        public PhoneNumber(int areaCode, int prefix, int lineNumber) {
            this.areaCode = areaCode;
            this.prefix = prefix;
            this.lineNumber = lineNumber;
        }

        public int getAreaCode() {
            return areaCode;
        }

        public int getPrefix() {
            return prefix;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }


}
