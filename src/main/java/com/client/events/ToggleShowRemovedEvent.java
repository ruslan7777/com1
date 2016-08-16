package com.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToggleShowRemovedEvent extends GwtEvent<ToggleShowRemovedEventHandler> {
    public static Type<ToggleShowRemovedEventHandler> TYPE = new Type<ToggleShowRemovedEventHandler>();
    private boolean isShowRemovedOn;
    private boolean isShowPayedCurrentState;




    @Override
    public Type<ToggleShowRemovedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ToggleShowRemovedEventHandler handler) {
        handler.toggleShowRemoved(this);
    }

    public boolean isShowRemovedOn() {
        return isShowRemovedOn;
    }

    public void setIsShowRemovedOn(boolean isShowRemovedOn) {
        this.isShowRemovedOn = isShowRemovedOn;
    }

    public boolean isShowPayedCurrentState() {
        return isShowPayedCurrentState;
    }

    public void setIsShowPayedCurrentState(boolean isShowPayedCurrentState) {
        this.isShowPayedCurrentState = isShowPayedCurrentState;
    }
}
