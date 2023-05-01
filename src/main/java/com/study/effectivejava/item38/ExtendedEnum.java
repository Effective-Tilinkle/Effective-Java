package com.study.effectivejava.item38;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtendedEnum {
    interface Operation {
        Map<String, Operation> map =
                Stream.of(BasicOperation.values(), ExtendedOperation.values())
                        .flatMap(arr -> Arrays.asList(arr).stream())
                        .collect(Collectors.toMap(op -> op.toString(), Function.identity()));

        double apply(double x, double y);
        static Optional<Operation> fromString(String symbol) {
            return Optional.ofNullable(map.get(symbol));
        }
    }

    enum BasicOperation implements Operation {
        PLUS("+") {
            @Override
            public double apply(double x, double y) {
                return x + y;
            }
        },
        MINUS("-") {
            @Override
            public double apply(double x, double y) {
                return x - y;
            }
        };

        private final String symbol;

        BasicOperation(String symbol) {

            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }

        //        public abstract double apply(double x, double y); // 인터페이스에 선언되어있기때문에 별도의 추상메서드 필요없음
    }

    enum ExtendedOperation implements Operation {
        REMAINDER("%") {
            @Override
            public double apply(double x, double y) {
                return x % y;
            }
        };

        private final String symbol;

        ExtendedOperation(String symbol) {

            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }

    }

    public static void main(String[] args) {
        double x = 10;
        double y = 3;

        test(ExtendedOperation.class, x, y);
        test(BasicOperation.class, x, y);
        test2(Arrays.asList(ExtendedOperation.values()), x, y);

        System.out.println(Operation.fromString("+").get());
        System.out.println(Operation.fromString("%").get());
        System.out.println(Operation.fromString("^")); // empty

    }

    private static <T extends Enum<T> & Operation> void test(Class<T> opEnumType, double x, double y) { // class 리터럴은 한정적 타입토큰역할.. 이를 활용하여 코드의 변경없이 어떤 enum을 넘겨주느냐에따라 BasicOperation을 사용할수도, ExtendedOperation을 사용할수도있다
        for (Operation o : opEnumType.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n",
                    x, o, y, o.apply(x, y));
        }
    }

    private static void test2(Collection<? extends Operation> opSet, double x, double y) { // 좀더 유연한 버전
        for (Operation o : opSet) {
            System.out.printf("%f %s %f = %f%n",
                    x, o, y, o.apply(x, y));
        }
    }
}
