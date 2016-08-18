package com.server.dao;

import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.shared.model.ClientSession;
import com.shared.model.DatePoint;
import com.shared.model.SessionPseudoName;
import com.shared.model.User;
import com.shared.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

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
    public List<ClientSession> saveClientSession(DatePoint datePoint, ClientSession clientSession, boolean isShowRemoved, boolean isShowPayed) {
        Result<Key<ClientSession>> clientSessionResult = ObjectifyService.ofy().save().entity(clientSession);
        return getClientSessionsList(datePoint, UserUtils.INSTANCE.getCurrentUser(), isShowRemoved, isShowPayed);
    }

    @Override
    public List<ClientSession> removeClientSession(DatePoint datePoint, ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn) {
        return  getClientSessionsList(datePoint, UserUtils.INSTANCE.getCurrentUser(), isShowRemoved, showPayedOn);
    }

    @Override
    public List<ClientSession> getClientSessionsList(DatePoint datePoint, User currentUser, boolean isShowRemoved, boolean showPayedOn) {
        return null;
    }

    @Override
    public List<ClientSession> stopClientSession(DatePoint datePoint, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        return new ArrayList<>();
    }

    @Override
    public List<ClientSession> payClientSession(DatePoint datePoint, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        return new ArrayList<>();
    }

    @Override
    public List<SessionPseudoName> getAllPseudoNames() {
        return null;
    }

    @Override
    public void addName(SessionPseudoName namesTextBoxValue) {

    }

    @Override
    public void removeName(SessionPseudoName sessionPseudoName) {

    }

    @Override
    public User getCurrentUser(String userName, String userPassword) {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User login(String userName, String userPassword) {
        return null;
    }

    @Override
    public List<ClientSession> startClientSession(DatePoint datePoint, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        return new ArrayList<>();
    }
}
