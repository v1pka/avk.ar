package ru.avk.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import ru.avk.domain.User;
import ru.avk.service.UserService;


@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout {

	private final UserService repository;

	private User user;

	TextField fullName = new TextField("Full Name");
	TextField userName = new TextField("Username");

	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public UserEditor(UserService userService) {
		this.repository = userService;

		addComponents(fullName, userName, actions);

		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		save.addClickListener(e -> userService.save(user));
		delete.addClickListener(e -> userService.delete(user));
		cancel.addClickListener(e -> editUser(user));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editUser(User c) {
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			user = repository.findOne(c.getId());
		}
		else {
			user = c;
		}
		cancel.setVisible(persisted);

		BeanFieldGroup.bindFieldsUnbuffered(user, this);

		setVisible(true);

		save.focus();

		userName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
