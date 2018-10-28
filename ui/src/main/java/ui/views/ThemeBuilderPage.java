package ui.views;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import kaesdingeling.hybridmenu.components.ColorPicker;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.server.Responsive;
import kaesdingeling.hybridmenu.builder.NotificationBuilder;
import kaesdingeling.hybridmenu.data.DesignItem;
import kaesdingeling.hybridmenu.data.enums.ENotificationPriority;
import javax.annotation.PostConstruct;
import ui.MainUI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = ThemeBuilderPage.NAME)
public class ThemeBuilderPage extends VerticalLayout implements View {

    public static final String NAME = "theme";

    private DesignItem designItem;
    private boolean lockForLoad = true;

    @Autowired
    private MainUI mainUI;

    //<editor-fold defaultstate="collapsed" desc="GUI fields">
    /* Colors */
    private FormLayout colorsForm;
    private ColorPicker darkColor;
    private ColorPicker whiteColor;

    /* Content-Background */
    private FormLayout contentBackgroundForm;
    private ColorPicker contentBackground;

    /* Notifications */
    private FormLayout notificationForm;
    private ColorPicker notificationPrioLow;
    private ColorPicker notificationPrioMedium;
    private ColorPicker notificationPrioHigh;
    private Slider notificationBorderColorOpacity;
    private Slider notificationShadow;
    private ColorPicker notificationCloseButtonHover;
    private Slider notificationCloseButtonOpacity;

    /* Left-Menu */
    private FormLayout leftMenuForm;
    private ColorPicker leftMenuBackground;
    private ColorPicker leftMenuShadow;
    private Slider leftMenuShadowOpacity;
    private Slider leftMenuButtonOpacity;
    private ColorPicker leftMenuButtonHover;
    private ColorPicker leftMenuButtonBorderHover;
    private ColorPicker leftMenuButtonActive;
    private ColorPicker leftMenuButtonBorderActive;
    private ColorPicker leftMenuSubMenuBorder;
    private ColorPicker leftMenuButtonTooltip;

    /* Top-Menu */
    private FormLayout topMenuForm;
    private ColorPicker topMenuBackground;
    private ColorPicker topMenuShadow;
    private Slider topMenuShadowOpacity;
    private ColorPicker topMenuButtonHover;
    private ColorPicker topMenuButtonActive;
    private ColorPicker topMenuButtonBorder;
    private ColorPicker topMenuButtonTooltip;

    private TextArea jsonOutput;
    private Button importButton;
    //</editor-fold>

    @PostConstruct
    void init() {
        Responsive.makeResponsive(this);

        guiFieldsInit();
        buildNewObject();
        load();

        Label title = new Label();
        title.setCaption("Theme Builder");
        title.setValue("Here you have the possibility to create your own colour scheme.");

        jsonOutput.setWidth("100%");
        jsonOutput.setHeight("500px");

        addComponents(title, colorsForm, contentBackgroundForm, notificationForm, leftMenuForm, topMenuForm, jsonOutput, importButton);

        importButton.addClickListener(e -> {
            lockForLoad = true;
            try {
                designItem = new Gson().fromJson(jsonOutput.getValue(), DesignItem.class);
                NotificationBuilder.get(mainUI.getHybridMenu().getNotificationCenter())
                        .withCaption("Import successful")
                        .withDescription("The design adapts itself automatically")
                        .withPriority(ENotificationPriority.MEDIUM)
                        .withIcon(VaadinIcons.INFO)
                        .withCloseByHide()
                        .build();
            } catch (JsonSyntaxException e2) {
                NotificationBuilder.get(mainUI.getHybridMenu().getNotificationCenter())
                        .withCaption("Import failed")
                        .withDescription("The entry was rejected")
                        .withPriority(ENotificationPriority.MEDIUM)
                        .withIcon(VaadinIcons.INFO)
                        .withCloseByHide()
                        .build();
                buildNewObject();
            } finally {
                load();
                update();
                lockForLoad = false;
            }
        });

        update();
        lockForLoad = false;
    }

    private void guiFieldsInit() {
        /* Colors */
        colorsForm = buildForm("Colors");
        darkColor = buildPicker(colorsForm, "Dark-Color");
        whiteColor = buildPicker(colorsForm, "White-Color");

        /* Content-Background */
        contentBackgroundForm = buildForm("Content-Background");
        contentBackground = buildPicker(contentBackgroundForm, "Content-Background");

        /* Notifications */
        notificationForm = buildForm("Notifications");
        notificationPrioLow = buildPicker(notificationForm, "Notification-Prio-Low");
        notificationPrioMedium = buildPicker(notificationForm, "Notification-Prio-Medium");
        notificationPrioHigh = buildPicker(notificationForm, "Notification-Prio-High");
        notificationBorderColorOpacity = buildSlider(notificationForm, "Notification-Border-Color-Opacity");
        notificationShadow = buildSlider(notificationForm, "Notification-Shadow");
        notificationCloseButtonHover = buildPicker(notificationForm, "Notification-Close-Button-Hover");
        notificationCloseButtonOpacity = buildSlider(notificationForm, "Notification-Close-Button-Opacity");

        /* Left-Menu */
        leftMenuForm = buildForm("Left-Menu");
        leftMenuBackground = buildPicker(leftMenuForm, "Left-Menu-Background");
        leftMenuShadow = buildPicker(leftMenuForm, "Left-Menu-Shadow");
        leftMenuShadowOpacity = buildSlider(leftMenuForm, "Left-Menu-Shadow-Opacity");
        leftMenuButtonOpacity = buildSlider(leftMenuForm, "Left-Menu-Button-Opacity");
        leftMenuButtonHover = buildPicker(leftMenuForm, "Left-Menu-Button-Hover");
        leftMenuButtonBorderHover = buildPicker(leftMenuForm, "Left-Menu-Button-Border-Hover");
        leftMenuButtonActive = buildPicker(leftMenuForm, "Left-Menu-Button-Active");
        leftMenuButtonBorderActive = buildPicker(leftMenuForm, "Left-Menu-Button-Border-Active");
        leftMenuSubMenuBorder = buildPicker(leftMenuForm, "Left-Menu-Sub-Menu-Border");
        leftMenuButtonTooltip = buildPicker(leftMenuForm, "Left-Menu-Button-Tooltip");

        /* Top-Menu */
        topMenuForm = buildForm("Top-Menu");
        topMenuBackground = buildPicker(topMenuForm, "Top-Menu-Background");
        topMenuShadow = buildPicker(topMenuForm, "Top-Menu-Shadow-Two");
        topMenuShadowOpacity = buildSlider(topMenuForm, "Top-Menu-Shadow-Opacity");
        topMenuButtonHover = buildPicker(topMenuForm, "Top-Menu-Button-Hover");
        topMenuButtonActive = buildPicker(topMenuForm, "Top-Menu-Button-Active");
        topMenuButtonBorder = buildPicker(topMenuForm, "Top-Menu-Button-Border");
        topMenuButtonTooltip = buildPicker(topMenuForm, "Top-Menu-Button-Tooltip");

        jsonOutput = new TextArea("JSON-Output");
        importButton = new Button("Import JSON");
    }

    private void load() {
        /* Colors */
        darkColor.setValue(designItem.getDarkColor());
        whiteColor.setValue(designItem.getWhiteColor());

        /* Content-Background */
        contentBackground.setValue(designItem.getContentBackground());

        /* Notifications */
        notificationPrioLow.setValue(designItem.getNotificationPrioLowBackground());
        notificationPrioMedium.setValue(designItem.getNotificationPrioMediumBackground());
        notificationPrioHigh.setValue(designItem.getNotificationPrioHighBackground());
        notificationBorderColorOpacity.setValue(designItem.getNotificationBorderColorOpacity());
        notificationShadow.setValue(designItem.getNotificationShadow());
        notificationCloseButtonHover.setValue(designItem.getNotificationCloseButtonHover());
        notificationCloseButtonOpacity.setValue(designItem.getNotificationCloseButtonOpacity());

        /* Left-Menu */
        leftMenuBackground.setValue(designItem.getMenuLeftBackground());
        leftMenuShadow.setValue(designItem.getMenuLeftShadow());
        leftMenuShadowOpacity.setValue(designItem.getMenuLeftShadowOpacity());
        leftMenuButtonOpacity.setValue(designItem.getMenuLeftButtonOpacity());
        leftMenuButtonHover.setValue(designItem.getMenuLeftButtonHover());
        leftMenuButtonBorderHover.setValue(designItem.getMenuLeftButtonBorderHover());
        leftMenuButtonActive.setValue(designItem.getMenuLeftButtonActive());
        leftMenuButtonBorderActive.setValue(designItem.getMenuLeftButtonBorderActive());
        leftMenuSubMenuBorder.setValue(designItem.getMenuLeftSubMenuBorder());
        leftMenuButtonTooltip.setValue(designItem.getMenuLeftButtonTooltip());

        /* Top-Menu */
        topMenuBackground.setValue(designItem.getMenuTopBackground());
        topMenuShadow.setValue(designItem.getMenuTopShadow());
        topMenuShadowOpacity.setValue(designItem.getMenuTopShadowOpacity());
        topMenuButtonHover.setValue(designItem.getMenuTopButtonHover());
        topMenuButtonActive.setValue(designItem.getMenuTopButtonActive());
        topMenuButtonBorder.setValue(designItem.getMenuTopButtonBorder());
        topMenuButtonTooltip.setValue(designItem.getMenuTopButtonTooltip());
    }

    private void write() {
        if (!lockForLoad) {
            /* Colors */
            designItem.setDarkColor(darkColor.getValue());
            designItem.setWhiteColor(whiteColor.getValue());

            /* Content-Background */
            designItem.setContentBackground(contentBackground.getValue());

            /* Notifications */
            designItem.setNotificationPrioLowBackground(notificationPrioLow.getValue());
            designItem.setNotificationPrioMediumBackground(notificationPrioMedium.getValue());
            designItem.setNotificationPrioHighBackground(notificationPrioHigh.getValue());
            designItem.setNotificationBorderColorOpacity(notificationBorderColorOpacity.getValue());
            designItem.setNotificationShadow(notificationShadow.getValue());
            designItem.setNotificationCloseButtonHover(notificationCloseButtonHover.getValue());
            designItem.setNotificationCloseButtonOpacity(notificationCloseButtonOpacity.getValue());

            /* Left-Menu */
            designItem.setMenuLeftBackground(leftMenuBackground.getValue());
            designItem.setMenuLeftShadow(leftMenuShadow.getValue());
            designItem.setMenuLeftShadowOpacity(leftMenuShadowOpacity.getValue());
            designItem.setMenuLeftButtonOpacity(leftMenuButtonOpacity.getValue());
            designItem.setMenuLeftButtonHover(leftMenuButtonHover.getValue());
            designItem.setMenuLeftButtonBorderHover(leftMenuButtonBorderHover.getValue());
            designItem.setMenuLeftButtonActive(leftMenuButtonActive.getValue());
            designItem.setMenuLeftButtonBorderActive(leftMenuButtonBorderActive.getValue());
            designItem.setMenuLeftSubMenuBorder(leftMenuSubMenuBorder.getValue());
            designItem.setMenuTopButtonTooltip(leftMenuButtonTooltip.getValue());

            /* Top-Menu */
            designItem.setMenuTopBackground(topMenuBackground.getValue());
            designItem.setMenuTopShadow(topMenuShadow.getValue());
            designItem.setMenuTopShadowOpacity(topMenuShadowOpacity.getValue());
            designItem.setMenuTopButtonHover(topMenuButtonHover.getValue());
            designItem.setMenuTopButtonActive(topMenuButtonActive.getValue());
            designItem.setMenuTopButtonBorder(topMenuButtonBorder.getValue());
            designItem.setMenuTopButtonTooltip(topMenuButtonTooltip.getValue());
        }
    }

    private void update() {
        write();
//        mainUI.getHybridMenu().switchTheme(designItem);
        jsonOutput.setValue(new Gson().toJson(designItem));
    }

    private void buildNewObject() {
        designItem = new DesignItem();

        /* Colors */
        designItem.setDarkColor(new Color(66, 66, 66));
        designItem.setWhiteColor(new Color(245, 245, 245));

        /* Content-Background */
        designItem.setContentBackground(new Color(245, 245, 245));

        /* Notifications */
        designItem.setNotificationPrioLowBackground(new Color(224, 224, 224));
        designItem.setNotificationPrioMediumBackground(new Color(0, 96, 100));
        designItem.setNotificationPrioHighBackground(new Color(191, 54, 12));
        designItem.setNotificationBorderColorOpacity(15.0);
        designItem.setNotificationShadow(70.0);
        designItem.setNotificationCloseButtonHover(new Color(222, 30, 30));
        designItem.setNotificationCloseButtonOpacity(50.0);

        /* Left-Menu */
        designItem.setMenuLeftBackground(new Color(238, 238, 238));
        designItem.setMenuLeftShadow(new Color(189, 189, 189));
        designItem.setMenuLeftShadowOpacity(40.0);
        designItem.setMenuLeftButtonOpacity(50.0);
        designItem.setMenuLeftButtonHover(new Color(224, 224, 224));
        designItem.setMenuLeftButtonBorderHover(new Color(25, 118, 210));
        designItem.setMenuLeftButtonActive(new Color(245, 245, 245));
        designItem.setMenuLeftButtonBorderActive(new Color(51, 105, 30));
        designItem.setMenuLeftSubMenuBorder(new Color(224, 224, 224));
        designItem.setMenuTopButtonTooltip(new Color(25, 118, 210));

        /* Top-Menu */
        designItem.setMenuTopBackground(new Color(224, 224, 224));
        designItem.setMenuTopShadow(new Color(189, 189, 189));
        designItem.setMenuTopShadowOpacity(40.0);
        designItem.setMenuTopButtonHover(new Color(51, 105, 30));
        designItem.setMenuTopButtonActive(new Color(46, 125, 50));
        designItem.setMenuTopButtonBorder(new Color(100, 221, 23));
        designItem.setMenuTopButtonTooltip(new Color(25, 118, 210));
    }

    private FormLayout buildForm(String caption) {
        FormLayout formLayout = new FormLayout();
        formLayout.setCaption(caption);
        return formLayout;
    }

    private ColorPicker buildPicker(Layout layout, String caption) {
        HorizontalLayout content = new HorizontalLayout();
        ColorPicker colorPicker = new ColorPicker(true);
        content.setCaption(caption);
        colorPicker.addValueChangeListener(e -> {
            update();
        });
        content.addComponent(colorPicker);
        layout.addComponent(content);
        return colorPicker;
    }

    private Slider buildSlider(Layout layout, String caption) {
        HorizontalLayout content = new HorizontalLayout();
        Slider slider = new Slider();
        Label hexCode = new Label();
        content.setCaption(caption);
        slider.setMin(0.0);
        slider.setMax(100.0);
        slider.addStyleName("ticks");
        slider.setWidth("300px");
        slider.addValueChangeListener(e -> {
            hexCode.setValue(String.valueOf((slider.getValue()).intValue()) + " %");
            update();
        });
        hexCode.setValue(String.valueOf((slider.getValue()).intValue()) + " %");
        content.addComponents(slider, hexCode);
        layout.addComponent(content);
        return slider;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
