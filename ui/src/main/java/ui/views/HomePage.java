package ui.views;

import backend.persistence20.MojServis;
import backend.persistence20.disk.CompactDisc;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import backend.data.Person;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import system.eventbus.Events;

@SpringView(name = HomePage.NAME)
public class HomePage extends VerticalLayout implements View {

    public static final String NAME = "home";

    @Autowired
    private Events events;

    @Autowired
    private MojServis mojServis;

    @Autowired
    private CompactDisc cdServis;

    public HomePage() {
        System.err.println("home stranica konstruktor...");
    }

    @PostConstruct
    private void init() {
        System.err.println("-----------------------");
        System.err.println(mojServis.getHello());
        cdServis.play();
        System.err.println("-----------------------");

        Responsive.makeResponsive(this);

        List<Person> people = Arrays.asList(
                new Person("Nicolaus Copernicus", 1543), new Person("Galileo Galilei", 1564), new Person("Johannes Kepler", 1571)
        );
        Grid<Person> grid = new Grid<>();
//        ne radi :
//        grid.addStyleName("style-dobri1");
        grid.setItems(people);
        grid.addColumn(Person::getName).setCaption("Name");
        grid.addColumn(Person::getBirthYear).setCaption("Year of birth");
        grid.setSizeFull();

        addComponent(grid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // events.post(new Events.LoginTryEvent("3. HomePage page..."));
    }
}
