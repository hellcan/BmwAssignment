package com.example.fengcheng.main.bmwassignment;

import java.util.Comparator;

/**
 * @Package com.example.fengcheng.main.bmwassignment
 * @FileName NameComparator
 * @Date 4/16/18, 9:48 AM
 * @Author Created by fengchengding
 * @Description BmwAssignment
 */

public class NameComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        LocationBean locationBean1 = (LocationBean)o1;
        LocationBean locationBean2 = (LocationBean)o2;

        return locationBean1.getName().compareTo(locationBean2.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
