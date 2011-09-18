package org.gwtmpv.todomvc.client.app;

import org.gwtmpv.presenter.Presenter;
import org.gwtmpv.todomvc.client.views.StubViewsProvider;

public abstract class AbstractPresenterTest {
  
  static {
    StubViewsProvider.install();
  }
  
  protected static <P extends Presenter> P bind(P p) {
    p.bind();
    return p;
  }

}
