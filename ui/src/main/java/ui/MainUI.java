package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import kaesdingeling.hybridmenu.HybridMenu;
import kaesdingeling.hybridmenu.builder.HybridMenuBuilder;
import kaesdingeling.hybridmenu.builder.NotificationBuilder;
import kaesdingeling.hybridmenu.builder.left.LeftMenuButtonBuilder;
import kaesdingeling.hybridmenu.builder.left.LeftMenuSubMenuBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuButtonBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuLabelBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuSubContentBuilder;
import kaesdingeling.hybridmenu.components.NotificationCenter;
import kaesdingeling.hybridmenu.data.DesignItem;
import kaesdingeling.hybridmenu.data.MenuConfig;
import kaesdingeling.hybridmenu.data.enums.EMenuComponents;
import kaesdingeling.hybridmenu.data.enums.EMenuStyle;
import kaesdingeling.hybridmenu.data.enums.ENotificationPriority;
import kaesdingeling.hybridmenu.data.leftmenu.MenuButton;
import kaesdingeling.hybridmenu.data.leftmenu.MenuSubMenu;
import kaesdingeling.hybridmenu.data.top.TopMenuButton;
import kaesdingeling.hybridmenu.data.top.TopMenuLabel;
import kaesdingeling.hybridmenu.data.top.TopMenuSubContent;
import org.springframework.beans.factory.annotation.Autowired;
import ui.views.LoginPage;
import ui.views.MemberPage;
import ui.views.SettingsPage;
import ui.views.ThemeBuilderPage;
import ui.views.GroupPage;
import ui.views.HomePage;
import system.eventbus.Events;
import com.vaadin.ui.Button;
import system.manager.SpringViewChangeManager;
import system.manager.NavigationManager;
import com.google.common.eventbus.Subscribe;
import com.vaadin.spring.navigator.SpringViewProvider;
import ui.views.ErrorPage;

@SpringUI
@Theme("mytheme")
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Title("Vaadin Spring Boot with HybridMenu Template")
@SuppressWarnings("serial")
public class MainUI extends UI {

    private final SpringViewProvider viewProvider;
    private final NavigationManager navigationManager;

    private HybridMenu hybridMenu;
    private final NotificationCenter notificationCenter;
    private final MenuConfig menuConfig = new MenuConfig();

    @Autowired
    private Events events;

    @Autowired
    private SpringViewChangeManager springViewChangeManager;

    // @Autowired
    // private LoginPage loginPage;
    @Autowired
    public MainUI(SpringViewProvider viewProvider, NavigationManager navigationManager) {
        notificationCenter = new NotificationCenter(5000);

        this.viewProvider = viewProvider;
        this.navigationManager = navigationManager;
        this.navigationManager.addProvider(this.viewProvider);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        events.register(this);

        // navigationManager.addProvider(viewProvider);
        setNavigator(navigationManager);

        navigationManager.setErrorProvider(viewProvider);
        navigationManager.setErrorView(viewProvider.getView(ErrorPage.NAME));
//
//        navigationManager.addView(LoginPage.NAME, viewProvider.getView(LoginPage.NAME));
//        navigationManager.addView(HomePage.NAME, viewProvider.getView(HomePage.NAME));
//        navigationManager.addView(GroupPage.NAME, viewProvider.getView(GroupPage.NAME));
//        navigationManager.addView(MemberPage.NAME, viewProvider.getView(MemberPage.NAME));
//        navigationManager.addView(SettingsPage.NAME, viewProvider.getView(SettingsPage.NAME));
//        navigationManager.addView(ThemeBuilderPage.NAME, viewProvider.getView(ThemeBuilderPage.NAME));

        LoginPage loginPage = new LoginPage();
        navigationManager.init(this, loginPage);
        setContent(loginPage);
        navigationManager.navigateToLoginView();

        menuConfig.setDesignItem(DesignItem.getDarkDesign());

        hybridMenu = HybridMenuBuilder.get()
                .setContent(new VerticalLayout())
                .setMenuComponent(EMenuComponents.LEFT_WITH_TOP)
                .setConfig(menuConfig)
                .withNotificationCenter(this.notificationCenter)
                .setInitNavigator(false)
                .withViewChangeManager(springViewChangeManager)
                .build();
    }

    private void buildTopMenu(HybridMenu hybridMenu) {
        TopMenuButtonBuilder.get()
                .setCaption("Home")
                .setIcon(VaadinIcons.HOME)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .setNavigateToName(HomePage.NAME)
                .build(hybridMenu);

        TopMenuButtonBuilder.get()
                .setCaption("Member")
                .setIcon(VaadinIcons.USER)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .setHideCaption(false)
                .setNavigateToName(MemberPage.NAME)
                .build(hybridMenu);

        TopMenuButtonBuilder.get()
                .setCaption("Member")
                .setIcon(VaadinIcons.USER)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .setHideCaption(false)
                .addStyleName(EMenuStyle.ICON_RIGHT)
                .setNavigateToName(MemberPage.NAME)
                .build(hybridMenu);

        TopMenuSubContent userAccountMenu = TopMenuSubContentBuilder.get()
                .setButtonCaption("Test User")
                .setButtonIcon(new ThemeResource("images/profilDummy.jpg"))
                .addButtonStyleName(EMenuStyle.ICON_RIGHT)
                .addButtonStyleName(EMenuStyle.PROFILVIEW)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .build(hybridMenu);

        userAccountMenu.addLabel("Account");
        userAccountMenu.addHr();
        userAccountMenu.addButton("Test");
        userAccountMenu.addHr();
        userAccountMenu.addButton("Test 2");
        userAccountMenu.getButton().addClickListener((Button.ClickEvent event) -> {
            System.err.println(event.getButton().getId());
        });

        TopMenuButtonBuilder.get()
                .setCaption("Home")
                .setIcon(VaadinIcons.HOME)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .setToolTip("2")
                .setNavigateToName(HomePage.NAME)
                .build(hybridMenu);

        TopMenuButton notiButton = TopMenuButtonBuilder.get()
                .setIcon(VaadinIcons.BELL_O)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .build(hybridMenu);

        this.notificationCenter.setNotificationButton(notiButton);

        TopMenuLabel label = TopMenuLabelBuilder.get()
                .setCaption("<b>Hybrid</b> Menu")
                .setIcon(new ThemeResource("images/hybridmenu-Logo.png"))
                .build(hybridMenu);

        label.getComponent().addClickListener(e -> {
            // UI.getCurrent().getNavigator().navigateTo(HomePage.class.getSimpleName());
            navigationManager.navigateTo(HomePage.NAME);
        });

        TopMenuButton notiButtonLow = TopMenuButtonBuilder.get()
                .setCaption("Add Low notification")
                .setIcon(VaadinIcons.ALARM)
                .setUseOwnListener(true)
                .build(hybridMenu);

        TopMenuButton notiButtonMedium = TopMenuButtonBuilder.get()
                .setCaption("Add Medium notification")
                .setIcon(VaadinIcons.CHILD)
                .setUseOwnListener(true)
                .build(hybridMenu);

        TopMenuButton notiButtonHigh = TopMenuButtonBuilder.get()
                .setCaption("Add High notification")
                .setIcon(VaadinIcons.MUSIC)
                .setUseOwnListener(true)
                .build(hybridMenu);

        notiButtonLow.addClickListener(e -> {
            NotificationBuilder.get(this.notificationCenter)
                    .withCaption("Test")
                    .withDescription("This is a LOW notification")
                    .withPriority(ENotificationPriority.LOW)
                    .withCloseButton()
                    .build();
        });

        notiButtonMedium.addClickListener(e -> {
            NotificationBuilder.get(this.notificationCenter)
                    .withCaption("Test")
                    .withDescription("This is a MEDIUM notification")
                    .build();
        });

        notiButtonHigh.addClickListener(e -> {
            NotificationBuilder.get(this.notificationCenter)
                    .withCaption("Test")
                    .withDescription("This is a HIGH notification")
                    .withPriority(ENotificationPriority.HIGH)
                    .withIcon(VaadinIcons.INFO)
                    .withCloseButton()
                    .build();
        });

        TopMenuButtonBuilder.get()
                .setCaption("Home")
                .setIcon(VaadinIcons.HOME)
                .setNavigateToName(HomePage.NAME)
                .build(hybridMenu);

    }

    private void buildLeftMenu(HybridMenu hybridMenu) {
        MenuButton homeButton = LeftMenuButtonBuilder.get()
                .withCaption("Home")
                .withIcon(VaadinIcons.HOME)
                .withNavigateTo(HomePage.NAME)
                .build();

        hybridMenu.addLeftMenuButton(homeButton);

        MenuButton themeBuilderButton = LeftMenuButtonBuilder.get()
                .withCaption("Theme Builder")
                .withIcon(VaadinIcons.WRENCH)
                .withNavigateTo(ThemeBuilderPage.NAME)
                .build();

        hybridMenu.addLeftMenuButton(themeBuilderButton);

        MenuButton settingsButton = LeftMenuButtonBuilder.get()
                .withCaption("Settings")
                .withIcon(VaadinIcons.COGS)
                .withNavigateTo(SettingsPage.NAME)
                .withClickListener((Button.ClickEvent e) -> {
                    events.post(new Events.MainMenuClickEvent());
                }).build();

        hybridMenu.addLeftMenuButton(settingsButton);

        MenuSubMenu memberList = LeftMenuSubMenuBuilder.get()
                .setCaption("Member")
                .setIcon(VaadinIcons.USERS)
                .setConfig(hybridMenu.getConfig())
                .build(hybridMenu);

        memberList.addLeftMenuButton(LeftMenuButtonBuilder.get()
                .withCaption("Member")
                .withIcon(VaadinIcons.USER)
                .withNavigateTo(MemberPage.NAME)
                .build());

        memberList.addLeftMenuButton(LeftMenuButtonBuilder.get()
                .withCaption("Group")
                .withIcon(VaadinIcons.USERS)
                .withNavigateTo(GroupPage.NAME)
                .build());

        MenuSubMenu demoSettings = LeftMenuSubMenuBuilder.get()
                .setCaption("Settings")
                .setIcon(VaadinIcons.COGS)
                .setConfig(hybridMenu.getConfig())
                .build(hybridMenu);

        LeftMenuButtonBuilder.get()
                .withCaption("White Theme")
                .withIcon(VaadinIcons.PALETE)
                .withClickListener(e -> hybridMenu.switchTheme(DesignItem.getWhiteDesign()))
                .build(demoSettings);

        LeftMenuButtonBuilder.get()
                .withCaption("White Color Theme")
                .withIcon(VaadinIcons.PALETE)
                .withClickListener(e -> hybridMenu.switchTheme(DesignItem.getWhiteBlueDesign()))
                .build(demoSettings);

        LeftMenuButtonBuilder.get()
                .withCaption("Dark Theme")
                .withIcon(VaadinIcons.PALETE)
                .withClickListener(e -> hybridMenu.switchTheme(DesignItem.getDarkDesign()))
                .build(demoSettings);

        LeftMenuButtonBuilder.get()
                .withCaption("Toggle MinimalView")
                .withIcon(VaadinIcons.PALETE)
                .withClickListener(e -> hybridMenu.setLeftMenuMinimal(!hybridMenu.isLeftMenuMinimal()))
                .build(demoSettings);
    }

    public HybridMenu getHybridMenu() {
        return hybridMenu;
    }

    @Subscribe
    public void loginTry(Events.LoginTryEvent lte) {
        System.err.println("Events.LoginTryEvent        class : " + getClass() + "          ->           " + lte.getUsername());
    }

    @Subscribe
    public void loginSuccess(Events.LoginSuccessEvent lse) {
        System.err.println("Events.LoginSuccessFullEvent        class : " + getClass());
        setMainPage();
    }

    private void setMainPage() {
        buildTopMenu(hybridMenu);
        buildLeftMenu(hybridMenu);

        setContent(hybridMenu);
        navigationManager.navigateTo(HomePage.NAME);
    }
}
