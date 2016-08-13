package com.server.dao;

import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
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
public class ClientSessionNoSqlDaoImpl implements ClientSessionDao{
//    Map<String,SessionPseudoName> pseudoNamesMap = new HashMap<>();
//    Map<Long, ClientSession> clientSessionMap = new HashMap<>();
    @Override
    public List<SessionPseudoName> getFreePseudoNames() {
        List<SessionPseudoName> freeNames = ObjectifyService.ofy().load().type(SessionPseudoName.class).
                filter(new Query.FilterPredicate("isUsed", Query.FilterOperator.EQUAL, "false")).list();//To change body of implemented methods use File | Settings | File Templates.
        return freeNames;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void markNameAsFree(SessionPseudoName name) {
        name.setIsUsed(false);
        ObjectifyService.ofy().save().entity(name);
    }

    @Override
    public void markNameAsUsed(SessionPseudoName name) {
        name.setIsUsed(true);
        ObjectifyService.ofy().save().entity(name);
    }

    @Override
    public void addNames(List<SessionPseudoName> pseudoNamesList) {
        for (SessionPseudoName name : pseudoNamesList) {
            ObjectifyService.ofy().save().entity(name);
        }
    }

    @Override
    public Long saveClientSession(ClientSession clientSession) {
        Result<Key<ClientSession>> clientSessionResult = ObjectifyService.ofy().save().entity(clientSession);
        return clientSessionResult.now().getId();
    }

    @Override
    public void removeClientSession(ClientSession clientSession) {

    }

    @Override
    public List<ClientSession> getClientSessionsList() {
        return null;
    }

    @Override
    public long stopClientSession(ClientSession clientSession) {
        return 0;
    }

    @Override
    public long payClientSession(ClientSession clientSession) {
        return 0;
    }
}
