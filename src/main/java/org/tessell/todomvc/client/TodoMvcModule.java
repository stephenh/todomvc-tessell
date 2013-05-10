package org.tessell.todomvc.client;

import org.tessell.todomvc.client.app.AppPresenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.resources.AppResources;
import org.tessell.todomvc.client.resources.AppResourcesUtil;
import org.tessell.todomvc.client.views.AppViews;
import org.tessell.todomvc.client.views.GwtViewsProvider;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class TodoMvcModule implements EntryPoint {

  @Override
  public void onModuleLoad() {
    AppResources r = GWT.create(AppResources.class);
    AppResourcesUtil.injectAll(r);

    AppViews.setProvider(new GwtViewsProvider(r.base()));
    AppState state = new AppState();
    AppPresenter p = new AppPresenter(state);
    p.bind();
    RootPanel.get().add(p);
  }

}
