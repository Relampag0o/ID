package org.example.apimodels; 
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Attributes{
    public Date createdAt;
    public Date updatedAt;
    public String slug;
    public String synopsis;
    public String description;
    public int coverImageTopOffset;
    public Titles titles;
    public String canonicalTitle;
    public ArrayList<String> abbreviatedTitles;
    public String averageRating;
    public RatingFrequencies ratingFrequencies;
    public int userCount;
    public int favoritesCount;
    public String startDate;
    public String endDate;
    public Object nextRelease;
    public int popularityRank;
    public int ratingRank;
    public String ageRating;
    public String ageRatingGuide;
    public String subtype;
    public String status;
    public String tba;
    public PosterImage posterImage;
    public CoverImage coverImage;
    public int episodeCount;
    public int episodeLength;
    public int totalLength;
    public String youtubeVideoId;
    public String showType;
    public boolean nsfw;

}
