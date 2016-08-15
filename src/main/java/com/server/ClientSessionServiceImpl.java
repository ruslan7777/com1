package com.server;

import com.client.service.ClientSessionService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.server.dao.ClientSessionDao;
import com.server.dao.ClientSessionMemoryDaoImpl;
import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;
import com.shared.model.User;

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
    public Long saveClientSession(ClientSession clientSession) {
        return clientSessionDao.saveClientSession(clientSession);//To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeClientSession(ClientSession clientSession) {
        clientSessionDao.removeClientSession(clientSession);
    }

    @Override
    public List<ClientSession> getClientSessions(User currentUser) {
        return clientSessionDao.getClientSessionsList(currentUser);
    }

    @Override
    public long stopClientSession(ClientSession clientSession) {
        return clientSessionDao.stopClientSession(clientSession);
    }

    @Override
    public long payClientSession(ClientSession clientSession) {
        return clientSessionDao.payClientSession(clientSession);
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
}
