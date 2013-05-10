package org.tessell.todomvc.client.app;

import static org.tessell.todomvc.client.views.AppViews.newAppView;
import static org.tessell.todomvc.client.views.AppViews.newInfoView;

import org.tessell.presenter.BasicPresenter;
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

    view.root().addAndReplaceElement(//
      addPresenter(new HeaderPresenter(state.allTodos)).getView(),
      view.headerPlaceholder());

    view.root().addAndReplaceElement(//
      addPresenter(new MainPresenter(state)).getView(),
      view.mainPlaceholder());

    view.root().addAndReplaceElement(//
      addPresenter(new FooterPresenter(state)).getView(),
      view.footerPlaceholder());

    view.root().addAndReplaceElement(//
      newInfoView(),
      view.infoPlaceholder());

    binder.when(state.allTodos.size()).is(0).hide(view.main());
    binder.when(state.allTodos.size()).is(0).hide(view.footer());
  }

}
