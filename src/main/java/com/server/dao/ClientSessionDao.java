package com.server.dao;

import com.shared.model.ClientSession;
import com.shared.model.DatePoint;
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

    List<ClientSession> saveClientSession(DatePoint datePoint, ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn);
    List<ClientSession> saveHiberClientSession(DatePoint datePoint, ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn);

    List<ClientSession> removeClientSession(DatePoint datePoint, ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn);

    List<ClientSession> getClientSessionsList(DatePoint datePoint, User currentUser, boolean isShowRemoved, boolean showPayedOn);

    List<ClientSession> stopClientSession(DatePoint datePoint, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed);

    List<ClientSession> payClientSession(DatePoint datePoint, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed);

    List<SessionPseudoName> getAllPseudoNames();

    void addName(SessionPseudoName namesTextBoxValue);

    void removeName(SessionPseudoName sessionPseudoName);

    User getCurrentUser(String userName, String userPassword);

    User saveUser(User user);

    User login(String userName, String userPassword);

    List<ClientSession> startClientSession(DatePoint datePoint, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed);

    List<ClientSession> unlimClientSession(DatePoint currentDatePointValue, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed);
}
