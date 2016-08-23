package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by dmitry on 14.08.16.
 */
@Entity
public class SettingsHolder implements Serializable, IsSerializable {
    @Id
    private Long settingsId;
  private Long firstPartSumAmount;
  private Long firstPartLength;
  private Long maxSessionLength = 90000l;
  private boolean isToShowRemoved = false;
  private boolean isToShowPayed = false;
  private Long unlimitedCost = 800l;
  private Map<Long, HourCostModel> hourCostModelMap;
  private countStrategy currentCountStrategy = countStrategy.MULTI_HOURS;
  private Long hourLength = 180000l;
  private User user;

  public enum countStrategy{
    MULTI_HOURS, HOUR_MINUTES
  }

  public countStrategy getCurrentCountStrategy() {
    return currentCountStrategy;
  }

  public void setCurrentCountStrategy(countStrategy currentCountStrategy) {
    this.currentCountStrategy = currentCountStrategy;
  }

  public Long getHourLength() {
    return hourLength;
  }

  public void setHourLength(Long hourLength) {
    this.hourLength = hourLength;
  }

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

  public Map<Long, HourCostModel> getHourCostModelMap() {
    return hourCostModelMap;
  }

  public List<HourCostModel> getOrderedHourCostModels() {
    List<HourCostModel> hourCostModels = new ArrayList<>();
    for (Long key : hourCostModelMap.keySet()) {
      hourCostModels.add(hourCostModelMap.get(key));
    }
    Collections.sort(hourCostModels, new Comparator<HourCostModel>() {
      @Override
      public int compare(HourCostModel o1, HourCostModel o2) {
        return o1.getHourOrder() > o2.getHourOrder() ? 1 : -1;
      }
    });
    return hourCostModels;
  }

  public void setHourCostModelMap(Map<Long, HourCostModel> hourCostModelMap) {
    this.hourCostModelMap = hourCostModelMap;
  }

  public Long getUnlimitedCost() {
    return unlimitedCost;
  }

  public void setUnlimitedCost(Long unlimitedCost) {
    this.unlimitedCost = unlimitedCost;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
