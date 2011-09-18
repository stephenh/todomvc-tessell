package org.gwtmpv.todomvc.client;

import org.gwtmpv.todomvc.client.app.AppPresenter;
import org.gwtmpv.todomvc.client.resources.AppResources;
import org.gwtmpv.todomvc.client.views.AppViews;
import org.gwtmpv.todomvc.client.views.GwtViewsProvider;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class TodoMvcModule implements EntryPoint {

  @Override
  public void onModuleLoad() {
    AppResources r = GWT.create(AppResources.class);
    r.reset().ensureInjected();
    r.todo().ensureInjected();
    r.uitooltip().ensureInjected();

    AppViews.setProvider(new GwtViewsProvider(r.todo(), r.uitooltip()));
    AppPresenter p = new AppPresenter();
    p.bind();
    RootPanel.get().add(p.getView().asWidget());
  }

}
