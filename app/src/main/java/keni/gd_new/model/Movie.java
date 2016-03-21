package keni.gd_new.model;

/**
 * Created by Keni on 29.02.2016.
 */
public class Movie {
    private String id, title, thumbnailUrl, rating, year, district;
    private int genre, place;
    private double latitude, longitude;

    public Movie() {
    }

    public Movie(String id, String name, String thumbnailUrl, String year, String rating, String district, int genre, int place, double latitude, double longitude)
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

}
