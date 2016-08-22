package com.client.widgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.shared.model.HourCostModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitry on 22.08.16.
 */
public class HourSettingsWidget extends VerticalPanel {
  private ListBox hoursOrderListBox = new ListBox();
  private Map<Long, HourCostModel> hourCostModelMap;
  public HourSettingsWidget(final Map<Long, HourCostModel> hourCostModelMap) {
    this.hourCostModelMap = hourCostModelMap;
    setWidth("200px");
    setHeight("200px");

    for (Long key : hourCostModelMap.keySet()) {
      hoursOrderListBox.addItem(String.valueOf(key));
    }
    hoursOrderListBox.setSelectedIndex(0);


    HourCostModel firstHourCostModel = hourCostModelMap.get(1l);
    add(new Label("Час № "));
    add(hoursOrderListBox);
    final HorizontalPanel costPerMinutePanel = new HorizontalPanel();
    costPerMinutePanel.add(new Label("Стоимость минуты:"));
    final TextBox costPerMinuteTextBox = new AntiTextBox();
    costPerMinuteTextBox.setValue(String.valueOf(firstHourCostModel.getCostPerMinute()));
    costPerMinutePanel.add(costPerMinuteTextBox);
    HorizontalPanel hourCostPanel = new HorizontalPanel();
    hourCostPanel.add(new Label("Стоимость часа:"));
    final TextBox hourCostTextBox = new AntiTextBox();
    hourCostTextBox.setValue(String.valueOf(firstHourCostModel.getCostPerHour()));
    hourCostPanel.add(hourCostTextBox);
    add(costPerMinutePanel);
    add(hourCostPanel);
    Button addHourButton = new Button("Добавить час");
    addHourButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        HourCostModel hourCostModel = new HourCostModel();
        HourCostModel existingLastModel = hourCostModelMap.get((long) hourCostModelMap.keySet().size());
        if (existingLastModel != null) {
          hourCostModel.setCostPerHour(existingLastModel.getCostPerHour());
          hourCostModel.setCostPerMinute(existingLastModel.getCostPerMinute());
          hourCostModel.setHourOrder(existingLastModel.getHourOrder() + 1);
        } else {
          hourCostModel.setCostPerHour(0l);
          hourCostModel.setCostPerMinute(0l);
          hourCostModel.setHourOrder(1);
        }
        hourCostModelMap.put(hourCostModel.getHourOrder(), hourCostModel);
        hoursOrderListBox.addItem(String.valueOf(hourCostModel.getHourOrder()));
        hoursOrderListBox.setSelectedIndex((int) hourCostModel.getHourOrder() - 1);

        costPerMinuteTextBox.setValue(String.valueOf(hourCostModel.getCostPerMinute()));
        hourCostTextBox.setValue(String.valueOf(hourCostModel.getCostPerHour()));
      }
    });
    costPerMinuteTextBox.addKeyUpHandler(new KeyUpHandler() {
      @Override
      public void onKeyUp(KeyUpEvent event) {
        HourCostModel hourCostModel = hourCostModelMap.get(Long.valueOf(hoursOrderListBox.getSelectedValue()));
        if (hourCostModel != null) {
          hourCostModel.setCostPerMinute(Long.parseLong(costPerMinuteTextBox.getValue()));
          hourCostModel.setCostPerHour(Long.parseLong(hourCostTextBox.getValue()));
        }
      }
    });
    hourCostTextBox.addKeyUpHandler(new KeyUpHandler() {
      @Override
      public void onKeyUp(KeyUpEvent event) {
        HourCostModel hourCostModel = hourCostModelMap.get(Long.valueOf(hoursOrderListBox.getSelectedValue()));
        if (hourCostModel != null) {
          hourCostModel.setCostPerMinute(Long.parseLong(costPerMinuteTextBox.getValue()));
          hourCostModel.setCostPerHour(Long.parseLong(hourCostTextBox.getValue()));
        }
      }
    });
    hoursOrderListBox.addChangeHandler(new ChangeHandler() {
      @Override
      public void onChange(ChangeEvent event) {
        HourCostModel existingHourCostModel = hourCostModelMap.get(((long) hoursOrderListBox.getSelectedIndex()));
        if (existingHourCostModel != null) {
          costPerMinuteTextBox.setValue(String.valueOf(existingHourCostModel.getCostPerMinute()));
          hourCostTextBox.setValue(String.valueOf(existingHourCostModel.getCostPerHour()));
        }
        String s = "dfdf";
      }
    });
    add(addHourButton);
    Button saveHourSettingsButton = new Button("Сохранить настройки стоимости");
    add(saveHourSettingsButton);
  }
}
