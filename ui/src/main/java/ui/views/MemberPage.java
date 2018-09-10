package ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = MemberPage.NAME)
public class MemberPage extends VerticalLayout implements View {

    public static final String NAME = "member";

    @PostConstruct
    void init() {
        Label title = new Label();
        title.setCaption("Member");
        title.setValue("Member view");
        addComponent(title);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
