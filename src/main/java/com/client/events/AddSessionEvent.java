package com.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddSessionEvent extends GwtEvent<AddSessionEventHandler> {
    public static Type<AddSessionEventHandler> TYPE = new Type<AddSessionEventHandler>();
    private String clientPseudoName;

    public String getClientPseudoName() {
        return clientPseudoName;
    }

    public void setClientPseudoName(String clientPseudoName) {
        this.clientPseudoName = clientPseudoName;
    }

    @Override
    public Type<AddSessionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddSessionEventHandler handler) {
        handler.addClientSession(this);
    }
}
