package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by dmitry on 26.07.16.
 */
//@Entity
@Entity
@Table(name = "pseudonames")
public class SessionPseudoName implements Serializable, IsSerializable {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  private String name;
  private boolean isUsed;

  @Column(name = "user_id")
  private long userEntity;
//  private long userEntity;

  public SessionPseudoName(String name) {
    this.name = name;
  }

  public SessionPseudoName(String name, long user) {
    this.name = name;
    this.userEntity = user;
  }

  public SessionPseudoName() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isUsed() {
    return isUsed;
  }

  public void setIsUsed(boolean isUsed) {
    this.isUsed = isUsed;
  }

//  public long getUserEntity() {
//    return userEntity;
//  }
//
//  public void setUserEntity(long userEntity) {
//    this.userEntity = userEntity;
//  }

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinTable(name = "users")
  @JoinColumn(name = "user_id")
  public long getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(long user) {
    this.userEntity = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
    public boolean equals(Object obj) {
      if (obj instanceof SessionPseudoName) {
        return this.getName().equals(((SessionPseudoName) obj).getName());
      } else {
        return false;
      }
    }
}
