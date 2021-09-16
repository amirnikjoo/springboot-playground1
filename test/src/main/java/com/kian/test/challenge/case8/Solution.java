package com.kian.test.challenge.case8;

public class Solution {

    public static void main(String[] args) {
        int a = 0;
        if (a % 2 == 1)
            System.out.println("Weird");
        else {
            if ( a > 20)
                System.out.println("Not Weird");
            else if ( a >= 6)
                System.out.println("Weird");
            else if (a >= 2)
                System.out.println("Not Weird");
        }

    }
}
