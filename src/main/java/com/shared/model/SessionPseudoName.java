package com.shared.model;

/**
 * Created by dmitry on 26.07.16.
 */
public class SessionPseudoName {
  private String name;
  private boolean isUsed;

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
}
