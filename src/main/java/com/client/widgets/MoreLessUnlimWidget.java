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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.shared.model.ClientSession;
import com.shared.model.HourCostModel;
import com.shared.model.MoreLessUnlimModel;
import com.shared.model.SettingsHolder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dmitry on 22.08.16.
 */
public class MoreLessUnlimWidget extends ScrollPanel {
  private final TextBox costPerMinuteTextBox = new AntiTextBox();
  final TextBox unlimCostTextBox = new AntiMoneyTextBox();
  private VerticalPanel hoursCostsPanel;
  final Button addHourButton = new Button("Добавить стоимость");

  public MoreLessUnlimWidget() {
    setWidth("100%");
    setHeight("100%");
//    setSpacing(3);

    VerticalPanel mainPanel = new VerticalPanel();
    mainPanel.setSize("100%", "100%");
    hoursCostsPanel = new VerticalPanel();
    hoursCostsPanel.setSize("400px", "150px");
    hoursCostsPanel.setSpacing(5);
    mainPanel.add(hoursCostsPanel);
    mainPanel.add(new Label("Стоимость минуты, коп.: "));
    mainPanel.add(costPerMinuteTextBox);
    mainPanel.add(new Label("Стоимость безлимита, руб.: "));
    mainPanel.add(unlimCostTextBox);
    setAddButtonEnabled(addHourButton);
    addHourButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        setAddButtonEnabled(addHourButton);
        final HorizontalPanel numberHoursCostPanel = new HorizontalPanel();
        numberHoursCostPanel.setSpacing(5);
        numberHoursCostPanel.setWidth("250px");
        numberHoursCostPanel.setHeight("50px");
        final TextBox numberOfHoursTextBox = new AntiTextBox();
        numberOfHoursTextBox.setName("numberOfHoursTextBox");
        final TextBox hoursCostTextBox = new AntiMoneyTextBox();
        hoursCostTextBox.setName("hoursCostTextBox");
        numberOfHoursTextBox.setName("numberOfHoursTextBox");
        numberHoursCostPanel.add(new Label("За "));
        numberOfHoursTextBox.setWidth("40px");
        numberHoursCostPanel.add(numberOfHoursTextBox);
        numberHoursCostPanel.add(new Label("часа - "));
        hoursCostTextBox.setWidth("30px");
        numberHoursCostPanel.add(hoursCostTextBox);
        numberHoursCostPanel.add(new Label("руб."));
        Button removeButton = new Button("Удалить");
        removeButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
            numberHoursCostPanel.removeFromParent();
            setAddButtonEnabled(addHourButton);
          }
        });
        numberHoursCostPanel.add(removeButton);
        if (hoursCostsPanel.getWidgetCount() < 6) {
          hoursCostsPanel.add(numberHoursCostPanel);
        }
      }
    });

    mainPanel.add(addHourButton);
    add(mainPanel);
//    setAlwaysShowScrollBars(true);
  }

  public void showSettings(List<MoreLessUnlimModel> moreLessUnlimModelList) {
    for (MoreLessUnlimModel moreLessUnlimModel : moreLessUnlimModelList) {
      final HorizontalPanel numberHoursCostPanel = new HorizontalPanel();
      numberHoursCostPanel.setSpacing(5);
      numberHoursCostPanel.setWidth("250px");
      numberHoursCostPanel.setHeight("50px");
      final TextBox numberOfHoursTextBox = new AntiTextBox();
      numberOfHoursTextBox.setName("numberOfHoursTextBox");
      final TextBox hoursCostTextBox = new AntiMoneyTextBox();
      hoursCostTextBox.setName("hoursCostTextBox");
      numberHoursCostPanel.add(new Label("За "));
      numberOfHoursTextBox.setWidth("40px");
      numberOfHoursTextBox.setValue(moreLessUnlimModel.getNumberOfHours() != 0 ? String.valueOf(moreLessUnlimModel.getNumberOfHours()) : null);
      numberHoursCostPanel.add(numberOfHoursTextBox);
      numberHoursCostPanel.add(new Label("часа - "));
      hoursCostTextBox.setWidth("30px");
      long cost = moreLessUnlimModel.getCostForHours();
      hoursCostTextBox.setValue(cost != 0 ? String.valueOf(cost) : "0");
      numberHoursCostPanel.add(hoursCostTextBox);
      numberHoursCostPanel.add(new Label("руб."));
      Button removeButton = new Button("Удалить");
      removeButton.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          numberHoursCostPanel.removeFromParent();
          setAddButtonEnabled(addHourButton);
        }
      });
      numberHoursCostPanel.add(removeButton);
      if (hoursCostsPanel.getWidgetCount() < 6) {
        hoursCostsPanel.add(numberHoursCostPanel);
      }
      setAddButtonEnabled(addHourButton);
      unlimCostTextBox.setValue(moreLessUnlimModel.getUnlimCost() != 0 ? String.valueOf(moreLessUnlimModel.getUnlimCost()) : "0");
      costPerMinuteTextBox.setValue(moreLessUnlimModel.getCostPerMinute() != 0 ? String.valueOf(moreLessUnlimModel.getCostPerMinute()) : "0");
    }
  }

  public boolean validate() {
    for (int i = 0; i < hoursCostsPanel.getWidgetCount(); i++) {
      Widget widget = hoursCostsPanel.getWidget(i);
      if (widget instanceof HorizontalPanel) {
        HorizontalPanel horizontalPanel = (HorizontalPanel) widget;
        for (int j = 0; j < horizontalPanel.getWidgetCount(); j++) {
          Widget childWidget = horizontalPanel.getWidget(j);
          if (childWidget instanceof AntiTextBox) {
            if (((AntiTextBox) childWidget).getValue() == null || ((AntiTextBox) childWidget).getValue().isEmpty() ) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  public boolean validateOrder() {
    long prevNumberOfHoursValue = 0;
    for (int i = 0; i < hoursCostsPanel.getWidgetCount(); i++) {
      Widget widget = hoursCostsPanel.getWidget(i);
      if (widget instanceof HorizontalPanel) {
        HorizontalPanel horizontalPanel = (HorizontalPanel) widget;
        for (int j = 0; j < horizontalPanel.getWidgetCount(); j++) {
          Widget childWidget = horizontalPanel.getWidget(j);
          if (childWidget instanceof AntiTextBox) {
            if (((AntiTextBox) childWidget).getName().equals("numberOfHoursTextBox")) {
              if (Long.valueOf(((AntiTextBox) childWidget).getValue()) <= prevNumberOfHoursValue) {
                return false;
              } else {
                prevNumberOfHoursValue = Long.valueOf(((AntiTextBox) childWidget).getValue());
              }
            }
            if (((AntiTextBox) childWidget).getValue() == null || ((AntiTextBox) childWidget).getValue().isEmpty() ) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  private void setAddButtonEnabled(Button addHourButton) {
    addHourButton.setEnabled(hoursCostsPanel.getWidgetCount() < 7);
  }

  public Map<Long, MoreLessUnlimModel> getSettings() {
    Map<Long, MoreLessUnlimModel> moreLessUnlimModelMap = new HashMap<>();
    long order = 1;
    for (int i = 0; i < hoursCostsPanel.getWidgetCount(); i++) {
      MoreLessUnlimModel moreLessUnlimModel = new MoreLessUnlimModel();
      Widget widget = hoursCostsPanel.getWidget(i);
      if (widget instanceof HorizontalPanel) {
        HorizontalPanel horizontalPanel = (HorizontalPanel) widget;
        for (int j = 0; j < horizontalPanel.getWidgetCount(); j++) {
          Widget childWidget = horizontalPanel.getWidget(j);
          if (childWidget instanceof AntiTextBox) {
            AntiTextBox antiTextBox = (AntiTextBox) childWidget;
            if (antiTextBox.getName().equals("numberOfHoursTextBox")) {
              moreLessUnlimModel.setNumberOfHours(antiTextBox.getValue() != null ? Long.valueOf(antiTextBox.getValue()) : 0);
            } else if (antiTextBox.getName().equals("hoursCostTextBox")) {
              antiTextBox = (AntiMoneyTextBox) childWidget;
              moreLessUnlimModel.setCostForHours(antiTextBox.getValue() != null ? Long.valueOf(antiTextBox.getValue()) : 0);
            }
          }
        }
      }
      moreLessUnlimModel.setCostPerMinute(Long.valueOf(costPerMinuteTextBox.getValue()));
      moreLessUnlimModel.setUnlimCost(Long.valueOf(unlimCostTextBox.getValue()));
      order += 1;
      moreLessUnlimModel.setOrder(order);
      moreLessUnlimModelMap.put(moreLessUnlimModel.getOrder(), moreLessUnlimModel);
    }
    return moreLessUnlimModelMap;
  }
}
