package com.client.bundles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Images extends ClientBundle {
  public static final Images INSTANCE =  GWT.create(Images.class);

   @Source("images/progress.gif")
   ImageResource progress();

   @Source("images/remove.png")
   ImageResource remove();

   @Source("images/stopped.png")
   ImageResource stopped();

 @Source("images/unlimited.gif")
 ImageResource stopped_unlimited();

 }