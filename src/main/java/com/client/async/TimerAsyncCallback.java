package com.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by dmitry on 13.08.16.
 */
public class TimerAsyncCallback implements AsyncCallback{
  @Override
  public void onFailure(Throwable caught) {
    System.out.println(caught.getMessage());
  }

  @Override
  public void onSuccess(Object result) {
    System.out.println("it's ok");
  }
}
