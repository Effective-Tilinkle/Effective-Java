package com.study.effectivejava.item80;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Long invoke = forkJoinPool.invoke(new SumTask(1, 10));

        System.out.println("result: "+invoke);
    }

    static class SumTask extends RecursiveTask<Long> {
        long from;
        long to;

        public SumTask(long from, long to) {
            this.from = from;
            this.to = to;
        }


        @Override
        protected Long compute() {
            System.out.println("sumtask : "+from+","+to+" | "+Thread.currentThread().getName());
            long size=to-from+1;
            if(size<5){
                return sum();
            }

            long half=(from+to)/2;
            SumTask leftSum=new SumTask(from,half);     //나누는 작업 (fork)
            SumTask rightSum=new SumTask(half+1,to);

            leftSum.fork();

            return rightSum.compute()+leftSum.join(); //합치는작업 (join)
        }

        private Long sum() {
            long sum=0L;
            for(long i=from;i<=to;i++){
                sum+=i;
            }

            return sum;
        }
    }
}
