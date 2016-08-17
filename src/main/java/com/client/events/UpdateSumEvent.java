package com.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateSumEvent extends GwtEvent<UpdateSumEventHandler> {
    public static Type<UpdateSumEventHandler> TYPE = new Type<UpdateSumEventHandler>();

    private long sum;

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    @Override
    public Type<UpdateSumEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UpdateSumEventHandler handler) {
        handler.updateSum(this);
    }
}
