package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;

/**
 * Created by dmitry on 14.08.16.
 */
public class User implements Serializable, IsSerializable {
  @Id
  private Long userId;
  private String userName;
  private String password;
  private SettingsHolder settings;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public SettingsHolder getSettings() {
    return settings;
  }

  public void setSettings(SettingsHolder settings) {
    this.settings = settings;
  }
}

