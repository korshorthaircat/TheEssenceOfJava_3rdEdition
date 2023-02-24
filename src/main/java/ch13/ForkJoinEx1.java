package ch13;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class ForkJoinEx1 {
    static final ForkJoinPool pool = new ForkJoinPool(); //쓰레드풀을 생성함
    //fork&join프레임웤에서 제공하는 쓰레드풀. 지정된 수의 쓰레드를 미리 만들어놓고 반복해서 재사용할 수 있다.
    //장점: 쓰레드를 반복해 생성하지 않아도 되는 점, 너무 많은 쓰레드가 생성되어 성능이 저하될 수도 있는 점을 막아줌
    //쓰레드풀은 쓰레드가 수행해야하는 작업이 담긴 큐를 제공하고, 각 쓰레드는 자신의 작업 큐에 담긴 작업을 순서대로 처리함
    //참고: 쓰레드풀은 기본적으로 코어의 개수와 동일한 수의 쓰레드를 생성함
    public static void main(String[] args) {
        long from = 1L;
        long to = 100_000_000L;

        SumTask task = new SumTask(from, to); //수행할 작업을 생성함

        //1) fork&join 프레임웍으로 작업 수행하는 경우
        long startTime = System.currentTimeMillis(); //시작시간 초기화
        Long result = pool.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed time()(8 core)" + (endTime - startTime));
        System.out.printf("sum of %d ~ %d = %d\n", from, to, result);
        System.out.println();
        //실행 결과
//        Elapsed time()(8 core)1089
//        sum of 1 ~ 100000000 = 5000000050000000

        //2) 반복문으로 작업 수행하는 경우
        result = 0L;
        startTime = System.currentTimeMillis(); //시작시간 초기화
        for(long i = from; i <= to; i++)
            result += i;
        endTime = System.currentTimeMillis();
        System.out.println("Elapsed time()(1 core)" + (endTime - startTime));
        System.out.printf("sum of %d ~ %d = %d\n", from, to, result);
        //실행 결과
//        Elapsed time()(1 core)177
//        sum of 1 ~ 100000000 = 5000000050000000

        //1)로 작업을 수행할 떄보다 2)로 작업을 수행할 때 시간이 덜 걸림
        //작업을 나누고 다시 합치는데 걸리는 시간이 있기 때문
        //(재귀호출보다 for문이 더 빠른 것 과 같은 이유)
        // -> 항상 멀티쓰레드로 처리하는 것이 빠른 것이 아님!

    }
}

class SumTask extends RecursiveTask<Long> {
    long from;
    long to;

    public SumTask(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    protected Long compute() { //compute()의 구조는 일반적인 재귀호출 메서드와 동일함
        long size = to - from + 1; //from <= i <= ti

        if(size <= 5)
            return sum(); //더할 숫자가 5개 이하이면, 숫자의 합을 반환

        long half = (from + to) / 2;

        //범위를 반으로 나누어 두 개의 작업을 생성 (작업의 범위를 어떻게 나눌것인지 정의해줘야 함)
        SumTask leftSum = new SumTask(from, half);
        SumTask rightSum = new SumTask(half + 1, to);

        leftSum.fork(); //fork() : 해당 작업(leftSum)을 작업 큐에 넣는다. 비동기 메서드.
        return rightSum.compute() + leftSum.join(); //join() : 해당 작업의 수행이 끝날 때까지 기다렸다가, 수행이 끝나면 그 결과를 반환한다. 동기 메서드.
        //즉, fork()로 작업을 작업큐에 넣고, compute()로 작업을 나누는 일을 반복함. 더 이상 작업을 나눌 수 없게 되었을 때 compute()의 재귀호출이 끝나면, join()의 결과를 기다렸다가 더해 결과를 반환한다.
        //재귀호출된 compute()가 모두 종료될 때 최종결과를 얻는다.
    }

    long sum() {
        long sumResult = 0L;
        for(long i = from; i <= to; i++) {
            sumResult += i;
        }
        return sumResult;
    }

}
