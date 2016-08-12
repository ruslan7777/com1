package com.client;

import com.client.panels.ReportsPanel;
import com.client.panels.SettingsPanel;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * Created by Dimon on 19.07.2016.
 */
public class MainTabPanel extends TabLayoutPanel {
    private SimpleEventBus simpleEventBus;
  /**
   * Creates an empty tab panel.
   *
   * @param barHeight the size of the tab bar
   * @param barUnit   the unit in which the tab bar size is specified
   */
  public MainTabPanel(double barHeight, Style.Unit barUnit, final SimpleEventBus eventBus) {
    super(barHeight, barUnit);
      this.simpleEventBus = eventBus;
    // Create a tab panel
//    TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Style.Unit.EM);
    setAnimationDuration(301);
//    getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);
//    getElement().getStyle().setMarginLeft(300.0, Style.Unit.PX);
    setHeight("600px");
    setWidth("100%");
//    setHeight("100%");
//    setWidth("100%");
    // Add a home tab
    String[] tabTitles = {"Сессии", "Настройки", "Отчеты"};
    ClientSessionGridPanel clientSessionGridPanel = new ClientSessionGridPanel(simpleEventBus);
    add(clientSessionGridPanel, tabTitles[0]);

    // Add a tab with an image
//    SimplePanel imageContainer = new SimplePanel();
//    imageContainer.setWidget(new Button("dfdfdf"));
    add(new SettingsPanel(), tabTitles[1]);

    // Add a tab
//    HTML moreInfo = new HTML("some html");
    add(new ReportsPanel(), tabTitles[2]);

    // Return the content
    selectTab(0);
//    ensureDebugId("cwTabPanel");

  }

}
