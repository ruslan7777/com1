package com.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;

/**
 * Created by dmitry on 26.07.16.
 */
@Entity
public class SessionPseudoName implements Serializable, IsSerializable {
  @Id
  private String name;
  private boolean isUsed;

  public SessionPseudoName(String name) {
    this.name = name;
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

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof SessionPseudoName) {
        return this.getName().equals(((SessionPseudoName) obj).getName());
      } else {
        return false;
      }
    }
}
