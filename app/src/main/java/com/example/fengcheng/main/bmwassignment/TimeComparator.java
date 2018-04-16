package com.example.fengcheng.main.bmwassignment;

import java.util.Comparator;

/**
 * @Package com.example.fengcheng.main.bmwassignment
 * @FileName TimeComparator
 * @Date 4/16/18, 10:16 AM
 * @Author Created by fengchengding
 * @Description BmwAssignment
 */

public class TimeComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        LocationBean l1 = (LocationBean) o1;
        LocationBean l2 = (LocationBean) o2;


        return l1.getArrivalTime().compareTo(l2.getArrivalTime());
    }
}
