package org.tessell.todomvc.client.app;

import org.tessell.presenter.Presenter;
import org.tessell.todomvc.client.views.StubViewsProvider;

public abstract class AbstractPresenterTest {
  
  static {
    StubViewsProvider.install();
  }
  
  protected static <P extends Presenter> P bind(P p) {
    p.bind();
    return p;
  }

}
