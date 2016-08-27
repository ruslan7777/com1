package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;

/**
 * Created by dmitry on 22.08.16.
 */
@Entity
public class MoreLessUnlimModel implements Serializable, IsSerializable {
  @Id
  private Long id;
  private long order;
  private long settingsHolderId;
  private long unlimCost;
  private long costPerMinute;
  private long numberOfHours;
  private long costForHours;

  public MoreLessUnlimModel(long settingsHolderId, long hourOrder, long costPerMinute,
                            long numberOfHours, long costForHours, long unlimCost) {
    this.order = hourOrder;
    this.settingsHolderId = settingsHolderId;
    this.unlimCost = hourOrder;
    this.costPerMinute = costPerMinute;
    this.numberOfHours = numberOfHours;
    this.costForHours = costForHours;
    this.unlimCost = unlimCost;
  }

  public MoreLessUnlimModel() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public long getOrder() {
    return order;
  }

  public void setOrder(long order) {
    this.order = order;
  }

  public long getSettingsHolderId() {
    return settingsHolderId;
  }

  public void setSettingsHolderId(long settingsHolderId) {
    this.settingsHolderId = settingsHolderId;
  }

  public long getUnlimCost() {
    return unlimCost;
  }

  public void setUnlimCost(long unlimCost) {
    this.unlimCost = unlimCost;
  }

  public long getCostPerMinute() {
    return costPerMinute;
  }

  public void setCostPerMinute(long costPerMinute) {
    this.costPerMinute = costPerMinute;
  }

  public long getNumberOfHours() {
    return numberOfHours;
  }

  public void setNumberOfHours(long numberOfHours) {
    this.numberOfHours = numberOfHours;
  }

  public long getCostForHours() {
    return costForHours;
  }

  public void setCostForHours(long costForHours) {
    this.costForHours = costForHours;
  }
}
