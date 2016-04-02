package ru.avk.ui.views;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.avk.ui.RootUI;

import javax.annotation.PreDestroy;

public abstract class AbstractView extends Panel implements View {

	private Logger LOG = LoggerFactory.getLogger(AbstractView.class);

	private VerticalLayout layout;

	public AbstractView() {
		setSizeFull();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
	}

	public void addComponent(Component c) {
		layout.addComponent(c);
	}

	protected void registerWithEventbus() {
		RootUI.getCurrent().getEventbus().register(this);

	}

	@PreDestroy
	public void destroy() {
		RootUI.getCurrent().getEventbus().register(this);

	}
}
