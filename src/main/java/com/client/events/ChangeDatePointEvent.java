package com.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.shared.model.DatePoint;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChangeDatePointEvent extends GwtEvent<ChangeDatePointEventHandler> {
    public static Type<ChangeDatePointEventHandler> TYPE = new Type<ChangeDatePointEventHandler>();
    private DatePoint datePoint;

    public DatePoint getDatePoint() {
        return datePoint;
    }

    public void setDatePoint(DatePoint datePoint) {
        this.datePoint = datePoint;
    }

    @Override
    public Type<ChangeDatePointEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ChangeDatePointEventHandler handler) {
        handler.changeDatePoint(this);
    }
}
