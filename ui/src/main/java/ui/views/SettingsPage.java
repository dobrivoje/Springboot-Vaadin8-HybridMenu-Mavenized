package ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = SettingsPage.NAME)
public class SettingsPage extends VerticalLayout implements View {

    public static final String NAME = "settings";

    @PostConstruct
    void init() {
        Responsive.makeResponsive(this);

        Label title = new Label();
        title.setCaption("Settings");
        title.setValue("Settings view");
        addComponent(title);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
