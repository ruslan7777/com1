package com.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * Created by Dimon on 19.07.2016.
 */
public class MainTabPanel extends TabLayoutPanel {
  /**
   * Creates an empty tab panel.
   *
   * @param barHeight the size of the tab bar
   * @param barUnit   the unit in which the tab bar size is specified
   */
  public MainTabPanel(double barHeight, Style.Unit barUnit) {
    super(barHeight, barUnit);
    // Create a tab panel
//    TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Style.Unit.EM);
    setAnimationDuration(300);
//    getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);
//    getElement().getStyle().setMarginLeft(300.0, Style.Unit.PX);
    setHeight("500px");
    setWidth("500px");
    // Add a home tab
    String[] tabTitles = {"Сессии", "Настройки", "Отчеты"};
    ClientSessionGridPanel clientSessionGridPanel = new ClientSessionGridPanel();
    Label label1 = new Label("This is contents of TAB 1");
    label1.setSize("100", "100");
    add(clientSessionGridPanel, tabTitles[0]);

    // Add a tab with an image
    SimplePanel imageContainer = new SimplePanel();
    imageContainer.setWidget(new Button("dfdfdf"));
    add(imageContainer, tabTitles[1]);

    // Add a tab
    HTML moreInfo = new HTML("some html");
    add(moreInfo, tabTitles[2]);

    // Return the content
    selectTab(0);
    ensureDebugId("cwTabPanel");

  }

}
