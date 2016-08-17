package com.client;

import com.client.events.AddSessionEvent;
import com.client.events.ToggleShowPayedEvent;
import com.client.events.ToggleShowRemovedEvent;
import com.client.events.UpdateSumEvent;
import com.client.events.UpdateSumEventHandler;
import com.client.panels.ReportsPanel;
import com.client.panels.SettingsPanel;
import com.client.service.ClientSessionService;
import com.client.service.ClientSessionServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.shared.model.SessionPseudoName;
import com.shared.utils.UserUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Dimon on 19.07.2016.
 */
public class MainTabPanel extends TabLayoutPanel {
    private SimpleEventBus simpleEventBus;
  ToggleButton showRemovedButton;
  ToggleButton showPayedButton;
  Label sumLabel;
  private final ClientSessionServiceAsync clientSessionService = GWT.create(ClientSessionService.class);
  /**
   * Creates an empty tab panel.
   *
   * @param barHeight the size of the tab bar
   * @param barUnit   the unit in which the tab bar size is specified
   */
  public MainTabPanel(double barHeight, Style.Unit barUnit, final SimpleEventBus eventBus) {
    super(barHeight, barUnit);
      this.simpleEventBus = eventBus;
    showRemovedButton = new ToggleButton("Показывать удаленные");
    showPayedButton = new ToggleButton("Показывать оплаченные");
    // Create a tab panel
//    TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Style.Unit.EM);
    setAnimationDuration(301);
//    getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);
//    getElement().getStyle().setMarginLeft(300.0, Style.Unit.PX);
    setHeight("600px");
    setWidth("100%");
    eventBus.addHandler(UpdateSumEvent.TYPE, new UpdateSumEventHandler() {
      @Override
      public void updateSum(UpdateSumEvent updateSumEvent) {
       sumLabel.setText(getPrettyMoney(updateSumEvent.getSum()));
      }
    });
//    setHeight("100%");
//    setWidth("100%");
    // Add a home tab
    String[] tabTitles = {"Сессии", "Настройки", "Отчеты"};
//    ClientSessionGridPanel clientSessionGridPanel = new ClientSessionGridPanel(simpleEventBus);
    SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
    splitLayoutPanel.setSize("100%", "100%");

    showRemovedButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        UserUtils.INSTANCE.getCurrentUser().getSettings().setIsToShowRemoved(event.getValue());
        ToggleShowRemovedEvent toggleShowRemovedEvent = new ToggleShowRemovedEvent();
        toggleShowRemovedEvent.setIsShowRemovedOn(event.getValue());
        toggleShowRemovedEvent.setIsShowPayedCurrentState(showPayedButton.getValue());
        eventBus.fireEvent(toggleShowRemovedEvent);
      }
    });
    showRemovedButton.setWidth("150px");
    showRemovedButton.setHeight("40px");
    showRemovedButton.setDown(true);
    VerticalPanel eastButtonsPanel = new VerticalPanel();

    Button addButton = new Button("Добавить");
    addButton.setHeight("30px");
    addButton.setWidth("200px");
    addButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {

        DialogBox dialogBox = createDialogBox();
        dialogBox.center();
        dialogBox.setModal(true);
        dialogBox.setText("Выбор псевдонима");
        dialogBox.setSize("200px", "150px");
//        firstPartTimeLength = UserUtils.INSTANCE.getCurrentUser().getSettings().getFirstPartLength();
//        firstPartSumAmount = UserUtils.INSTANCE.getCurrentUser().getSettings().getFirstPartSumAmount();
        dialogBox.show();
      }
    });

    eastButtonsPanel.add(addButton);

    eastButtonsPanel.getElement().getStyle().setMargin(10, Style.Unit.PX);
    eastButtonsPanel.add(showRemovedButton);
    showPayedButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        UserUtils.INSTANCE.getCurrentUser().getSettings().setIsToShowPayed(event.getValue());
        ToggleShowPayedEvent toggleShowPayedEvent = new ToggleShowPayedEvent();
        toggleShowPayedEvent.setIsShowPayedOn(event.getValue());
        toggleShowPayedEvent.setIsShowRemovedCurrentState(showRemovedButton.getValue());
        eventBus.fireEvent(toggleShowPayedEvent);
      }
    });
    showPayedButton.setWidth("150px");
    showPayedButton.setHeight("40px");
    showPayedButton.setDown(true);
    eastButtonsPanel.add(showPayedButton);

    eastButtonsPanel.add(new Label("Сумма:"));
    sumLabel = new Label();
    eastButtonsPanel.add(sumLabel);

    splitLayoutPanel.addEast(eastButtonsPanel, 250);
    splitLayoutPanel.getElement().getStyle().setMargin(5, Style.Unit.PX);
    splitLayoutPanel.getElement().getStyle().setPadding(5, Style.Unit.PX);
    add(splitLayoutPanel, tabTitles[0]);
    HorizontalPanel southPanel = new HorizontalPanel();
    southPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    southPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//    southPanel.getElement().getStyle().setMargin(10, Style.Unit.PX);
//    splitLayoutPanel.addSouth(southPanel, 60);
    splitLayoutPanel.add(new ClientSessionGridPanel(simpleEventBus));

    // Add a tab with an image
//    SimplePanel imageContainer = new SimplePanel();
//    imageContainer.setWidget(new Button("dfdfdf"));
    add(new SettingsPanel(simpleEventBus), tabTitles[1]);

    // Add a tab
//    HTML moreInfo = new HTML("some html");
    add(new ReportsPanel(), tabTitles[2]);

    // Return the content
    selectTab(0);
//    ensureDebugId("cwTabPanel");

  }

  private DialogBox createDialogBox() {
    // Create a dialog box and set the caption text
    final DialogBox dialogBox = new DialogBox();

//        dialogBox.setWidth("400px");
//        dialogBox.setHeight("400px");
    dialogBox.ensureDebugId("cwDialogBox");
//        dialogBox.setText("dfd");

    // Create a table to layout the content
    VerticalPanel dialogContents = new VerticalPanel();
    dialogContents.setSpacing(5);
    dialogContents.setSize("300px", "300px");
    dialogContents.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    dialogContents.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    dialogBox.setWidget(dialogContents);

    final ListBox namesListBox = new ListBox();
    namesListBox.setWidth("200px");
    clientSessionService.getFreePseudoNames(new AsyncCallback<List<SessionPseudoName>>() {
      @Override
      public void onFailure(Throwable caught) {

      }

      @Override
      public void onSuccess(List<SessionPseudoName> result) {
        for (SessionPseudoName item : result) {
          namesListBox.addItem(item.getName());
        }
      }
    });
//        namesListBox.addItem("GREEN");
//        namesListBox.addItem("YELLOW");
//        namesListBox.addItem("BLACK");
    dialogContents.add(namesListBox);
    Button createButton = new Button("Создать");
    createButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        final AddSessionEvent event = new AddSessionEvent();
        event.setClientPseudoName(namesListBox.getSelectedValue());
        clientSessionService.markNameAsUsed(new SessionPseudoName(namesListBox.getSelectedValue()), new AsyncCallback<Void>() {
          @Override
          public void onFailure(Throwable caught) {

          }

          @Override
          public void onSuccess(Void result) {
            simpleEventBus.fireEvent(event);
          }
        });
        //To change body of implemented methods use File | Settings | File Templates.
        dialogBox.hide();
      }
    });
    Button cancelButton = new Button("Отмена");
    cancelButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        dialogBox.hide();
      }
    });
    HorizontalPanel buttonContainer = new HorizontalPanel();
    buttonContainer.add(createButton);
    buttonContainer.add(cancelButton);
    dialogContents.add(buttonContainer);
//          Button addEntityButton = new Button("Создать client");
//          addEntityButton.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent clickEvent) {
//              final ClientSession clientSession = new ClientSession(System.currentTimeMillis(),
//                      0, false, UserUtils.INSTANCE.getCurrentUser());
//              clientSessionService.saveClientSession(clientSession, new AsyncCallback<Long>() {
//                @Override
//                public void onFailure(Throwable throwable) {
//                  //To change body of implemented methods use File | Settings | File Templates.
//                }
//
//                @Override
//                public void onSuccess(Long id) {
//                  clientSession.setId(id);//To change body of implemented methods use File | Settings | File Templates.
//                }
//              });
//
//            }
//          });
//        dialogContents.add(addEntityButton);
//        if (LocaleInfo.getCurrentLocale().isRTL()) {
//            dialogContents.setCellHorizontalAlignment(
//                    closeButton, HasHorizontalAlignment.ALIGN_LEFT);
//
//        } else {
//            dialogContents.setCellHorizontalAlignment(
//                    closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
//        }

    // Return the dialog box
    return dialogBox;
  }

  public Label getSumLabel() {
    return sumLabel;
  }

  public void setSumLabel(Label sumLabel) {
    this.sumLabel = sumLabel;
  }

  private String getPrettyMoney(long minPayment) {
    return new BigDecimal(minPayment).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
  }

}
