package com.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;
import com.shared.model.SettingsHolder;
import com.shared.model.User;

import java.util.Collection;
import java.util.List;

public interface ClientSessionServiceAsync {
    void getFreePseudoNames(AsyncCallback<List<SessionPseudoName>> async);

    void markNameAsFree(SessionPseudoName name, AsyncCallback<Void> async);

    void markNameAsUsed(SessionPseudoName name, AsyncCallback<Void> async);

    void addNames(List<SessionPseudoName> pseudoNamesList, AsyncCallback<Void> asyncCallback);

    void saveClientSession(ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn, AsyncCallback<List<ClientSession>> asyncCallback);

    void removeClientSession(ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn, AsyncCallback<List<ClientSession>> asyncCallback);

    void getClientSessions(User currentUser, boolean isShowRemoved, boolean showPayedOn, AsyncCallback<List<ClientSession>> asyncCallback);

    void stopClientSession(ClientSession clientSession, AsyncCallback<Long> asyncCallback);

    void payClientSession(ClientSession clientSession, AsyncCallback<Long> asyncCallback);

    void getAllPseudoNames(AsyncCallback<List<SessionPseudoName>> asyncCallback);

    void addName(SessionPseudoName namesTextBoxValue, AsyncCallback<Void> asyncCallback);

    void removeName(SessionPseudoName sessionPseudoName, AsyncCallback<Void> asyncCallback);

    void getCurrentUser(String userName, String userPassword, AsyncCallback<User> asyncCallback);

    void saveUser(User user, AsyncCallback<User> asyncCallback);

    void login(String userName, String passwordTextBoxValue, AsyncCallback<User> asyncCallback);

    void startClientSession(ClientSession clientSession, AsyncCallback<Long> asyncCallback);
}
