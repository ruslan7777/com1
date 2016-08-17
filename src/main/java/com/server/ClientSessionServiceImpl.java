package com.server;

import com.client.service.ClientSessionService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.server.dao.ClientSessionDao;
import com.server.dao.ClientSessionMemoryDaoImpl;
import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;
import com.shared.model.User;
import com.sun.webkit.LoadListenerClient;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/9/16
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientSessionServiceImpl extends RemoteServiceServlet implements ClientSessionService {
    private ClientSessionDao clientSessionDao = new ClientSessionMemoryDaoImpl();
    @Override
    public List<SessionPseudoName> getFreePseudoNames() {
        return clientSessionDao.getFreePseudoNames();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void markNameAsFree(SessionPseudoName name) {
        clientSessionDao.markNameAsFree(name);
    }

    @Override
    public void markNameAsUsed(SessionPseudoName name) {
        clientSessionDao.markNameAsUsed(name);
    }

    @Override
    public void addNames(List<SessionPseudoName> pseudoNamesList) {
        clientSessionDao.addNames(pseudoNamesList);
    }

    @Override
    public List<ClientSession> saveClientSession(ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn) {
        return clientSessionDao.saveClientSession(clientSession, isShowRemoved, showPayedOn);//To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ClientSession> removeClientSession(ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn) {
        return clientSessionDao.removeClientSession(clientSession, isShowRemoved, showPayedOn);
    }

    @Override
    public List<ClientSession> getClientSessions(User currentUser, boolean isShowRemoved, boolean showPayedOn) {
        return clientSessionDao.getClientSessionsList(currentUser, isShowRemoved, showPayedOn);
    }

    @Override
    public List<ClientSession> stopClientSession(ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        return clientSessionDao.stopClientSession(clientSession, toShowRemoved, toShowPayed);
    }

    @Override
    public List<ClientSession> payClientSession(ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        return clientSessionDao.payClientSession(clientSession, toShowRemoved, toShowPayed);
    }

    @Override
    public List<SessionPseudoName> getAllPseudoNames() {
        return clientSessionDao.getAllPseudoNames();
    }

    @Override
    public void addName(SessionPseudoName namesTextBoxValue) {
        clientSessionDao.addName(namesTextBoxValue);
    }

    @Override
    public void removeName(SessionPseudoName sessionPseudoName) {
        clientSessionDao.removeName(sessionPseudoName);
    }

    @Override
    public User getCurrentUser(String userName, String userPassword) {
        return clientSessionDao.getCurrentUser(userName, userPassword);
    }

    @Override
    public User saveUser(User user) {
        return clientSessionDao.saveUser(user);
    }

    @Override
    public User login(String userName, String userPassword) {
        return clientSessionDao.login(userName, userPassword);
    }

    @Override
    public List<ClientSession> startClientSession(ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        return clientSessionDao.startClientSession(clientSession, toShowRemoved, toShowPayed);
    }
}
