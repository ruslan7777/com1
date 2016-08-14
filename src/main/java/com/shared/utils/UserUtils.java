package com.shared.utils;

import com.shared.model.SettingsHolder;
import com.shared.model.User;

/**
 * Created by dmitry on 14.08.16.
 */
public class UserUtils {
//  static
//  {
//    INSTANCE = new UserUtils();
//  }
  public static UserUtils INSTANCE;
  private User currentUser;
  private SettingsHolder settings;

  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  public SettingsHolder getSettings() {
    return settings;
  }

  public void setSettings(SettingsHolder settings) {
    this.settings = settings;
  }

  public static void init() {
    INSTANCE = new UserUtils();
  }
}
