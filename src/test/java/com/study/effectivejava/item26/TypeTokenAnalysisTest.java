package com.study.effectivejava.item26;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeTokenAnalysisTest {
    @Test
    void test() throws IOException {
        Class<String> type = String.class;
        Class<Integer> type2 = Integer.class;
        MyClass<String> stringMyClass = new MyClass<>(String.class);
//        MyClass<List<String>> stringMyClass = new MyClass<>(List<String>.class); // 파라미터화 타입은 클래스 리터럴 사용 불가
        MyClass<Integer> integerMyClass = new MyClass<>(type2);

        String abc = stringMyClass.get("abc");

        List<String> o = new ObjectMapper().readValue("", new TypeReference<>() {}); // 컴파일 타임에 안전
//        List<Integer> o = new ObjectMapper().readValue("", new TypeReference<String>() {}); // 컴파일 에러.. 예전 Jackson은 이게 허용되었음..
        List<Integer> o1 = new TempObjectMapper().readValue_oldJackson("", new TypeReference<String>() {});
    }

    static class MyClass<T> {
        Class<T> tClass;

        public MyClass(Class<T> tClass) {
            this.tClass = tClass; // 타입토큰.. 런타임시에 <T>는 소거되지만, tClass에 클래스에 대한 정보를 가지고 있기때문에, 아래 get 메서드에서 cast 해줄수 있다. 즉 타입토큰을 통해서 런타임시에도 안정적으로 캐스팅 가능!
        }

        public T get(Object obj){
            return tClass.cast(obj); // ClassCastException 유발가능~
        }
    }

    @Test
    void nullTest() {
        List<?> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);

        assertEquals(4, list.size());
    }

    static class TempObjectMapper{
        public <T> T readValue(String content, com.fasterxml.jackson.core.type.TypeReference<T> valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
            return null;
        }

        public <T> T readValue_oldJackson(String content, com.fasterxml.jackson.core.type.TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
            return null;
        }
    }

}