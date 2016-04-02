package ru.avk.ui.security;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import ru.avk.ui.RootUI;
import ru.avk.ui.events.NavigationEvent;
import ru.avk.ui.views.AccessDeniedView;
import ru.avk.ui.views.LoginView;


public class SecurityErrorHandler implements ErrorHandler {

    private static Logger LOG = LoggerFactory.getLogger(SecurityErrorHandler.class);
    private EventBus eventbus;
    private Navigator navigator;

    public SecurityErrorHandler(EventBus eventbus, Navigator navigator) {
        this.eventbus = eventbus;
        this.navigator = navigator;
    }

    @Override
    public void error(ErrorEvent event) {
        LOG.error("Error handler caught exception {}", event.getThrowable().getClass().getName());
        if (event.getThrowable() instanceof AccessDeniedException || event.getThrowable().getCause() instanceof AccessDeniedException) {
            if (RootUI.getCurrent().isUserAnonymous() && !navigator.getState().startsWith(LoginView.NAME)) {
                eventbus.post(new NavigationEvent(this, LoginView.loginPathForRequestedView(navigator.getState())));
            } else if (!RootUI.getCurrent().isUserAnonymous()) {
                eventbus.post(new NavigationEvent(this, AccessDeniedView.NAME));
            }
        } else {
            // handle other exceptions a bit more graciously than this
            event.getThrowable().printStackTrace();
        }
    }
}
