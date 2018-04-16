package com.example.fengcheng.main.bmwassignment;

import java.util.Comparator;

/**
 * @Package com.example.fengcheng.main.bmwassignment
 * @FileName DistanceComparator
 * @Date 4/16/18, 10:57 AM
 * @Author Created by fengchengding
 * @Description BmwAssignment
 */

public class DistanceComparator implements Comparator {
    private double lastLatitude;
    private double lastLongtitude;

    public DistanceComparator(double lastLatitude, double lastLongtitude) {
        this.lastLatitude = lastLatitude;
        this.lastLongtitude = lastLongtitude;
    }

    @Override
    public int compare(Object o1, Object o2) {
        LocationBean l1 = (LocationBean) o1;
        LocationBean l2 = (LocationBean) o2;

        Double distance1 = l1.countDistance(lastLatitude, lastLongtitude);
        Double distance2 = l2.countDistance(lastLatitude, lastLongtitude);

        if (distance1 > distance2) {
            return 1;
        } else if (distance1 < distance2) {
            return -1;
        }
        return 0;
    }
}
