package com.client.panels;

import com.client.events.UserLoggedInEvent;
import com.client.events.UserLoggedInHandler;
import com.client.service.ClientSessionService;
import com.client.service.ClientSessionServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.shared.model.SessionPseudoName;
import com.shared.model.SettingsHolder;
import com.shared.model.User;
import com.shared.utils.UserUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/9/16
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsPanel extends VerticalPanel {
    private SimpleEventBus simpleEventBus;
    private ClientSessionServiceAsync clientSessionService = GWT.create(ClientSessionService.class);
//    private FormPanel formPanel = new FormPanel();
//    VerticalPanel mainPanel = new VerticalPanel();
    public SettingsPanel(SimpleEventBus simpleEventBus) {
        this.simpleEventBus = simpleEventBus;
        setWidth("100%");
        setHeight("300px");
//        add(new CheckBox("Some check"));

        Label namesLabel = new Label("Имена:");
        add(namesLabel);
        final TextBox namesTextBox = new TextBox();
        add(namesTextBox);
        final ListBox namesBox = new ListBox();
        namesBox.setWidth("200px");
        clientSessionService.getAllPseudoNames(new AsyncCallback<List<SessionPseudoName>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onSuccess(List<SessionPseudoName> strings) {
                for (SessionPseudoName name : strings) {
                    namesBox.addItem(name.getName());
                }//To change body of implemented methods use File | Settings | File Templates.
            }
        });
        Button addNameButton = new Button("Добавить имя");
        addNameButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String namesTextBoxValue = namesTextBox.getValue();
                if (namesTextBoxValue == null || namesTextBoxValue.isEmpty()) {
                    Window.alert("Нельзя добавить пустое имя");
                    return;
                }
                if (isNameAlreadyPresent()) {
                    Window.alert("Такое имя уже есть в базе");
                    return;
                }
                clientSessionService.addName(new SessionPseudoName(namesTextBoxValue), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(Void result) {
                        namesTextBox.setText("");
                        namesBox.addItem(namesTextBoxValue);
                    }
                });
            }

            private boolean isNameAlreadyPresent() {
                for (int i = 0; i < namesBox.getItemCount(); i++) {
                    if (namesTextBox.getValue().equalsIgnoreCase(namesBox.getItemText(i))) {
                        return true;
                    }
                }
                return false;
            }

        });
        Button removeNameButton = new Button("Удалить имя");
        removeNameButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String selectedName = namesBox.getSelectedValue();
                clientSessionService.getFreePseudoNames(new AsyncCallback<List<SessionPseudoName>>() {
                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(List<SessionPseudoName> result) {
                        if (!result.contains(new SessionPseudoName(selectedName))) {
                            Window.alert("Это имя используется");
                        } else {
                            clientSessionService.removeName(new SessionPseudoName(selectedName), new AsyncCallback<Void>() {
                                @Override
                                public void onFailure(Throwable caught) {

                                }

                                @Override
                                public void onSuccess(Void result) {
                                    namesTextBox.setText("");
                                    namesBox.removeItem(namesBox.getSelectedIndex());
                                }
                            });
                        }
                    }
                });
            }
        });

        add(namesBox);
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.add(addNameButton);
        buttonsPanel.add(removeNameButton);
        add(buttonsPanel);

        add(new Label("Время:"));
        final TextBox firstPartLength = new TextBox();
        firstPartLength.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                String input = firstPartLength.getText();
                if (!input.matches("[0-9]*")) {
                    // show some error
                    return;
                }
                // do your thang
            }
        });
        add(firstPartLength);
        UserUtils.init();

        final TextBox firstPartSumAmount = new TextBox();
        firstPartSumAmount.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                String input = firstPartSumAmount.getText();
                if (!input.matches("[0-9]*")) {
                    // show some error
                    return;
                }
                // do your thang
            }
        });
//        clientSessionService.getCurrentUser();
        add(firstPartSumAmount);

        Button saveButton = new Button("Сохранить");
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                UserUtils.INSTANCE.getCurrentUser().getSettings().setFirstPartLength(Long.valueOf(firstPartLength.getValue()));
                UserUtils.INSTANCE.getCurrentUser().getSettings().setFirstPartSumAmount(Long.valueOf(firstPartSumAmount.getValue()));
                clientSessionService.saveUser(UserUtils.INSTANCE.getCurrentUser(), new AsyncCallback<User>() {
                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(User result) {
                        UserUtils.INSTANCE.setCurrentUser(result);
                        DecoratedPopupPanel decoratedPopupPanel = new DecoratedPopupPanel();
                        decoratedPopupPanel.center();
                        decoratedPopupPanel.setAutoHideEnabled(true);
                        decoratedPopupPanel.setWidget(new HTML(result.getUserName() + " обновлен"));
                        decoratedPopupPanel.show();
                    }
                });
            }
        });

        add(saveButton);

        simpleEventBus.addHandler(UserLoggedInEvent.TYPE, new UserLoggedInHandler() {
            @Override
            public void userIsLoggedIn(UserLoggedInEvent userLoggedInEvent) {
                clientSessionService.getCurrentUser(userLoggedInEvent.getUserName(), userLoggedInEvent.getUserPassword(),
                        new AsyncCallback<User>() {
                            @Override
                            public void onFailure(Throwable caught) {

                            }

                            @Override
                            public void onSuccess(User result) {
                                UserUtils.INSTANCE.setCurrentUser(result);
                                firstPartLength.setValue(String.valueOf(result.getSettings().getFirstPartLength()));
                                firstPartSumAmount.setValue(String.valueOf(result.getSettings().getFirstPartSumAmount()));
                            }
                        });
            }
        });

    }
}
