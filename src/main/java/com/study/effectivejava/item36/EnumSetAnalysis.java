package com.study.effectivejava.item36;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.EnumSet;

import static com.study.effectivejava.item36.EnumSetAnalysis.Style.*;

public class EnumSetAnalysis {
    public static void main(String[] args) throws JsonProcessingException {
        Text text = new Text();
        text.applyStyles(Text.STYLE_BOLD | Text.STYLE_ITALIC);

        /*
        RegularEnumSet 내부 (해당 enum의 상수 갯수가 64개 이하일 경우)

        // ...

        public boolean add(E e) {
            typeCheck(e);

            long oldElements = elements;
            elements |= (1L << ((Enum<?>)e).ordinal()); // enum의 ordinal을 사용하여 element와 OR 비트연산을 하여 enum의 상수를 저장한다 (long을 사용하니 64비트까지만..)
            return elements != oldElements;
        }

        // ...

        public boolean retainAll(Collection<?> c) {
            if (!(c instanceof RegularEnumSet<?> es))       // RegularEnumSet 즉, 64개 이하의 EnumSet이 아니라면~
                return super.retainAll(c);

            if (es.elementType != elementType) {            // element는 Class<T> 인데, 위의 예에서는 Style.class로 보면됨. 즉, 다른 타입이면~
                boolean changed = (elements != 0);
                elements = 0;
                return changed;
            }

            long oldElements = elements;
            elements &= es.elements;                        // AND(&) 비트 연산을 사용하여 파라미터로받은 c의 elements들과 같은것들만 남기도록.. 만약 중복된게 없으면 빈값이됨..
            return elements != oldElements;
        }
         */



        /////////////////////
        // json test
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(enumSet));
//
//        List<Style> list = new ArrayList<>();
//        list.add(BOLD);
//        list.add(ITALIC);
//        System.out.println(objectMapper.writeValueAsString(list));
    }

    enum Style {
        BOLD,
        ITALIC,
        UNDERLINE
    }

    static class Text { // 구닥다리 기법
        public static final int STYLE_BOLD = 1 << 0;
        public static final int STYLE_ITALIC = 1 << 1;
        public static final int STYLE_UNDERLINE = 1 << 2;
        public static final int STYLE_STRIKETHROUGH = 1 << 3;
        private String str;

        public void applyStyles(int styles) {
            if ((styles & STYLE_BOLD) != 0) { // STYLE_BOLD 면
                applyBold();
            }

            if ((styles & STYLE_ITALIC) != 0) { // STYLE_BOLD 면
                applyItalic();
            }

            // ...

        }

        private void applyItalic() {
            System.out.println("italic 적용");
        }

        private void applyBold() {
            System.out.println("bold 적용");
        }

    }

}
