package com.client.panels;

import com.client.events.UserLoggedInEvent;
import com.client.events.UserLoggedInHandler;
import com.client.service.ClientSessionService;
import com.client.service.ClientSessionServiceAsync;
import com.client.widgets.HourSettingsWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.shared.model.HourCostModel;
import com.shared.model.SessionPseudoName;
import com.shared.model.User;
import com.shared.utils.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/9/16
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsPanel extends SplitLayoutPanel {
    private SimpleEventBus simpleEventBus;
    private ClientSessionServiceAsync clientSessionService = GWT.create(ClientSessionService.class);
//    private FormPanel formPanel = new FormPanel();
//    VerticalPanel mainPanel = new VerticalPanel();
    public SettingsPanel(SimpleEventBus simpleEventBus) {
        this.simpleEventBus = simpleEventBus;
        setWidth("100%");
        setHeight("300px");
//        add(new CheckBox("Some check"));

        VerticalPanel pseudoNamesSettingsPanel = new VerticalPanel();
        pseudoNamesSettingsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        pseudoNamesSettingsPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        Label namesLabel = new Label("Настройки псевдонимов:");
        pseudoNamesSettingsPanel.add(namesLabel);
        HorizontalPanel addNamePanel = new HorizontalPanel();
        Label newNameLabel = new Label("Новое имя: ");
        final TextBox namesTextBox = new TextBox();
        addNamePanel.add(newNameLabel);
        addNamePanel.add(namesTextBox);
        pseudoNamesSettingsPanel.add(addNamePanel);
        HorizontalPanel existingNamesPanel = new HorizontalPanel();
        Label existingNamesLabel = new Label("Существующие имена: ");
        final ListBox namesBox = new ListBox();
        namesBox.setWidth("200px");
        existingNamesPanel.add(existingNamesLabel);
        existingNamesPanel.add(namesBox);
        pseudoNamesSettingsPanel.add(existingNamesPanel);
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

//        add(namesBox);
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.add(addNameButton);
        buttonsPanel.add(removeNameButton);
        pseudoNamesSettingsPanel.add(buttonsPanel);
        pseudoNamesSettingsPanel.setSpacing(10);
//        add(pseudoNamesSettingsPanel);
        addWest(pseudoNamesSettingsPanel, 350);

//        add(new Label("Время:"));

        VerticalPanel costSettingsPanel = new VerticalPanel();
        costSettingsPanel.setSpacing(10);
        Label costSettingsLabel = new Label("Настройки стоимости:");
        costSettingsPanel.add(costSettingsLabel);

        VerticalPanel hoursCostPanel = new VerticalPanel();
        Map<Long, HourCostModel> hourCostModelMap = new HashMap<Long, HourCostModel>();
        HourCostModel hourCostModel = new HourCostModel();
        hourCostModel.setHourOrder(1);
        hourCostModel.setCostPerMinute(5l);
        hourCostModel.setCostPerHour(2501);
        hourCostModelMap.put(hourCostModel.getHourOrder(), hourCostModel);
        hoursCostPanel.add(new HourSettingsWidget(hourCostModelMap));
        add(hoursCostPanel);
        final TextBox firstPartLengthTextBox = new TextBox();
        firstPartLengthTextBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                String input = firstPartLengthTextBox.getText();
                if (!input.matches("[0-9]*")) {
                    // show some error
                    return;
                }
                // do your thang
            }
        });
//        add(firstPartLengthTextBox);
//        UserUtils.init();

        final TextBox firstPartSumAmountTextBox = new TextBox();
        firstPartSumAmountTextBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                String input = firstPartSumAmountTextBox.getText();
                if (!input.matches("[0-9]*")) {
                    // show some error
                    return;
                }
                // do your thang
            }
        });
//        clientSessionService.getCurrentUser();
//        add(firstPartSumAmountTextBox);

        final TextBox maxSessionLengthTextBox = new TextBox();
        firstPartSumAmountTextBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                String input = maxSessionLengthTextBox.getText();
                if (!input.matches("[0-9]*")) {
                    // show some error
                    return;
                }
                // do your thang
            }
        });
//        clientSessionService.getCurrentUser();
//        add(maxSessionLengthTextBox);

        Button saveButton = new Button("Сохранить");
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                UserUtils.INSTANCE.getCurrentUser().getSettings().setFirstPartLength(Long.valueOf(firstPartLengthTextBox.getValue()));
                UserUtils.INSTANCE.getCurrentUser().getSettings().setFirstPartSumAmount(Long.valueOf(firstPartSumAmountTextBox.getValue()));
                UserUtils.INSTANCE.getCurrentUser().getSettings().setMaxSessionLength(Long.valueOf(maxSessionLengthTextBox.getValue()));
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

//        add(saveButton);

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
                                firstPartLengthTextBox.setValue(String.valueOf(result.getSettings().getFirstPartLength()));
                                firstPartSumAmountTextBox.setValue(String.valueOf(result.getSettings().getFirstPartSumAmount()));
                                maxSessionLengthTextBox.setValue(result.getSettings().getMaxSessionLength() == null? "0" : String.valueOf(result.getSettings().getMaxSessionLength()));
                            }
                        });
            }
        });

        firstPartLengthTextBox.setValue(String.valueOf(UserUtils.INSTANCE.getCurrentUser().getSettings().getFirstPartLength()));
        firstPartSumAmountTextBox.setValue(String.valueOf(UserUtils.INSTANCE.getCurrentUser().getSettings().getFirstPartSumAmount()));
        maxSessionLengthTextBox.setValue(String.valueOf(UserUtils.INSTANCE.getCurrentUser().getSettings().getMaxSessionLength()));
    }
}
