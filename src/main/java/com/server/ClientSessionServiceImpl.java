package com.server;

import com.client.service.ClientSessionService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.server.dao.ClientSessionDao;
import com.server.dao.ClientSessionMemoryDaoImpl;
import com.shared.model.ClientSession;

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
    public List<String> getPseudoNames  () {
        return clientSessionDao.getPseudoNames();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addName(String name) {
        clientSessionDao.addName(name);
    }

    @Override
    public void removeName(String name) {
        clientSessionDao.removeName(name);
    }

    @Override
    public void addNames(List<String> pseudoNamesList) {
        clientSessionDao.addNames(pseudoNamesList);
    }

    @Override
    public void saveClientSession(ClientSession clientSession) {
        clientSessionDao.saveClientSession(clientSession);//To change body of implemented methods use File | Settings | File Templates.
    }
}
