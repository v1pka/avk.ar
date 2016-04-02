package ru.avk.service.impl;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avk.service.VaadinUIService;
import ru.avk.ui.RootUI;
import ru.avk.ui.events.NavigationEvent;

@Service
public class VaadinUIServiceImpl implements VaadinUIService {

    @Override
    public void postNavigationEvent(Object source, String target) {
        RootUI.getCurrent().getEventbus().post(new NavigationEvent(source, target));
    }

    @Override
    public boolean isUserAnonymous() {
        return RootUI.getCurrent().isUserAnonymous();
    }
}
