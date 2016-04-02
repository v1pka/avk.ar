package ru.avk.ui.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import ru.avk.ui.RootUI;

@SpringView(name = "")
public class RedirectToMainView extends Navigator.EmptyView {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        RootUI.getCurrent().getNavigator().navigateTo(MainView.NAME);
    }
}
