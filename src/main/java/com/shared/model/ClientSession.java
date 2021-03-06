package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by dmitry on 26.07.16.
 */
@javax.persistence.Entity
@Table(name = "clientsessions")
public class ClientSession implements Serializable, IsSerializable, Comparable<ClientSession> {
//    @Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
  private long creationTime;
  private long startTime;
  private long stopTime;
  private long finalSum;
//  private String sessionPseudoName;
  private SESSION_STATUS status = SESSION_STATUS.CREATED;
//    private long userEntity;

  private long userEntity;

  @Column(unique = false)
  private SessionPseudoName sessionPseudoName;
//  @Transient
//  private String pseudoName;


  public ClientSession(long startTime, long stopTime, long user) {
    this.startTime = startTime;
    this.stopTime = stopTime;
      this.userEntity = user;
  }

  public ClientSession() {
  }

  @Override
  public int compareTo(ClientSession o) {
    return o.getCreationTime() >= this.getCreationTime() ? 1 : -1;
  }

//  public String getPseudoName() {
//    return pseudoName;
//  }
//
//  public void setPseudoName(String pseudoName) {
//    this.pseudoName = pseudoName;
//  }

  public enum SESSION_STATUS implements Serializable, IsSerializable {
    CREATED("Создана", "Старт"), STARTED("В процессе", "Стоп"), PAUSED("Приостановлена", "Возобновить"),
    STOPPED("Остановлена", "Оплачено"), STOPPED_UNLIMITED ("Безлимит", "Оплачено"), PAYED("Оплачена", "Оплачена"), REMOVED("Удалена", "Удалена");
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

//  public long getUserEntity() {
//    return userEntity;
//  }
//
//  public void setUserEntity(long userEntity) {
//    this.userEntity = userEntity;
//  }

//  @ManyToOne
//  @JoinTable(name = "users")
//  @JoinColumn(name = "userEntity")
//  public long getUserEntity() {
//    return userEntity;
//  }
//
//  public void setUserEntity(long user) {
//    this.userEntity = user;
//  }

    @ManyToOne
  @JoinTable(name = "users")
  @JoinColumn(name = "userId")
  public long getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(long userEntity) {
    this.userEntity = userEntity;
  }

  @OneToOne(cascade = CascadeType.ALL)
  public SessionPseudoName getSessionPseudoName() {
    return sessionPseudoName;
  }

  public void setSessionPseudoName(SessionPseudoName sessionPseudoName) {
    this.sessionPseudoName = sessionPseudoName;
  }
}
