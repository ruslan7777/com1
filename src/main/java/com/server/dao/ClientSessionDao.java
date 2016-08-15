package com.server.dao;

import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;
import com.shared.model.SettingsHolder;
import com.shared.model.User;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientSessionDao {

    List<SessionPseudoName> getFreePseudoNames();

    void markNameAsFree(SessionPseudoName name);

    void markNameAsUsed(SessionPseudoName name);

    void addNames(List<SessionPseudoName> pseudoNamesList);

    Long saveClientSession(ClientSession clientSession);

    void removeClientSession(ClientSession clientSession);

    List<ClientSession> getClientSessionsList(User currentUser);

    long stopClientSession(ClientSession clientSession);

    long payClientSession(ClientSession clientSession);

    List<SessionPseudoName> getAllPseudoNames();

    void addName(SessionPseudoName namesTextBoxValue);

    void removeName(SessionPseudoName sessionPseudoName);

    User getCurrentUser(String userName, String userPassword);

    User saveUser(User user);

    User login(String userName, String userPassword);
}
