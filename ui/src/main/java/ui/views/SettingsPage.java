package ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import system.eventbus.Events;

@SpringView(name = SettingsPage.NAME)
//@SpringComponent
//@Lazy(false)
public class SettingsPage extends VerticalLayout implements View {
    
    public static final String NAME = "settings";
    
    @Autowired
    private Events events;
    
    public SettingsPage() {
        System.err.println("SettingsPage stranica konstruktor...");
        System.err.println(events == null);
    }
    
    @PostConstruct
    void init() {
//        events.register(this);
        Responsive.makeResponsive(this);
        
        Label title = new Label();
        title.setCaption("Settings");
        title.setValue("Settings view");
        addComponent(title);
        events.post(new Events.LoginTryEvent("2. settings page..."));
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
