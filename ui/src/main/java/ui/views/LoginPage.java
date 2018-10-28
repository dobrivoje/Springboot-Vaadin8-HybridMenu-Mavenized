package ui.views;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Arrays;
import system.eventbus.Events;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = LoginPage.NAME)
public class LoginPage extends CssLayout implements View {

    public static final String NAME = "login";

    public static final String[] DOMAINS = new String[]{/*"INTERMOL", "RIS",*/"SERVER"};
    private TextField username;
    private PasswordField password;
    private ComboBox domain;

    private Button login;
    private Button forgotPassword;

    // @Autowired
    // private MojServis mojServis;
    //
    @Autowired
    private Events events;

    public LoginPage() {
        buildUI();
        username.focus();
    }

    @PostConstruct
    protected void initEvents() {
        events.register(this);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    private void buildUI() {
        addStyleName("login-screen");

        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        // information text about logging in
        CssLayout loginInformation = buildLoginInformation();

        addComponent(centeringLayout);
        addComponent(loginInformation);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();

        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.setMargin(false);

        username = new TextField("Username");
        username.setIcon(VaadinIcons.USER);
        username.setWidth(15, Unit.EM);
        loginForm.addComponent(username);

        password = new PasswordField("password");
        password.setWidth(15, Unit.EM);
        password.setDescription("**************");
        password.setIcon(VaadinIcons.LOCK);
        loginForm.addComponent(password);

        domain = new ComboBox("Domain", Arrays.asList(DOMAINS));
        domain.setWidth(15, Unit.EM);
        domain.setDescription("Help");
        domain.setIcon(VaadinIcons.DASHBOARD);
        domain.setTextInputAllowed(false);
        domain.setValue(DOMAINS[0]);
        domain.setEmptySelectionAllowed(false);
        loginForm.addComponent(domain);

        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        loginForm.addComponent(buttons);

        buttons.addComponent(login = new Button("Login"));
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.setDisableOnClick(true);
        login.addClickListener((Button.ClickEvent e) -> {
            try {
                getUsernameAndPassword();
                events.post(new Events.LoginSuccessEvent());
            } finally {
                login.setEnabled(true);
            }
        });

        buttons.addComponent(forgotPassword = new Button("Fotgotten password"));
        forgotPassword.addClickListener((Button.ClickEvent e) -> {
            showNotification(new Notification("For reseting password"));
        });
        forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);

        return loginForm;
    }

    private CssLayout buildLoginInformation() {
        CssLayout loginInformation = new CssLayout();
        loginInformation.setStyleName("login-information");
        Label loginInfoText = new Label(
                "<h1>" + "Application" + "</h1>"
                + "<h2>" + "App name" + " " + "Version 1.0" + " build " + "001" + "</h2>"
                + "LOGINPAGE.FIRM.CLIENT.MESSAGE",
                ContentMode.HTML
        );
        loginInformation.addComponent(loginInfoText);
        return loginInformation;
    }

    private void getUsernameAndPassword() {
        String un;

        switch (((String) domain.getValue()).toLowerCase()) {
            case "domain1":
                un = "domain1\\";
                break;
            case "domain2":
                un = "rs.domain2\\";
                break;
            case "server":
            default:
                un = "";
                break;
        }

        un += (username.getValue()).toLowerCase();
    }

    private void showNotification(Notification notification) {
        notification.setDelayMsec(3000);
        notification.show(Page.getCurrent());
    }
}
