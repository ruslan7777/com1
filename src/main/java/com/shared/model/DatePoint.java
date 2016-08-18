package com.shared.model;

/**
 * Created by dmitry on 18.08.16.
 */
public enum DatePoint {
  TODAY(0, 0, "Сегодня"), YESTERDAY(1, -1, "Вчера"), LAST_SEVEN_DAYS(2, -7, "Последние 7 дней"), LAST_THIRTY_DAYS(3, -30, "Поледние 30 дней"), ALL(4, -1000000, "Все");
  private int index;
  private int shiftValue;
  private String text;

  DatePoint(int index, int shiftValue, String text) {
    this.index = index;
    this.shiftValue = shiftValue;
    this.text = text;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getShiftValue() {
    return shiftValue;
  }

  public void setShiftValue(int shiftValue) {
    this.shiftValue = shiftValue;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public static DatePoint indexOf(int index) {
    for (DatePoint datePoint : values()) {
      if (index == datePoint.getIndex()) {
        return datePoint;
      }
    }
    return null;
  }

}
