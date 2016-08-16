package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;

/**
 * Created by dmitry on 14.08.16.
 */
@Entity
public class SettingsHolder implements Serializable, IsSerializable {
    @Id
    private Long settingsId;
  private Long firstPartSumAmount;
  private Long firstPartLength;
  private Long maxSessionLength;
  private boolean isToShowRemoved = true;
  private boolean isToShowPayed = true;
  private User user;

  public Long getSettingsId() {
    return settingsId;
  }

  public void setSettingsId(Long settingsId) {
    this.settingsId = settingsId;
  }

  public Long getFirstPartSumAmount() {
    return firstPartSumAmount;
  }

  public void setFirstPartSumAmount(Long firstPartSumAmount) {
    this.firstPartSumAmount = firstPartSumAmount;
  }

  public Long getFirstPartLength() {
    return firstPartLength;
  }

  public void setFirstPartLength(Long firstPartLength) {
    this.firstPartLength = firstPartLength;
  }

    public Long getMaxSessionLength() {
        return maxSessionLength;
    }

    public void setMaxSessionLength(Long maxSessionLength) {
        this.maxSessionLength = maxSessionLength;
    }

  public boolean isToShowRemoved() {
    return isToShowRemoved;
  }

  public void setIsToShowRemoved(boolean isToShowRemoved) {
    this.isToShowRemoved = isToShowRemoved;
  }

  public boolean isToShowPayed() {
    return isToShowPayed;
  }

  public void setIsToShowPayed(boolean isToShowPayed) {
    this.isToShowPayed = isToShowPayed;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
