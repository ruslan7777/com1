package com.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;

import java.util.List;

public interface ClientSessionServiceAsync {
    void getFreePseudoNames(AsyncCallback<List<SessionPseudoName>> async);

    void markNameAsFree(SessionPseudoName name, AsyncCallback<Void> async);

    void markNameAsUsed(SessionPseudoName name, AsyncCallback<Void> async);

    void addNames(List<SessionPseudoName> pseudoNamesList, AsyncCallback<Void> asyncCallback);

    void saveClientSession(ClientSession clientSession, AsyncCallback<Void> asyncCallback);
}
