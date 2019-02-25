package backend.persistence20.disk;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author д06ри, dobri7@gmail.com
 */
public class BlankDisc implements CompactDisc {

    private String title;
    private String artist;
    private List<String> tracks = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="g/s">
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<String> getTracks() {
        return tracks;
    }

    public void setTracks(List<String> tracks) {
        this.tracks = tracks;
    }
    //</editor-fold>

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
        for (String track : tracks) {
            System.out.println("-Track: " + track);
        }
    }
}
