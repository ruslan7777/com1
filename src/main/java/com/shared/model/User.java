package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dmitry on 14.08.16.
 */
@Entity
@Table(name = "users")
public class User implements Serializable, IsSerializable {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long userId;
  private String userName;
  private String password;
  @Column(name = "settings_holder_id")
  private long settingsHolder;

  public User() {
  }

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<ClientSession> clientSessions;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MoreLessUnlimModel> moreLessUnlimModelList;

  public List<MoreLessUnlimModel> getMoreLessUnlimModelList() {
    if (moreLessUnlimModelList == null) {
      moreLessUnlimModelList = new ArrayList<>();
    }
    return moreLessUnlimModelList;
  }

  public void setMoreLessUnlimModelList(List<MoreLessUnlimModel> moreLessUnlimModelList) {
    this.moreLessUnlimModelList = moreLessUnlimModelList;
  }


  public List<ClientSession> getClientSessions() {
    return this.clientSessions;
  }

  public void setClientSessions(List<ClientSession> clientSessions) {
    this.clientSessions = clientSessions;
  }

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SessionPseudoName> sessionPseudoNames;

  public List<SessionPseudoName> getSessionPseudoNames() {
    return sessionPseudoNames;
  }

  public void setSessionPseudoNames(List<SessionPseudoName> sessionPseudoNames) {
    this.sessionPseudoNames = sessionPseudoNames;
  }

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

  @OneToOne
  @JoinTable(name = "settins")
  @JoinColumn(name = "settings_holder_id", referencedColumnName = "setting_id")
  public long getSettingsHolder() {
    return settingsHolder;
  }

  public void setSettingsHolder(long settingsHolder) {
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

