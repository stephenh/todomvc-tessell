package org.tessell.todomvc.client.app;

import org.tessell.presenter.Presenter;
import org.tessell.todomvc.client.resources.StubBaseStyle;
import org.tessell.todomvc.client.views.StubViewsProvider;

public abstract class AbstractPresenterTest {
  
  static {
    StubViewsProvider.install(new StubBaseStyle());
  }
  
  protected static <P extends Presenter> P bind(P p) {
    p.bind();
    return p;
  }

}
