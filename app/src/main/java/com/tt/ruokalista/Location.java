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

    public static void load() {
        // Espoo
        locations.put("546", new Location("546", "Stella Business Park, Terra", "Espoo"));
        locations.put("79", new Location("79", "Stella Business Park, Nova", "Espoo"));
        locations.put("77", new Location("77", "Kaivomestari", "Espoo"));
        locations.put("12", new Location("12", "Business Park Mankkaa", "Espoo"));
        locations.put("12651", new Location("12651", "Niittykatu 8", "Espoo"));
        locations.put("13821", new Location("13821", "Ravintola Laturi", "Espoo"));
        locations.put("16435", new Location("16435", "Metropolia Vanha Maantie 6", "Espoo"));
        locations.put("57", new Location("57", "Keilasatama 5", "Espoo"));
        locations.put("16435", new Location("16435", "Metropolia Vanha Maantie 6", "Espoo"));

        // Turku
        locations.put("70", new Location("70", "Old Mill", "Turku"));
        locations.put("54", new Location("54", "ICT-Talo", "Turku"));
        locations.put("132", new Location("132", "Auriga Business Center, Fiskarholmen", "Turku"));
        locations.put("16543", new Location("16543", "Candio", "Turku"));
        locations.put("64", new Location("64", "Turun AMK, Lemminkäisenkatu", "Turku"));
        locations.put("14627", new Location("14627", "Fläkt Woods", "Turku"));

        // Tampere
        locations.put("22", new Location("22", "Dynamo Business Park", "Tampere"));
        locations.put("83", new Location("83", "Rengasterassi", "Tampere"));
        locations.put("19759", new Location("19759", "Ravintola Näköala", "Tampere"));
        locations.put("91", new Location("91", "Tampereen Oikeustalo", "Tampere"));
        locations.put("19758", new Location("19758", "Ravintola Palopesä", "Tampere"));
        locations.put("92", new Location("92", "Tampereen yliopisto, Linna", "Tampere"));
        locations.put("19754", new Location("19754", "Ravintola Tietotalo", "Tampere"));
        locations.put("58", new Location("58", "Kulmasarvis", "Tampere"));
        locations.put("19751", new Location("19751", "Ravintola Varikko", "Tampere"));
        locations.put("19682", new Location("19682", "Ravintola Vaski", "Tampere"));
        locations.put("12812", new Location("12812", "TTY Tietotalo", "Tampere"));
        locations.put("22", new Location("22", "Dynamo Business Park", "Tampere"));

        // Oulu
        locations.put("12820", new Location("12820", "Technopolis Peltola RE5T4UR4NT MYRSKY", "Oulu"));
        locations.put("8336", new Location("8336", "Technopolis Luoto", "Oulu"));
        locations.put("49", new Location("49", "Technopolis Galaksi", "Oulu"));
        locations.put("122", new Location("122", "Technopolis Oulu, Kahvila", "Oulu"));
        locations.put("16", new Location("16", "Technopolis Oulu Linnanmaa, Elektra", "Oulu"));
        locations.put("72", new Location("72", "Oulun kaupunki, Oulu ja Haukipudas koulut ja päiväkodit", "Oulu"));
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
    public int compareTo(@NonNull Location another) {
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
