package com.client.panels;

import com.client.service.ClientSessionService;
import com.client.service.ClientSessionServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.shared.model.SessionPseudoName;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/9/16
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsPanel extends VerticalPanel {
    private ClientSessionServiceAsync clientSessionService = GWT.create(ClientSessionService.class);
//    private FormPanel formPanel = new FormPanel();
//    VerticalPanel mainPanel = new VerticalPanel();
    public SettingsPanel() {
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
        TextBox firstPartTimeValue = new TextBox();
        add(firstPartTimeValue);
    }
}
