package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * Created by dmitry on 26.07.16.
 */
public class ClientSession implements Serializable, IsSerializable {
  private long id;
  private long startTime;
  private long stopTime;
  private boolean isPayed;
  private SESSION_STATUS status;

  public ClientSession(long startTime, long stopTime, boolean isPayed) {
    this.startTime = startTime;
    this.stopTime = stopTime;
    this.isPayed = isPayed;
    setStatus(SESSION_STATUS.CREATED);
  }

  public ClientSession() {
  }

  public enum SESSION_STATUS implements Serializable, IsSerializable {
    CREATED, STARTED, PAUSED, STOPPED, PAYED;
  }

  public SESSION_STATUS getSessionStatus() {
    return status;
  }

  public void setStatus(SESSION_STATUS status) {
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public boolean isPayed() {
    return isPayed;
  }

  public void setIsPayed(boolean isPayed) {
    this.isPayed = isPayed;
  }
}
