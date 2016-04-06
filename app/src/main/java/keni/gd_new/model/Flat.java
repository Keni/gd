package keni.gd_new.model;

import java.util.ArrayList;

/**
 * Created by Keni on 29.02.2016.
 */
public class Flat
{
    private String id, title, thumbnailUrl, rating, year, district;
    private int genre, place;
    private double latitude, longitude;
    private ArrayList<String> images;

    public Flat()
    {
    }

    public Flat(String id, String name, String thumbnailUrl, String year, String rating, String district, int genre, int place, double latitude, double longitude, ArrayList<String> images)
    {
        this.id = id;
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.year = year;
        this.rating = rating;
        this.district = district;
        this.genre = genre;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String name)
    {
        this.title = name;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public int getGenre()
    {
        return genre;
    }

    public void setGenre(int genre)
    {
        this.genre = genre;
    }

    public int getPlace()
    {
        return place;
    }

    public void setPlace(int place)
    {
        this.place = place;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public ArrayList<String> getImages()
    {
        return images;
    }

    public void setImages(ArrayList<String> images)
    {
        this.images = images;
    }

}
