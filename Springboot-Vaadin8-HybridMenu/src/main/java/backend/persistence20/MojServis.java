package backend.persistence20;

import org.springframework.stereotype.Service;

/**
 *
 * @author д06ри, dobri7@gmail.com
 */
@Service
public class MojServis {

    String ime;
    String prezime;

    //<editor-fold defaultstate="collapsed" desc="konstr">
    public MojServis() {
    }

    public MojServis(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    //</editor-fold>

    public String getHello() {
        return ime + " " + prezime;
    }
}
