package ru.avk.ui.views;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import ru.avk.ui.RootUI;
import ru.avk.ui.components.GoToMainViewLink;
import ru.avk.ui.components.LogoutLink;
import ru.avk.ui.events.UserLoggedInEvent;

import javax.annotation.PostConstruct;

@Secured("ROLE_USER")
@SpringView(name = ProfileView.NAME)
public class ProfileView extends AbstractView {

    private Logger LOG = LoggerFactory.getLogger(ProfileView.class);

    public final static String NAME = "profile";

    private ObjectProperty<String> labelProperty;
    private LogoutLink logoutLink;

    public ProfileView() {
        logoutLink = new LogoutLink();
        labelProperty = new ObjectProperty<String>("");
        addComponent(new Label(labelProperty, ContentMode.HTML));
        updateLabelProperty();
        addComponent(new GoToMainViewLink());
        addComponent(logoutLink);
        logoutLink.updateVisibility();

        registerWithEventbus();
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("Created new instance of ProfileView");
    }

    private void updateLabelProperty() {
        labelProperty.setValue("<h1>"
                + (RootUI.getCurrent().getCurrentUser() == null ? "" :
                "FullName: " +
                RootUI.getCurrent().getCurrentUser().getFullName())
                + "'s Profile</h1>... not much to see here, though.");
    }

    @Override
    public void enter(ViewChangeEvent event) {
        LOG.info("Entering profile view");
    }

    @Subscribe
    public void userLoggedIn(UserLoggedInEvent event) {
        logoutLink.updateVisibility();
        updateLabelProperty();
    }
}
