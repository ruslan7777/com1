package com.client.panels;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/9/16
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportsPanel extends VerticalPanel{
    public ReportsPanel() {
        setHeight("400px");
        setWidth("100%");
        PieChart pie = new PieChart(createTable(), createOptions());
        add(pie);
    }
    private Options createOptions() {
        Options options = Options.create();
        options.setWidth(400);
        options.setHeight(240);
        options.set3D(true);
        options.setTitle("My Daily Activities");
        return options;
    }

    private AbstractDataTable createTable() {
        DataTable data = DataTable.create();
        data.addColumn(ColumnType.STRING, "Task");
        data.addColumn(ColumnType.NUMBER, "Hours per Day");
        data.addRows(2);
        data.setValue(0, 0, "Work");
        data.setValue(0, 1, 14);
        data.setValue(1, 0, "Sleep");
        data.setValue(1, 1, 10);
        return data;
    }
}
