package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by dmitry on 14.08.16.
 */
@Entity
public class User implements Serializable, IsSerializable {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long userId;
  private String userName;
  private String password;
  private SettingsHolder settingsHolder;

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

  public SettingsHolder getSettingsHolder() {
    return settingsHolder;
  }

  public void setSettingsHolder(SettingsHolder settingsHolder) {
    this.settingsHolder = settingsHolder;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof User) {
      return this.getUserName().equals(((User)obj).getUserName());
    }
    return false;
  }
}

