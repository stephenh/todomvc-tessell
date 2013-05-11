package org.tessell.todomvc.client.app;

import static org.tessell.todomvc.client.views.AppViews.newAppView;
import static org.tessell.todomvc.client.views.AppViews.newInfoView;

import org.tessell.gwt.dom.client.IsElement;
import org.tessell.presenter.BasicPresenter;
import org.tessell.presenter.Presenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.views.IsAppView;

public class AppPresenter extends BasicPresenter<IsAppView> {

  private final AppState state;

  public AppPresenter(AppState state) {
    super(newAppView());
    this.state = state;
  }

  @Override
  public void onBind() {
    super.onBind();

    add(new HeaderPresenter(state.allTodos), view.headerPlaceholder());
    add(new MainPresenter(state), view.mainPlaceholder());
    add(new FooterPresenter(state), view.footerPlaceholder());
    view.root().addAndReplaceElement(newInfoView(), view.infoPlaceholder());

    binder.when(state.allTodos.size()).is(0).hide(view.main());
    binder.when(state.allTodos.size()).is(0).hide(view.footer());
  }

  private void add(Presenter p, IsElement placeholder) {
    view.root().addAndReplaceElement(addPresenter(p).getView(), placeholder);
  }

}
