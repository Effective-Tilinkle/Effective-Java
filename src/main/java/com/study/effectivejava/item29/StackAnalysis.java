package com.study.effectivejava.item29;

import java.util.Stack;

public class StackAnalysis {
    public static void main(String[] args) {
        Stack<String> stringStack = new Stack<>();
        stringStack.pop();

//        // Stack 내부
//        class Stack<E> extends Vector<E> {
//            public synchronized E pop() {
//                E       obj;
//                int     len = size();
//
//                obj = peek();
//                removeElementAt(len - 1);
//
//                return obj;
//            }
//
//            public synchronized E peek() {
//                int     len = size();
//
//                if (len == 0)
//                    throw new EmptyStackException();
//                return elementAt(len - 1);
//            }
//        }
//
//
//
//
//        // Vector 내부
//
//        public synchronized E elementAt(int index) {
//            if (index >= elementCount) {
//                throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
//            }
//
//            return elementData(index);
//        }
//
//        @SuppressWarnings("unchecked")
//        E elementData(int index) {
//            return (E) elementData[index]; // elementData는 Object[]
//        }

    }
}
