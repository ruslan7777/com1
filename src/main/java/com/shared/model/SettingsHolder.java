package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
  private Map<Long, MoreLessUnlimModel> moreLessUnlimModelMap;
  private countStrategy currentCountStrategy = countStrategy.MULTI_HOURS;
  private Long hourLength = 60000l;
  private User user;

  public SettingsHolder() {
    hourCostModelMap = new HashMap<>();
    moreLessUnlimModelMap = new HashMap<>();
    HourCostModel hourCostModel = new HourCostModel();
    hourCostModel.setHourOrder(1);
    hourCostModel.setCostPerHour(250);
    hourCostModel.setCostPerMinute(5);
    hourCostModel.setSettingsHolderId(1);
    hourCostModelMap.put(hourCostModel.getHourOrder(), hourCostModel);
    HourCostModel hourCostModel2 = new HourCostModel();
    hourCostModel2.setHourOrder(2);
    hourCostModel2.setCostPerHour(200);
    hourCostModel2.setCostPerMinute(4);
    hourCostModel2.setSettingsHolderId(1);
    hourCostModelMap.put(hourCostModel2.getHourOrder(), hourCostModel2);
    MoreLessUnlimModel moreLessUnlimModel = new MoreLessUnlimModel(1, 1, 5, 2, 250, 350);
    moreLessUnlimModelMap.put(moreLessUnlimModel.getOrder(), moreLessUnlimModel);
    MoreLessUnlimModel moreLessUnlimModel2 = new MoreLessUnlimModel(1, 2, 5, 3, 300, 350);
    moreLessUnlimModelMap.put(moreLessUnlimModel2.getOrder(), moreLessUnlimModel2);
    MoreLessUnlimModel moreLessUnlimModel3 = new MoreLessUnlimModel(1, 3, 5, 4, 350, 350);
    moreLessUnlimModelMap.put(moreLessUnlimModel3.getOrder(), moreLessUnlimModel3);
  }

  public enum countStrategy{
    MULTI_HOURS("Больше>Меньше>Безлимит"), HOUR_MINUTES("Первый час");
    private String text;

    countStrategy(String text) {
      this.text = text;
    }

    public String getText() {
      return text;
    }

    public void setText(String text) {
      this.text = text;
    }
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

  public List<MoreLessUnlimModel> getOrderedMoreLessUnlimModels() {
    List<MoreLessUnlimModel> moreLessUnlimModels = new ArrayList<>();
    for (Long key : moreLessUnlimModelMap.keySet()) {
      moreLessUnlimModels.add(moreLessUnlimModelMap.get(key));
    }
    Collections.sort(moreLessUnlimModels, new Comparator<MoreLessUnlimModel>() {
      @Override
      public int compare(MoreLessUnlimModel o1, MoreLessUnlimModel o2) {
        return o1.getOrder() > o2.getOrder() ? 1 : -1;
      }
    });
    return moreLessUnlimModels;
  }

  public Map<Long, MoreLessUnlimModel> getMoreLessUnlimModelMap() {
    return moreLessUnlimModelMap;
  }

  public void setMoreLessUnlimModelMap(Map<Long, MoreLessUnlimModel> moreLessUnlimModelMap) {
    this.moreLessUnlimModelMap = moreLessUnlimModelMap;
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
