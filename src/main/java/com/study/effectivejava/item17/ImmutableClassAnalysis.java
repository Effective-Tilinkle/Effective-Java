package com.study.effectivejava.item17;

import java.math.BigInteger;

public class ImmutableClassAnalysis {
    public static void main(String[] args) {
        BigInteger bigInteger = new BigInteger("100");
        BigInteger negate = bigInteger.negate();

        /*
         // BigInteger 클래스 내부

         // 불변 객체끼리 내부적으로 데이터를 공유할 수 있다
         public BigInteger negate() {
            return new BigInteger(this.mag, -this.signum); // mag는 int[] 을 가리킴. 즉, int[]는 지금 새로만드는 BigInteger에서 공유해서 사용. 이렇게 공유할수있는 BigInteger를 생성할때는 아래와 같이 package-private 생성자를 사용
         }

         BigInteger(int[] magnitude, int signum) { // package-private 생성자
            this.signum = (magnitude.length == 0 ? 0 : signum);
            this.mag = magnitude;
            if (mag.length >= MAX_MAG_LENGTH) {
                checkRange();
            }
         }

        -----------------------------------------

        // BigInteger의 가변 동반클래스는 MutableBigInteger가 있다
        public BigInteger sqrt() {
            if (this.signum < 0) {
                throw new ArithmeticException("Negative BigInteger");
            }

            return new MutableBigInteger(this.mag).sqrt().toBigInteger(); // 이런식으로 mag 값을 가지고 MutableBigInteger 클래스를 생성한뒤 sqrt를 호출하면 내부 상태값이 변경이 되는데(연산에 여러 단계가 있으면 MutalbeBigInteger를 더 많이 사용하며 메서드를 호출하여 상태 변경하는부분이 더 많다), 이를 BigInteger로 마지막에 생성해서 반환해준다
        }
         */


        // String의 가변동반클래스 StringBuilder
        String test = "abcdefgabcdefg";
        System.out.println(test.replace("bc","r"));

        /*

        public String replace(CharSequence target, CharSequence replacement) {
            String tgtStr = target.toString();
            String replStr = replacement.toString();
            int j = indexOf(tgtStr);
            if (j < 0) {
                return this;
            }
            int tgtLen = tgtStr.length();
            int tgtLen1 = Math.max(tgtLen, 1);
            int thisLen = length();

            int newLenHint = thisLen - tgtLen + replStr.length();
            if (newLenHint < 0) {
                throw new OutOfMemoryError();
            }
            StringBuilder sb = new StringBuilder(newLenHint); // (1)
            int i = 0;
            do {
                sb.append(this, i, j).append(replStr);  // (2)
                i = j + tgtLen;
            } while (j < thisLen && (j = indexOf(tgtStr, j + tgtLen1)) > 0);
            return sb.append(this, i, thisLen).toString(); // (3)
        }

        (1) StringBuilder라는 String의 가변동반 클래스를 사용
        (2) 가변 동반 클래스의 도움으로 String을 계속 새로 만드는게 아닌, StringBuilder 객체 내부에서 replace요청한 String을 삽입(StringBuilder의 상태변경)
        (3) 최종 String(불변객체) return

        => (2)가 가변 동반 클래스의 이점이자, 활용할 수 있는 포인트라고 생각합니다. 여기서 만약 StringBuilder인 가변클래스를 사용하지않았다면, target 문자열을 만날때마다 계속 String(불변객체)이 생성되었을 것이고, 중간연산에서만 사용된 String 불변객체들은 곧 다 버려지게 될 것입니다.
        이런 부분은 BigInteger에서도 확인가능한데, BigInteger 내부에서 계산하는식에는 여러 많은 연산과정이 필요한곳에 가변동반클래스인 MutableBigInteger를 사용하고있습니다
         */
    }

    // 불변 객체가 확장가능하도록 설계되었다면, 아래와같은 방어적 복사가 필요하다 (BigInteger와 BigDecimal은 불변객체이나 확장가능하도록 설계되었다
    public static BigInteger safeInstance(BigInteger val) {
        return val.getClass() == BigInteger.class ? val : new BigInteger(val.toByteArray());
    }
}
