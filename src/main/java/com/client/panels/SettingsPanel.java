package com.client.panels;

import com.client.service.ClientSessionService;
import com.client.service.ClientSessionServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

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
        add(new CheckBox("Some check"));
        add(new TextBox());
        final ListBox namesBox = new ListBox();
        clientSessionService.getPseudoNames(new AsyncCallback<List<String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onSuccess(List<String> strings) {
                for (String string : strings) {
                    namesBox.addItem(string);
                }//To change body of implemented methods use File | Settings | File Templates.
            }
        });
        Button addNameButton = new Button("Add");
        Button removeNameButton = new Button("Remove");

        add(namesBox);
        add(addNameButton);
        add(removeNameButton);
//        mainPanel.add(formPanel);
    }
}
