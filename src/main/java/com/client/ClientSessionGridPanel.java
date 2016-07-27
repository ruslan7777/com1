package com.client;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.shared.model.ClientSession;

import java.util.Collections;

/**
 * Created by dmitry on 26.07.16.
 */
public class ClientSessionGridPanel extends HorizontalPanel {
  public ClientSessionGridPanel() {
    Grid clientSessionGrid = new Grid(4, 4);
    setSize("400", "400");
    clientSessionGrid.setSize("400", "400");
    // Add images to the grid
    int numRows = clientSessionGrid.getRowCount();
    int numColumns = clientSessionGrid.getColumnCount();
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numColumns; col++) {
        clientSessionGrid.setWidget(row, col, new Label("aaaa"));
      }
    }
    clientSessionGrid.setWidth("100%");
    clientSessionGrid.setHeight("100%");
    add(clientSessionGrid);
//    clientSessionGrid.setRowData(0, Collections.singletonList(new ClientSession(System.currentTimeMillis(),
//            System.currentTimeMillis(), false)));
  }
}
