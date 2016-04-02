package ru.avk.ui.views;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.avk.domain.User;
import ru.avk.service.AdminService;
import ru.avk.ui.RootUI;
import ru.avk.ui.components.LogoutLink;
import ru.avk.ui.events.LogoutEvent;
import ru.avk.ui.events.UserLoggedInEvent;

@SpringView(name = MainView.NAME)
public class MainView extends AbstractView {

    @Autowired
    private AdminService adminService;

    public final static String NAME = "main";

    private ObjectProperty<String> welcomeLabelText;
    private LogoutLink logoutLink;

    public MainView() {

        registerWithEventbus();

        if(RootUI.getCurrent().isUserAnonymous()){
            showLoginPage();
            return;
        }
        welcomeLabelText = new ObjectProperty<>("");

        updateWelcomeMessage();
        Label welcomeLabel = new Label(welcomeLabelText, ContentMode.HTML);

        addComponent(welcomeLabel);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        final Link profileLink = new Link("Your Profile", new ExternalResource("#!" + ProfileView.NAME));
        profileLink.setIcon(FontAwesome.USER);
        horizontalLayout.addComponent(profileLink);

        Link adminLink = new Link("Admin page", new ExternalResource("#!" + AdminView.NAME));
        adminLink.setIcon(FontAwesome.LOCK);
        horizontalLayout.addComponent(adminLink);

        Link aboutLink = new Link("About", new ExternalResource("#!" + AboutView.NAME));
        aboutLink.setIcon(FontAwesome.QUESTION_CIRCLE);
        horizontalLayout.addComponent(aboutLink);
        addComponent(horizontalLayout);

        logoutLink = new LogoutLink();
        logoutLink.updateVisibility();
        horizontalLayout.addComponent(logoutLink);

        Button adminButton = new Button("Admin Button");
        adminButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                adminService.doSomeAdministrationTask();
            }
        });
        addComponent(adminButton);


    }

    private void showLoginPage() {
        RootUI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
    }

    private void updateWelcomeMessage() {
        String username = null;
        final User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        username = principal.getUsername();

        welcomeLabelText
                .setValue("<h1>Welcome " + username + "!</h1><hr/>");
    }

    @Subscribe
    public void userLoggedIn(UserLoggedInEvent event) {
        updateWelcomeMessage();
        logoutLink.updateVisibility();
    }

    @Subscribe
    public void userLoggedOut(LogoutEvent event) {
        logoutLink.updateVisibility();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
