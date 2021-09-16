package com.kian.test.challenge.case4;

class MyCalculator implements AdvancedArithmetic{
    @Override
    public int divisor_sum(int n) {
        int ret = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0)
                ret += i;
        }
        return ret;
    }
}
