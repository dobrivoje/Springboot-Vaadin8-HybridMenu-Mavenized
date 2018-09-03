package ui.service;

import org.springframework.stereotype.Service;

@Service
public class MojServis {

    String ime;

    public MojServis() {
    }

    public String zdravo(String ime) {
        return "Zdravo " + ime;
    }

}
