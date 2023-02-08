package com.study.effectivejava.item2;

import java.util.EnumSet;
import java.util.Set;

public class HierarchyBuilder {
    static abstract class Pizza {
        enum Topping {HAM, CHEESE}
        final Set<Topping> toppings;

        Pizza(Builder<?> builder) {
            this.toppings = builder.toppings.clone();
        }

        abstract static class Builder<T extends Builder<T>> { // 재귀적 타입한정 => 공변반환 타이핑 가능하도록 해준다.?
            EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

            public T addTopping(Topping topping) {
                toppings.add(topping);
                return self();
            }

            protected abstract T self();

            abstract Pizza build();
        }
    }

    static class NyPizza extends Pizza {
        public enum Size {SMALL, MEDIUM, LARGE}
        private final Size size;
        private NyPizza(Builder builder) {
            super(builder);
            size = builder.size;
        }

        static class Builder extends Pizza.Builder<Builder> {
            private final Size size;

            public Builder(Size size) {
                this.size = size;
            }

            @Override
            protected Builder self() {
                return this;
            }

            @Override
            NyPizza build() {
                return new NyPizza(this);
            }
        }

    }

    public static void main(String[] args) {
        NyPizza nyPizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addTopping(Pizza.Topping.HAM)
                .addTopping(Pizza.Topping.CHEESE)
                .build();
    }
}
