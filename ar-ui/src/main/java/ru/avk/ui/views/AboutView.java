package ru.avk.ui.views;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = AboutView.NAME)
public class AboutView extends AbstractView {

    public static final String NAME = "about";

    public AboutView() {
        addComponent(new Label("<h1>About AR</h1>", ContentMode.HTML));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
