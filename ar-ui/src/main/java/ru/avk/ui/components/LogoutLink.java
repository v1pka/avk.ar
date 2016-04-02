package ru.avk.ui.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import ru.avk.ui.RootUI;
import ru.avk.ui.views.LogoutView;

public class LogoutLink extends CustomComponent {

	public LogoutLink() {
		Link logoutLink = new Link("Logout", new ExternalResource("#!" + LogoutView.NAME));
		logoutLink.setIcon(FontAwesome.SIGN_OUT);
		setCompositionRoot(logoutLink);
	}

	public void updateVisibility() {
		setVisible(!RootUI.getCurrent().isUserAnonymous());
	}
}
