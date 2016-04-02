package ru.avk.ui.views;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import ru.avk.service.UserService;

@Secured("ROLE_ADMIN")
@SpringView(name = AdminView.NAME)
public class AdminView extends AbstractView {

    public static final String NAME = "admin";

    @Autowired
    public AdminView(UserService userService) {
        addComponent(new UserView(userService, new UserEditor(userService)));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
