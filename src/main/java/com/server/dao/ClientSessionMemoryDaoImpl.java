package com.server.dao;

import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;
import com.shared.model.SettingsHolder;
import com.shared.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientSessionMemoryDaoImpl implements ClientSessionDao{
    Map<String,SessionPseudoName> pseudoNamesMap = new HashMap<>();
    Map<Long, ClientSession> clientSessionMap = new HashMap<>();
    Map<Long, User> usersMap = new HashMap<>();
    Map<Long, SettingsHolder> settingsHolderMap = new HashMap<>();

    public ClientSessionMemoryDaoImpl() {
        User testUser = new User();
        testUser.setUserId(0l);
        testUser.setUserName("a");
        testUser.setPassword("");
        usersMap.put(testUser.getUserId(), testUser);

        SettingsHolder testSettingsHolder = new SettingsHolder();
        testSettingsHolder.setFirstPartLength(20000l);
        testSettingsHolder.setFirstPartSumAmount(3500l);
        testSettingsHolder.setSettingsId(0l);
        testSettingsHolder.setUser(testUser);
        settingsHolderMap.put(testSettingsHolder.getSettingsId(), testSettingsHolder);
    }

    @Override
    public List<SessionPseudoName> getFreePseudoNames() {
        List<SessionPseudoName> freeNames = new ArrayList<>();
        for (String key : pseudoNamesMap.keySet()) {
            if (!pseudoNamesMap.get(key).isUsed()) {
                freeNames.add(pseudoNamesMap.get(key));
            }
        }
        return freeNames;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void markNameAsFree(SessionPseudoName name) {
        this.pseudoNamesMap.get(name.getName()).setIsUsed(false);
    }

    @Override
    public void markNameAsUsed(SessionPseudoName name) {
        this.pseudoNamesMap.get(name.getName()).setIsUsed(true);
    }

    @Override
    public void addNames(List<SessionPseudoName> pseudoNamesList) {
        for (SessionPseudoName name : pseudoNamesList) {
            this.pseudoNamesMap.put(name.getName(), name);
        }
    }

    @Override
    public Long saveClientSession(ClientSession clientSession) {
        clientSession.setId(getMaxId() + 1);
        this.clientSessionMap.put(clientSession.getId(), clientSession);
        return clientSession.getId();
    }

    @Override
    public void removeClientSession(ClientSession clientSession) {
        ClientSession sessionToRemove = this.clientSessionMap.get(clientSession.getId());
        if (sessionToRemove != null) {
            sessionToRemove.setStatus(ClientSession.SESSION_STATUS.REMOVED);
        }
    }

    @Override
    public List<ClientSession> getClientSessionsList() {
        List<ClientSession> clientSessions = new ArrayList<>();
        for (ClientSession clientSession : clientSessionMap.values()) {
            clientSessions.add(clientSession);
        }
        return clientSessions;
    }

    @Override
    public long stopClientSession(ClientSession clientSession) {
        ClientSession session = this.clientSessionMap.get(clientSession.getId());
        session.setStatus(ClientSession.SESSION_STATUS.STOPPED);
        session.setStopTime(clientSession.getStopTime());
        return session.getId();
    }

    @Override
    public long payClientSession(ClientSession clientSession) {
        ClientSession session = this.clientSessionMap.get(clientSession.getId());
        session.setStatus(ClientSession.SESSION_STATUS.PAYED);
        return session.getId();
    }

    @Override
    public List<SessionPseudoName> getAllPseudoNames() {
        List<SessionPseudoName> allNames = new ArrayList<>();
        for (String key : pseudoNamesMap.keySet()) {
            allNames.add(pseudoNamesMap.get(key));
        }
        return allNames;
    }

    @Override
    public void addName(SessionPseudoName namesTextBoxValue) {
        this.pseudoNamesMap.put(namesTextBoxValue.getName(), namesTextBoxValue);
    }

    @Override
    public void removeName(SessionPseudoName sessionPseudoName) {
        this.pseudoNamesMap.remove(sessionPseudoName.getName());
    }

    @Override
    public User getCurrentUser(String userName, String userPassword) {
        for (Long key : usersMap.keySet()) {
            User userFromMap = usersMap.get(key);
            if (userFromMap != null && userFromMap.getUserName().equals(userName) &&
                    userFromMap.getPassword().equals(userPassword)) {
                for (Long settingsKey : settingsHolderMap.keySet()) {
                    SettingsHolder settingsHolder = settingsHolderMap.get(settingsKey);
                    if (settingsHolder.getUser().equals(userFromMap)) {
                        userFromMap.setSettings(settingsHolder);
                        return userFromMap;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public User saveUser(User user) {
        User savedUser = usersMap.get(user.getUserId());
        SettingsHolder settingsHolder = savedUser.getSettings();
        settingsHolder.setFirstPartLength(user.getSettings().getFirstPartLength());
        settingsHolder.setFirstPartSumAmount(user.getSettings().getFirstPartSumAmount());
        return savedUser;
    }

    private long getMaxId() {
        long maxId = 0;
        for (Long key : clientSessionMap.keySet()) {
            if (key > maxId) {
                maxId = key;
            }
        }
        return maxId;
    }
//    @Override
//    public void saveClientSession(ClientSession clientSession) {
//        ObjectifyService.ofy().save().entity(clientSession);//To change body of implemented methods use File | Settings | File Templates.
//    }
}
