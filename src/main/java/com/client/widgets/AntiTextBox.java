package com.client.widgets;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Created by dmitry on 22.08.16.
 */
public class AntiTextBox extends TextBox {
  public AntiTextBox() {
    addKeyPressHandler(new KeyPressHandler() {
      @Override
      public void onKeyPress(KeyPressEvent event) {
        String input = getText();
        if (!input.matches("[0-9]*")) {
          // show some error
          return;
        }
        // do your thang
      }
    });
  }
}
