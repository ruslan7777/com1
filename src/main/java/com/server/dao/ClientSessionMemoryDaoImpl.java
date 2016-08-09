package com.server.dao;

import com.googlecode.objectify.ObjectifyService;
import com.shared.model.ClientSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientSessionMemoryDaoImpl implements ClientSessionDao{
    List<String> pseudoNames = new ArrayList<>();
    @Override
    public List<String> getPseudoNames() {
        return pseudoNames;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addName(String name) {
        this.pseudoNames.add(name);//To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeName(String name) {
        this.pseudoNames.remove(name);//To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addNames(List<String> pseudoNamesList) {
        this.pseudoNames.addAll(pseudoNamesList);
    }

    @Override
    public void saveClientSession(ClientSession clientSession) {
        ObjectifyService.ofy().save().entity(clientSession);//To change body of implemented methods use File | Settings | File Templates.
    }
}
