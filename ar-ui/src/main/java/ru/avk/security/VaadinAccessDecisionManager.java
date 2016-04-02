package ru.avk.security;

import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.avk.service.VaadinUIService;
import ru.avk.ui.views.AccessDeniedView;
import ru.avk.ui.views.LoginView;

import java.util.Collection;

@Component
public class VaadinAccessDecisionManager implements AccessDecisionManager {

    private Logger LOG = LoggerFactory.getLogger(VaadinAccessDecisionManager.class);

    private AccessDecisionManager delegate;

    @Autowired
    private VaadinUIService vaadinUIService;

    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        this.delegate = accessDecisionManager;
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {
        try {
            if (configAttributes == null) {
                // no action if an object with no access restrictions is visited
                return;
            }
            // delegate access decision itself to super class
            delegate.decide(authentication, object, configAttributes);
        } catch (AccessDeniedException adExc) {
            // we handle security exceptions in the Vaadin way, i. e. we publish appropriate events on the event bus instead
            // of redirecting to some error page (remember that we've a single-page application)
            if (vaadinUIService.isUserAnonymous()) {
                vaadinUIService.postNavigationEvent(this, LoginView.loginPathForRequestedView(UI.getCurrent().getNavigator().getState()));
                throw adExc;
            } else {
                // if she is logged in but doesn't have adequate access rights, send her to the access denied view
                vaadinUIService.postNavigationEvent(this, AccessDeniedView.NAME);
                throw adExc;
            }
        } catch (Exception exc) {
            LOG.info("Exception after authentication decision: {}", exc.getClass().getName());
            exc.printStackTrace();
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return delegate.supports(attribute);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return delegate.supports(clazz);
    }
}
