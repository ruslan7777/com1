package com.server.dao;

import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;
import com.shared.model.SettingsHolder;
import com.shared.model.User;
import com.shared.utils.UserUtils;

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

        ClientSession createdTestClientSession = new ClientSession(0l, 0l, false, testUser);
        createdTestClientSession.setId(0l);
        SessionPseudoName testSessionPseudoName = new SessionPseudoName("testName0");
        addName(testSessionPseudoName);
        testSessionPseudoName.setIsUsed(true);
        createdTestClientSession.setSessionPseudoName(testSessionPseudoName);
        clientSessionMap.put(createdTestClientSession.getId(), createdTestClientSession);
        ClientSession testClientSession1 = new ClientSession(System.currentTimeMillis(), 0l, false, testUser);
        testClientSession1.setId(1l);
        SessionPseudoName startedTestSessionPseudoName1 = new SessionPseudoName("testName1");
        addName(startedTestSessionPseudoName1);
        testSessionPseudoName.setIsUsed(true);
        testClientSession1.setSessionPseudoName(startedTestSessionPseudoName1);
        testClientSession1.setStatus(ClientSession.SESSION_STATUS.STARTED);
        clientSessionMap.put(testClientSession1.getId(), testClientSession1);
        ClientSession testClientSession2 = new ClientSession(System.currentTimeMillis() - 50000, System.currentTimeMillis(), false, testUser);
        testClientSession2.setId(2l);
        SessionPseudoName stoppedTeststSessionPseudoName2 = new SessionPseudoName("testName2");
        addName(stoppedTeststSessionPseudoName2);
        stoppedTeststSessionPseudoName2.setIsUsed(true);
        testClientSession2.setSessionPseudoName(stoppedTeststSessionPseudoName2);
        testClientSession2.setStatus(ClientSession.SESSION_STATUS.STOPPED);
        clientSessionMap.put(testClientSession2.getId(), testClientSession2);
        ClientSession testClientSession3 = new ClientSession(System.currentTimeMillis() - 50000, System.currentTimeMillis(), false, testUser);
        testClientSession3.setId(3l);
        SessionPseudoName payedTestSessionPseudoName3 = new SessionPseudoName("testName3");
        addName(payedTestSessionPseudoName3);
        payedTestSessionPseudoName3.setIsUsed(true);
        testClientSession3.setSessionPseudoName(payedTestSessionPseudoName3);
        testClientSession3.setStatus(ClientSession.SESSION_STATUS.PAYED);
        clientSessionMap.put(testClientSession3.getId(), testClientSession3);
        ClientSession removedTestClientSession4 = new ClientSession(System.currentTimeMillis() - 150000, System.currentTimeMillis(), false, testUser);
        removedTestClientSession4.setId(4l);
        SessionPseudoName removedTestSessionPseudoName4 = new SessionPseudoName("testName4");
        addName(removedTestSessionPseudoName4);
        removedTestSessionPseudoName4.setIsUsed(true);
        removedTestClientSession4.setSessionPseudoName(removedTestSessionPseudoName4);
        removedTestClientSession4.setStatus(ClientSession.SESSION_STATUS.REMOVED);
        clientSessionMap.put(removedTestClientSession4.getId(), removedTestClientSession4);
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
        clientSession.setUser(UserUtils.INSTANCE.getCurrentUser());
        markNameAsUsed(clientSession.getSessionPseudoName());
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
    public List<ClientSession> getClientSessionsList(User currentUser) {
        List<ClientSession> clientSessions = new ArrayList<>();
        for (ClientSession clientSession : clientSessionMap.values()) {
            if (clientSession.getUser() != null && clientSession.getUser().equals(currentUser)) {
                clientSessions.add(clientSession);
            }
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

    @Override
    public User login(String userName, String userPassword) {
        for (Long key : usersMap.keySet()) {
            User userFromMap = usersMap.get(key);
            if (userFromMap != null && userFromMap.getUserName().equals(userName) &&
                    userFromMap.getPassword().equals(userPassword)) {
                for (Long settingsKey : settingsHolderMap.keySet()) {
                    SettingsHolder settingsHolder = settingsHolderMap.get(settingsKey);
                    if (settingsHolder.getUser().equals(userFromMap)) {
                        userFromMap.setSettings(settingsHolder);
                        UserUtils.init();
                        UserUtils.INSTANCE.setCurrentUser(userFromMap);
                        return userFromMap;
                    }
                }
            }
        }
        return null;
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
