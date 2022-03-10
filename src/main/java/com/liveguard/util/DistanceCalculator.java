package com.liveguard.util;

/**
 * Program for distance between two points on earth
 * Problem can be solved using Haversine formula:
 *
 * The great circle distance or the orthodromic distance is the shortest distance between two points on a sphere
 * (or the surface of Earth). In order to use this method, we need to have the co-ordinates of point A and point
 * B.The great circle method is chosen over other methods.
 *
 * First, convert the latitude and longitude values from decimal degrees to radians. For this divide the values
 * of longitude and latitude of both the points by 180/pi. The value of pi is 22/7. The value of 180/pi is
 * approximately 57.29577951. If we want to calculate the distance between two places in miles, use the value
 * 3, 963, which is the radius of Earth. If we want to calculate the distance between two places in kilometers,
 * use the value 6, 378.8, which is the radius of Earth.
 *
 * Find the value of the latitude in radians:
 * Value of Latitude in Radians, lat = Latitude / (180/pi) OR
 * Value of Latitude in Radians, lat = Latitude / 57.29577951
 * Find the value of longitude in radians:
 * Value of Longitude in Radians, long = Longitude / (180/pi) OR
 * Value of Longitude in Radians, long = Longitude / 57.29577951
 *
 * Get the co-ordinates of point A in terms of latitude and longitude. Use the above conversion method to convert
 * the values of latitude and longitude in radians. I will call it as lat1 and long1. Do the same for the
 * co-ordinates of Point B and get lat2 and long2.
 *
 *
 * Now, to get the distance between point A and point B use the following formula:
 * Distance, d = 3963.0 * arccos[(sin(lat1) * sin(lat2)) + cos(lat1) * cos(lat2) * cos(long2 – long1)]
 *
 * The obtained distance, d, is in miles. If you want your value to be in units of kilometers, multiple d by 1.609344.
 * d in kilometers = 1.609344 * d in miles
 * Thus you can have the shortest distance between two places on Earth using the great circle distance approach.
 */


public class DistanceCalculator {

    public static double distance(double lat1, double lat2, double lon1, double lon2) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }

}
