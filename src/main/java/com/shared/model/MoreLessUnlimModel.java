package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created by dmitry on 22.08.16.
 */
@Entity
public class MoreLessUnlimModel implements Serializable, IsSerializable {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  private long modelOrder;
  private SettingsHolder settingsHolder;
  private long unlimCost;
  private long costPerMinute;
  private long numberOfHours;
  private long costForHours;

  public MoreLessUnlimModel(SettingsHolder settingsHolder, long hourOrder, long costPerMinute,
                            long numberOfHours, long costForHours, long unlimCost) {
    this.settingsHolder = settingsHolder;
    this.modelOrder = hourOrder;
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

  public long getModelOrder() {
    return modelOrder;
  }

  public void setModelOrder(long modelOrder) {
    this.modelOrder = modelOrder;
  }

  public SettingsHolder getSettingsHolder() {
    return settingsHolder;
  }

  public void setSettingsHolder(SettingsHolder settingsHolder) {
    this.settingsHolder = settingsHolder;
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
