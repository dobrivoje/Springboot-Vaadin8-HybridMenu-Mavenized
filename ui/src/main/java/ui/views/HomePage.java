package ui.views;

import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import data.Person;
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

    public HomePage() {
        System.err.println("home stranica konstruktor...");
    }

    @PostConstruct
    private void init() {
        events.post(new Events.LoginTryEvent("HomePage : pokušava da se loguje !   ha ha haaaaaa !"));
        Responsive.makeResponsive(this);

//        Label title = new Label();
//        title.setCaption("Home");
//        title.setValue("Home view");
        List<Person> people = Arrays.asList(
                new Person("Nicolaus Copernicus", 1543),
                new Person("Galileo Galilei", 1564),
                new Person("Johannes Kepler", 1571));
        Grid<Person> grid = new Grid<>();
        grid.addStyleName("style-dobri1");
        grid.setItems(people);
        grid.addColumn(Person::getName).setCaption("Name");
        grid.addColumn(Person::getBirthYear).setCaption("Year of birth");
        grid.setSizeFull();

//        addComponent(title);
        addComponent(grid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // events.post(new Events.LoginTryEvent("3. HomePage page..."));
    }
}
