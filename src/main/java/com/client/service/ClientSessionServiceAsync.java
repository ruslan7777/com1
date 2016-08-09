package com.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

public interface ClientSessionServiceAsync {
    void getPseudoNames(AsyncCallback<List<String>> async);

    void addName(String name, AsyncCallback<Void> async);

    void removeName(String name, AsyncCallback<Void> async);

    void addNames(List<String> pseudoNamesList, AsyncCallback<Void> asyncCallback);
}
