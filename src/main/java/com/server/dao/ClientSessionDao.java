package com.server.dao;

import com.shared.model.ClientSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientSessionDao {

    List<String> getPseudoNames();

    void addName(String name);

    void removeName(String name);

    void addNames(List<String> pseudoNamesList);

    void saveClientSession(ClientSession clientSession);
}
