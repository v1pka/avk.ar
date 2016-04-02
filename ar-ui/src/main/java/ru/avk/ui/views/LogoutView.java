package ru.avk.ui.views;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import ru.avk.ui.RootUI;
import ru.avk.ui.events.LogoutEvent;

@SpringView(name = LogoutView.NAME)
public class LogoutView extends Navigator.EmptyView {

    private EventBus eventBus = RootUI.getCurrent().getEventbus();

    public static final String NAME = "logout";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        RootUI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
        eventBus.post(new LogoutEvent(this));

    }
}
