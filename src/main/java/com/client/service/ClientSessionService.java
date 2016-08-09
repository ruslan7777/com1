package com.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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
    public List<String> getPseudoNames();
    public void addName(String name);
    public void removeName(String name);

    void addNames(List<String> pseudoNamesList);
}
