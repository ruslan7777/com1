package com.server.dao;

import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;

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
