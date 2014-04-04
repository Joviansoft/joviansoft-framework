package com.joviansoft.mpolice.session;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bigbao on 14-2-24.
 */
public class TestConcurrentHashMapTest {
   // Map<Integer, String> map = new ConcurrentHashMap<Integer, String>(128, 0.75f, 128);
   Map<Integer, String> map = new HashMap<Integer, String>();
    @Test
    public void test(){
//        map.put("a", "a");
//        map.put("b", "b");
//        map.put("c", "c");

        for(int i = 0; i<100000;i++){
            map.put(i,"" + i);
        }

        Iterator iterator = map.keySet().iterator();

        while (iterator.hasNext()){
            String str = map.get(iterator.next());

            System.out.println(str);

        }



    }

}
