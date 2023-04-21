package com.study.effectivejava.item34;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.study.effectivejava.item34.EnumWithMethod.PayrollDay.PayType.WEEKDAY;
import static com.study.effectivejava.item34.EnumWithMethod.PayrollDay.PayType.WEEKEND;


public class EnumWithMethod {

    // 상수별로 다르게 동작하는 코드를 구현해야한다면, 추상메서드를 통한 상수별 메서드구현!
    enum Operation {
        PLUS("+") {
            @Override
            public double apply(double x, double y) { return x+y;}
        },
        MINUS("-") {
            @Override
            public double apply(double x, double y) { return x-y;}
        },
        TIMES("*") {
            @Override
            public double apply(double x, double y) { return x*y;}
        },
        DIVIDE("/") {
            @Override
            public double apply(double x, double y) { return x/y;}
        };

        private final String symbol;
        Operation(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        public abstract double apply(double x, double y); // 상수별 메서드 구현활용을 위한 추상 메서드 선언

        @Override
        public String toString() { // 재정의가능. 하지만 toString을 재정의 하였다면, 이 반환하는 문자열을 기반으로 열거타입 상수로 반환해주는 메서드를 만들자 (fromString)
            return symbol;
        }

        static Map<String, Operation> symbolToOperation = Stream.of(Operation.values())
                .collect(Collectors.toMap(Operation::toString, Function.identity())); //

        public static Optional<Operation> fromString(String symbol) { // 인자로 받은 symbol이 없을수도 있으니, 센스있게 Optional로 처리하자 (Optional을 사용하여 클라이언트에게 없을수도 있음을 알리고 이를 처리하라고 알려줌)
            return Optional.ofNullable(symbolToOperation.get(symbol));
        }
    }

    ////////////////////////////////////

    // 열거 타입 상수끼리 코드를 공유하기에 좋은 방법? -> 전략 열거 타입 사용 (중복을 막아줄 수 있다)
    public enum PayrollDay {
        MONDAY(WEEKEND), TUESDAY(WEEKDAY), WEDNESDAY(WEEKEND);
        // ...

        private final PayType payType; // 이 부분을 함수형 인터페이스를 활용해도 나쁘지않을듯..?
        PayrollDay(PayType payType) {
            this.payType = payType;
        }

        public int pay(int minutesWorked, int payRate) {
            return payType.pay(minutesWorked, payRate);
        }

        // 전략 열거 타입
        enum PayType { // 내부적으로만 사용하니 package-private
            WEEKDAY {
                @Override
                int overtimePay(int mins, int payRate) {
                    return 0;
                }
            },
            WEEKEND {
                @Override
                int overtimePay(int mins, int payRate) {
                    return 0;
                }
            };

            abstract int overtimePay(int mins, int payRate);

            int pay(int minsWorked, int payRate) {
                int basePay = minsWorked + payRate;
                return basePay + overtimePay(minsWorked, payRate);
            }
        }
    }
}
