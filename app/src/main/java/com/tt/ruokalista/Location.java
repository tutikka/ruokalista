package com.tt.ruokalista;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Location implements Comparable<Location> {

    private static Map<String, Location> locations = new HashMap<>();

    public static List<Location> getLocations() {
        List<Location> result = new ArrayList<>();
        result.addAll(locations.values());
        Collections.sort(result);
        return (result);
    }

    public static void load(Context context) {
        List<Location> result = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(context.getAssets().open("locations.csv"), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split("\\|", -1);
                Location location = new Location();
                location.setCode(parseString(s[0]));
                location.setTitle(parseString(s[1]));
                location.setCity(parseString(s[2]));
                location.setType(parseString(s[3]));
                location.setAddress(parseString(s[4]));
                location.setPostalCode(parseString(s[5]));
                location.setUri(parseString(s[6]));
                location.setLatitude(parseDouble(s[7]));
                location.setLongitude(parseDouble(s[8]));
                locations.put(location.getCode(), location);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
        }
        Collections.sort(result);
    }

    private static String parseString(String s) {
        if (s == null || s.length() == 0) {
            return (null);
        } else {
            return (s.trim());
        }
    }

    private static double parseDouble(String s) {
        try {
            return (Double.parseDouble(s.trim()));
        } catch (Exception e) {
            return (-1);
        }
    }

    public static Location getLocation(String code) {
        return (locations.get(code));
    }

    private String code;

    private String title;

    private String city;

    private String type;

    private String address;

    private String postalCode;

    private String uri;

    private double latitude;

    private double longitude;

    public Location() {
    }

    public Location(String code, String title, String city) {
        this.code = code;
        this.title = title;
        this.city = city;
    }

    @Override
    public String toString() {
        return (title + ", " + city);
    }

    @Override
    public int compareTo(Location another) {
        int i = city.compareTo(another.getCity());
        if (i == 0) {
            return (title.compareTo(another.getTitle()));
        } else {
            return (i);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
