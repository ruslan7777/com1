package com.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToggleShowPayedEvent extends GwtEvent<ToggleShowPayedEventHandler> {
    public static Type<ToggleShowPayedEventHandler> TYPE = new Type<ToggleShowPayedEventHandler>();
    private boolean isShowPayedOn;
    private boolean isShowRemovedCurrentState;

    @Override
    public Type<ToggleShowPayedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ToggleShowPayedEventHandler handler) {
        handler.toggleShowPayed(this);
    }

    public boolean isShowPayedOn() {
        return isShowPayedOn;
    }

    public void setIsShowPayedOn(boolean isShowPayedOn) {
        this.isShowPayedOn = isShowPayedOn;
    }

    public boolean isShowRemovedCurrentState() {
        return isShowRemovedCurrentState;
    }

    public void setIsShowRemovedCurrentState(boolean isShowRemovedCurrentState) {
        this.isShowRemovedCurrentState = isShowRemovedCurrentState;
    }
}
