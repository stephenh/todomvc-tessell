package org.tessell.todomvc.client.app;

import org.tessell.presenter.Presenter;
import org.tessell.todomvc.client.resources.StubTodoStyle;
import org.tessell.todomvc.client.resources.StubUitooltipStyle;
import org.tessell.todomvc.client.views.StubViewsProvider;

public abstract class AbstractPresenterTest {
  
  static {
    StubViewsProvider.install(new StubTodoStyle(), new StubUitooltipStyle());
  }
  
  protected static <P extends Presenter> P bind(P p) {
    p.bind();
    return p;
  }

}
