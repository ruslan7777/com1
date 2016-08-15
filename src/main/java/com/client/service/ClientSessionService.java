package com.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;
import com.shared.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/9/16
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
@RemoteServiceRelativePath("clientSession")
public interface ClientSessionService extends RemoteService {
    public List<SessionPseudoName> getFreePseudoNames();
    public void markNameAsFree(SessionPseudoName name);
    public void markNameAsUsed(SessionPseudoName name);

    void addNames(List<SessionPseudoName> pseudoNamesList);

    Long saveClientSession(ClientSession clientSession);

    void removeClientSession(ClientSession clientSession);

    List<ClientSession> getClientSessions(User currentUser);

    long stopClientSession(ClientSession clientSession);

    long payClientSession(ClientSession clientSession);

    List<SessionPseudoName> getAllPseudoNames();

    void addName(SessionPseudoName namesTextBoxValue);

    void removeName(SessionPseudoName sessionPseudoName);

    User getCurrentUser(String userName, String userPassword);

    User saveUser(User user);

    User login(String userName, String passwordTextBoxValue);
}
