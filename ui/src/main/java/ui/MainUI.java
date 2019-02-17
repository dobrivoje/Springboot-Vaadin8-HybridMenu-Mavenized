package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.server.Responsive;
import org.springframework.beans.factory.annotation.Autowired;
import ui.views.MemberPage;
import ui.views.SettingsPage;
import ui.views.ThemeBuilderPage;
import ui.views.GroupPage;
import ui.views.HomePage;
import system.eventbus.Events;
import system.uimanagement.NavigationManager;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.HasValue;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Notification;
import elemental.json.JsonArray;
import kaesdingeling.hybridmenu.HybridMenu;
import kaesdingeling.hybridmenu.components.HMButton;
import kaesdingeling.hybridmenu.components.HMLabel;
import kaesdingeling.hybridmenu.components.HMSubMenu;
import kaesdingeling.hybridmenu.components.HMTextField;
import kaesdingeling.hybridmenu.components.LeftMenu;
import kaesdingeling.hybridmenu.components.NotificationCenter;
import kaesdingeling.hybridmenu.components.TopMenu;
import kaesdingeling.hybridmenu.data.MenuConfig;
import kaesdingeling.hybridmenu.design.DesignItem;
import ui.views.ErrorPage;
import ui.views.NotificationBuilderPage;

@SpringUI
@Theme("mytheme")
@Viewport("width=device-width,initial-scale=1.0,user-scalable=yes")
@Title("Vaadin Spring Boot with HybridMenu Template")
@SuppressWarnings("serial")
public class MainUI extends UI implements DetachListener {

    private final SpringViewProvider viewProvider;
    private final NavigationManager navigationManager;

    private HybridMenu hybridMenu;
    private NotificationCenter notificationCenter;

    @Autowired
    private Events events;

    @Autowired
    public MainUI(SpringViewProvider viewProvider, NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
        this.viewProvider = viewProvider;
        this.navigationManager.addProvider(viewProvider);

        super.setNavigator(navigationManager);
    }

    @Subscribe
    public void loginTry(Events.LoginTryEvent lte) {
        System.err.println("Events.LoginTryEvent        class : " + getClass() + "          ->           " + lte.getUsername());
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        events.register(this);
        events.post(new Events.LoginTryEvent("DOBRILO !!!"));

        Responsive.makeResponsive(this);

        UI.getCurrent().setPollInterval(5000);

        hybridMenu = HybridMenu.get()
                .withNaviContent(new VerticalLayout())
                .withConfig(MenuConfig.get().withDesignItem(DesignItem.getDarkDesign()))
                .build();

        buildTopOnlyMenu();
        buildLeftMenu();

        getNavigator().addViewChangeListener((ViewChangeListener.ViewChangeEvent event) -> {
//        navigationManager.addViewChangeListener((ViewChangeListener.ViewChangeEvent event) -> {
            if (event.getOldView() != null && event.getOldView().getClass().getSimpleName().equals(ThemeBuilderPage.class.getSimpleName())) {
                hybridMenu.switchTheme(DesignItem.getDarkDesign());
            }
            return true;
        });

        UI.getCurrent().getNavigator().setErrorProvider(viewProvider);
        navigationManager.setErrorProvider(viewProvider);
        setErrorHandler((com.vaadin.server.ErrorEvent event) -> {
//            navigationManager.navigateToLoginView();
//            navigationManager.navigateTo("loginPage");
            Notification.show("Sorry, you don't have access to do that.");

            navigationManager.navigateTo(ErrorPage.NAME);
        });

        setContent(hybridMenu);

        JavaScript.getCurrent().addFunction("aboutToClose", (JsonArray arg) -> {
            detach();
        });

        Page.getCurrent().getJavaScript().execute("window.onbeforeunload = function (e) { var e = e || window.event; aboutToClose(); return; };");
    }

    private void buildTopOnlyMenu() {
        TopMenu topMenu = hybridMenu.getTopMenu();
        HMTextField searcher = HMTextField.get(VaadinIcons.SEARCH, "Search ...");
        searcher.setValueChangeMode(ValueChangeMode.LAZY);
        searcher.addValueChangeListener((HasValue.ValueChangeEvent<String> event) -> {
            System.err.println("tražim... ->\nStaro : " + event.getOldValue() + "\nNovo : " + event.getValue());
        });
        topMenu.add(searcher);

        topMenu.add(HMButton.get()
                .withIcon(VaadinIcons.HOME)
                .withDescription("Home")
                // .withNavigateTo(HomePage.class));
                .withNavigateTo(HomePage.NAME));

        hybridMenu.getNotificationCenter()
                .setNotiButton(topMenu.add(HMButton.get()
                        .withDescription("Notifications")));
    }

    private void buildLeftMenu() {
        LeftMenu leftMenu = hybridMenu.getLeftMenu();

        leftMenu.add(HMLabel.get()
                .withCaption("<b>Hybrid</b> Menu")
                .withIcon(new ThemeResource("images/hybridmenu-Logo.png")));

        hybridMenu.getBreadCrumbs().setRoot(leftMenu.add(HMButton.get()
                .withCaption("Home")
                .withIcon(VaadinIcons.HOME)
                .withNavigateTo(HomePage.NAME)));

        leftMenu.add(HMButton.get()
                .withCaption("Notification Builder")
                .withIcon(VaadinIcons.BELL)
                // .withNavigateTo(NotificationBuilderPage.class));
                .withNavigateTo(NotificationBuilderPage.NAME));

        leftMenu.add(HMButton.get()
                .withCaption("Theme Builder")
                .withIcon(VaadinIcons.WRENCH)
                .withNavigateTo(ThemeBuilderPage.NAME));

        HMSubMenu memberList = leftMenu.add(HMSubMenu.get()
                .withCaption("Member")
                .withIcon(VaadinIcons.USERS));

        memberList.add(HMButton.get()
                .withCaption("Settings")
                .withIcon(VaadinIcons.COGS)
                .withNavigateTo(SettingsPage.NAME));

        memberList.add(HMButton.get()
                .withCaption("Member")
                .withIcon(VaadinIcons.USERS)
                .withNavigateTo(MemberPage.NAME));

        memberList.add(HMButton.get()
                .withCaption("Group")
                .withIcon(VaadinIcons.USERS)
                .withNavigateTo(GroupPage.NAME));

        HMSubMenu memberListTwo = memberList.add(HMSubMenu.get()
                .withCaption("Member")
                .withIcon(VaadinIcons.USERS));

        memberListTwo.add(HMButton.get()
                .withCaption("Settings")
                .withIcon(VaadinIcons.COGS)
                .withNavigateTo(SettingsPage.NAME));

        memberListTwo.add(HMButton.get()
                .withCaption("Member")
                .withIcon(VaadinIcons.USERS)
                .withNavigateTo(MemberPage.NAME));

        HMSubMenu demoSettings = leftMenu.add(HMSubMenu.get()
                .withCaption("Settings")
                .withIcon(VaadinIcons.COGS));

        demoSettings.add(HMButton.get()
                .withCaption("White Theme")
                .withIcon(VaadinIcons.PALETE)
                .withClickListener(e -> hybridMenu.switchTheme(DesignItem.getWhiteDesign())));

        demoSettings.add(HMButton.get()
                .withCaption("Dark Theme")
                .withIcon(VaadinIcons.PALETE)
                .withClickListener(e -> hybridMenu.switchTheme(DesignItem.getDarkDesign())));

        demoSettings.add(HMButton.get()
                .withCaption("Minimal")
                .withIcon(VaadinIcons.COG)
                .withClickListener(e -> hybridMenu.getLeftMenu().toggleSize()));
    }

    public HybridMenu getHybridMenu() {
        return hybridMenu;
    }

    @Override
    public void detach(DetachEvent event) {
        getUI().close();
    }

}
