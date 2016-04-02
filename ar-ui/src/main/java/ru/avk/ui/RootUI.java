package ru.avk.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.avk.domain.User;
import ru.avk.service.VaadinUIService;
import ru.avk.service.impl.VaadinUIServiceImpl;
import ru.avk.ui.events.LogoutEvent;
import ru.avk.ui.events.NavigationEvent;
import ru.avk.ui.security.SecurityErrorHandler;
import ru.avk.ui.security.ViewAccessDecisionManager;
import ru.avk.ui.views.ErrorView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;

@SpringUI
@Theme("valo")
@PreserveOnRefresh
public class RootUI extends UI {

    private static final Logger LOG = LoggerFactory.getLogger(RootUI.class);

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private ViewAccessDecisionManager viewAccessDecisionManager;

    private EventBus eventbus;

    @PostConstruct
    private void initEventbus() {
        eventbus = new EventBus("main");
        eventbus.register(this);
    }

    public EventBus getEventbus() {
        return eventbus;
    }

    public static RootUI getCurrent() {
        return (RootUI) UI.getCurrent();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildNavigator();
        VaadinSession.getCurrent().setErrorHandler(new SecurityErrorHandler(eventbus, getNavigator()));

        viewAccessDecisionManager.checkAccessRestrictionForRequestedView(getNavigator());

        Page.getCurrent().setTitle("Vaadin and Spring Security Demo");
    }

    private void buildNavigator() {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(ErrorView.class);
        setNavigator(navigator);
    }

    public User getCurrentUser() {
        if (isUserAnonymous()) {
            return null;
        } else {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }

    public boolean isUserAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }

    @Subscribe
    public void userLoggedOut(LogoutEvent event) throws ServletException {
        ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest().logout();
        VaadinSession.getCurrent().close();
    }

    @Subscribe
    public void handleNavigation(NavigationEvent event) {
        getNavigator().navigateTo(event.getTarget());
    }

}
