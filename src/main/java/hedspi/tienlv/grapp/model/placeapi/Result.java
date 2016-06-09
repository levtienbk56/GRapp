/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hedspi.tienlv.grapp.model.placeapi;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Lev Tien
 */
public class Result {

    public static final String[] array = {
        "amusement_park",
        "aquarium",
        "art_gallery",
        "bar",
        "book_store",
        "bowling_alley",
        "cafe",
        "campground",
        "casino",
        "library",
        "movie_rental",
        "movie_theater",
        "museum",
        "night_club",
        "park",
        "rv_park",
        "spa",
        "stadium",
        "zoo",
        "food",
        "restaurant",
        "beauty_salon",
        "dentist",
        "doctor",
        "gym",
        "hair_care",
        "health",
        "hospital",
        "hindu_temple",
        "airport",
        "atm",
        "bank",
        "bus_station",
        "car_repair",
        "car_wash",
        "cemetery",
        "church",
        "courthouse",
        "electrician",
        "embassy",
        "establishment",
        "fire_station",
        "funeral_home",
        "gas_station",
        "general_contractor",
        "insurance_agency",
        "jewelry_store",
        "laundry",
        "lawyer",
        "liquor_store",
        "meal_delivery",
        "meal_takeaway",
        "mosque",
        "moving_company",
        "painter",
        "parking",
        "pet_store",
        "pharmacy",
        "physiotherapist",
        "place_of_worship",
        "plumber",
        "police",
        "real_estate_agency",
        "roofing_contractor",
        "storage",
        "subway_station",
        "synagogue",
        "taxi_stand",
        "train_station",
        "travel_agency",
        "veterinary_care",
        "local_government_office",
        "locksmith",
        "lodging",
        "bakery",
        "bicycle_store",
        "car_dealer",
        "car_rental",
        "clothing_store",
        "convenience_store",
        "department_store",
        "electronics_store",
        "florist",
        "furniture_store",
        "grocery_or_supermarket",
        "hardware_store",
        "home_goods_store",
        "shoe_store",
        "shopping_mall",
        "store",
        "accounting",
        "city_hall",
        "finance",
        "post_office",
        "school",
        "university"};
    public static final List<String> listTypes = Arrays.asList(array);

    private Geometry geometry;
    private String icon;
    private String id;
    private String name;
    private List<Photo> photos;
    private String place_id;
    private String scope;
    private int price_level;
    private double rating;
    private String reference;
    private List<String> types;
    private String vicinity;

    public Result() {
    }

    public Result(Geometry geometry, String icon, String id, String name, List<Photo> photos, String place_id, String scope, int price_level, double rating, String reference, List<String> types, String vicinity) {
        this.geometry = geometry;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.photos = photos;
        this.place_id = place_id;
        this.scope = scope;
        this.price_level = price_level;
        this.rating = rating;
        this.reference = reference;
        this.types = types;
        this.vicinity = vicinity;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getPrice_level() {
        return price_level;
    }

    public void setPrice_level(int price_level) {
        this.price_level = price_level;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getTypesInString() {
        String str = "";
        for (String s : types) {
            str += s + ",";
        }
        return str;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    @Override
    public boolean equals(Object o) {
        Result obj = (Result) o;
        if (obj.place_id.equals(this.place_id)) {
            return true;
        } else {
            return false;
        }
    }

}
