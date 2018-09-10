package ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;

@SpringView(name = HomePage.NAME)
public class HomePage extends VerticalLayout implements View {

    public static final String NAME = "home";

    @PostConstruct
    void init() {
        Label title = new Label();
        title.setCaption("Home");
        title.setValue("Home view");
        addComponent(title);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
