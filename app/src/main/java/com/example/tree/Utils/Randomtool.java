package com.example.tree.Utils;

import java.util.HashMap;
import java.util.Random;

public class Randomtool {//随机出题
    Object[] values = new Object[20];
    Random random = new Random();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    public Object[] ali(int size){
        for(int i = 0;;i++){
            int number = random.nextInt(size);
            hashMap.put(number, i);
            if (hashMap.size()==20) {
                break;
            }
        }
        values = hashMap.keySet().toArray();
        return values;
    }
}
