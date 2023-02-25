package com.study.effectivejava.item11;

import java.util.Objects;

public class HashCodeLazyInitThreadSafe { // 불변클래스 전제!
    private String coreField1;
    private String coreFiled2;
    private int hashCode; // 캐싱

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashCodeLazyInitThreadSafe that = (HashCodeLazyInitThreadSafe) o;
        return Objects.equals(coreField1, that.coreField1) && Objects.equals(coreFiled2, that.coreFiled2);
    }

    @Override
    public int hashCode() {
        int result = hashCode; // 스레드에 안전하기위해서는 공유 가능한 멤버변수를 직접 수정하지말고 이렇게 지역변수로 새로이 만들어서 진행하자
        if (result == 0) {
            result = coreField1.hashCode();
            result = 31 * result + coreFiled2.hashCode();
//            result = Objects.hash(coreField1, coreFiled2); // 위의 두줄을 이렇게 한줄로도 가능

            hashCode = result;
        }
        return result;
    }
}
