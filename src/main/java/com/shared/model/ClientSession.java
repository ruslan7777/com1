package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by dmitry on 26.07.16.
 */
@Entity
public class ClientSession implements Serializable, IsSerializable, Comparable<ClientSession> {
    @Id
    private Long id;
  private long creationTime;
  private long startTime;
  private long stopTime;
  private long finalSum;
  private String sessionPseudoName;
  private SESSION_STATUS status = SESSION_STATUS.CREATED;
    private long userId;

  public ClientSession(long startTime, long stopTime, long userId) {
    this.startTime = startTime;
    this.stopTime = stopTime;
      this.userId = userId;
  }

  public ClientSession() {
  }

  @Override
  public int compareTo(ClientSession o) {
    return o.getCreationTime() >= this.getCreationTime() ? 1 : -1;
  }

  public enum SESSION_STATUS implements Serializable, IsSerializable {
    CREATED("Создана", "Старт"), STARTED("В процессе", "Стоп"), PAUSED("Приостановлена", "Возобновить"),
    STOPPED("Остановлена", "Оплатить"), STOPPED_UNLIMITED ("Безлимит", "Оплатить"), PAYED("Оплачена", "Оплачена"), REMOVED("Удалена", "Удалена");
    private String value;
    private String buttonText;
    private int order;

    SESSION_STATUS(String value, String buttonText) {
      this.value = value;
      this.buttonText = buttonText;
    }

    public String getValue() {
      return value;
    }

    public String getButtonText() {
      return buttonText;
    }

    public int getOrder() {
      return order;
    }

    public void setOrder(int order) {
      this.order = order;
    }
  }

  public SESSION_STATUS getSessionStatus() {
    return status;
  }

  public void setStatus(SESSION_STATUS status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(long creationTime) {
    this.creationTime = creationTime;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getStopTime() {
    return stopTime;
  }

  public void setStopTime(long stopTime) {
    this.stopTime = stopTime;
  }

  public long getFinalSum() {
    return finalSum;
  }

  public void setFinalSum(long finalSum) {
    this.finalSum = finalSum;
  }

  public String getSessionPseudoName() {
    return sessionPseudoName;
  }

  public void setSessionPseudoName(String sessionPseudoName) {
    this.sessionPseudoName = sessionPseudoName;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }
}
