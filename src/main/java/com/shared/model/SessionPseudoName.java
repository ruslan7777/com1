package com.shared.model;

import java.io.Serializable;

/**
 * Created by dmitry on 26.07.16.
 */
public class SessionPseudoName implements Serializable{
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
        return this.getName().equals(obj);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
