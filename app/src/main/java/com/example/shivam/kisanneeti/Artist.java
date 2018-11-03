package com.example.shivam.kisanneeti;

/**
 * Created by hp on 28-10-2018.
 */

public class Artist {

    //private String ArtistId;
    private String ArtistName;
    private String ArtistGenre;

    public Artist(){

    }

    public Artist( String artistName, String artistGenre) {
      //  ArtistId = artistId;
        ArtistName = artistName;
        ArtistGenre = artistGenre;
    }

    /*public String getArtistId() {
        return ArtistId;
    }*/

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public void setArtistGenre(String artistGenre) {
        ArtistGenre = artistGenre;
    }

    public String getArtistGenre() {
        return ArtistGenre;

    }
}
