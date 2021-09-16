package com.kian.test.challenge.case7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        List a = new ArrayList();
        a.add("Amir");
        a.add("###");
        a.add(2);

        Iterator it = a.iterator();
        while(it.hasNext()){
            Object b = it.next();
            if (b instanceof String && b.toString().equals("###"))
                break;
            else
                System.out.println(b.toString());
        }
    }

}
