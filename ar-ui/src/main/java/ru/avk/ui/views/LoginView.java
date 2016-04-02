package ru.avk.ui.views;

import com.google.common.eventbus.EventBus;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.avk.security.UserAuthenticationService;
import ru.avk.ui.RootUI;
import ru.avk.ui.components.GoToMainViewLink;
import ru.avk.ui.events.NavigationEvent;

@SpringView(name = LoginView.NAME)
public class LoginView extends AbstractView implements Button.ClickListener {

    public final static String NAME = "login";

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    private String forwardTo;
    private TextField nameTF;
    private PasswordField passwordTF;

    public LoginView() {
        addComponent(new Label(
                "Please enter your credentials:"));
        nameTF = new TextField();
        nameTF.setRequired(true);
        nameTF.focus();

        passwordTF = new PasswordField();
        passwordTF.setRequired(true);

        addComponent(nameTF);
        addComponent(passwordTF);

        Button loginButton = new Button("Login");
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginButton.addClickListener(this);
        loginButton.setIcon(FontAwesome.SIGN_IN);
        addComponent(loginButton);

        addComponent(new GoToMainViewLink());
    }

    @Override
    public void enter(ViewChangeEvent event) {
        forwardTo = event.getParameters();
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (nameTF.isValid() && passwordTF.isValid()) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(nameTF.getValue(), passwordTF.getValue());
            if (userAuthenticationService.loginUser(authentication)) {
                RootUI.getCurrent().getEventbus().post(new NavigationEvent(this, forwardTo));
            } else {
                passwordTF.setValue("");
            }
        } else {
            if (nameTF.isEmpty()) {
                nameTF.setRequiredError("Please enter your username");
            }
            if (passwordTF.isEmpty()) {
                passwordTF.setRequiredError("Please enter your password");
            }
        }
    }

    public static String loginPathForRequestedView(String requestedViewName) {
        return NAME + "/" + requestedViewName;
    }
}
