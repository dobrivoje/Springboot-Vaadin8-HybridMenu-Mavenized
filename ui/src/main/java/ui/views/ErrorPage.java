package ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = ErrorPage.NAME)
public class ErrorPage extends VerticalLayout implements View {

    public static final String NAME = "error";

    public ErrorPage() {
        System.err.println("error page constructor");
    }

    @PostConstruct
    void init() {
        addComponent(new Label("This is the error view"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        System.err.println("error page in \"enter\" method...");
    }
}
