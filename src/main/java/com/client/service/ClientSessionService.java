package com.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.model.ClientSession;
import com.shared.model.SessionPseudoName;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/9/16
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
@RemoteServiceRelativePath("clientSession")
public interface ClientSessionService extends RemoteService {
    public List<SessionPseudoName> getFreePseudoNames();
    public void markNameAsFree(SessionPseudoName name);
    public void markNameAsUsed(SessionPseudoName name);

    void addNames(List<SessionPseudoName> pseudoNamesList);

    void saveClientSession(ClientSession clientSession);
}
