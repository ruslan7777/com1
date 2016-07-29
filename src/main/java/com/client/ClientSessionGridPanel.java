package com.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by dmitry on 26.07.16.
 */
public class ClientSessionGridPanel extends HorizontalPanel {
  public ClientSessionGridPanel() {
    setHeight("100%");
    setWidth("100%");
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.setHeight("100%");
    verticalPanel.setWidth("100%");

//    add(clientSessionGrid);
    final DataGrid<ClientSession> clientSessionDataGrid = new DataGrid<ClientSession>(10, new ProvidesKey<ClientSession>() {
      @Override
      public Object getKey(ClientSession item) {
        return ((ClientSession)item).getId();
      }
    });
    clientSessionDataGrid.insertColumn(0, new Column<ClientSession, String>(new AbstractCell<String>() {
      @Override
      public void render(Context context, final String value, SafeHtmlBuilder sb) {
        sb.append(new SafeHtml() {
          @Override
          public String asString() {
            return value;
          }
        });
      }
    }) {
      @Override
      public String getValue(ClientSession object) {
        return String.valueOf(object.getId());
      }
    }, new TextHeader("Id"));


    final List<String> sessionPseudoNames = new ArrayList<>();
    sessionPseudoNames.addAll(Arrays.asList("GREEN", "YELLOW", "RED"));
    SelectionCell categoryCell = new SelectionCell(sessionPseudoNames);
    Column<ClientSession, String> categoryColumn = new Column<ClientSession, String>(categoryCell) {
      @Override
      public String getValue(ClientSession object) {
        return object.getSessionPseudoName().getName();
      }
    };
    clientSessionDataGrid.setColumnWidth(categoryColumn, 200, Style.Unit.PX);
//    clientSessionDataGrid.addColumn(categoryColumn, "Псевдоним");
    categoryColumn.setFieldUpdater(new FieldUpdater<ClientSession, String>() {
      @Override
      public void update(int index, ClientSession object, String value) {
        for (String category : sessionPseudoNames) {
          if (category.equals(value)) {
            object.setSessionPseudoName(new SessionPseudoName(category));
          }
        }
//        ContactDatabase.get().refreshDisplays();
      }
    });
//    dataGrid.setColumnWidth(categoryColumn, 130, Unit.PX);
    clientSessionDataGrid.insertColumn(1, categoryColumn, new TextHeader("Псевдоним"));
    ClientSession clientSession = new ClientSession(new Date().getTime(), new Date().getTime(), false);
    clientSession.setId(0);
    clientSession.setSessionPseudoName(new SessionPseudoName("GREEN"));
    clientSessionDataGrid.setRowData(0, Arrays.asList(clientSession));
    clientSessionDataGrid.setHeight("400px");
    clientSessionDataGrid.setWidth("100%");
    verticalPanel.add(clientSessionDataGrid);
    Button addButton = new Button("Add Row");
    addButton.setHeight("30px");
    addButton.setWidth("130px");
    addButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        ClientSession clientSession = new ClientSession(new Date().getTime(), new Date().getTime(), false);
        clientSession.setId(1);
        clientSession.setSessionPseudoName(new SessionPseudoName("GREEN"));
        clientSessionDataGrid.setRowData(clientSessionDataGrid.getVisibleItems().size(), Arrays.asList(clientSession));
      }
    });
    verticalPanel.add(addButton);
    add(verticalPanel);
//    clientSessionGrid.setRowData(0, Collections.singletonList(new ClientSession(System.currentTimeMillis(),
//            System.currentTimeMillis(), false)));
  }
}
