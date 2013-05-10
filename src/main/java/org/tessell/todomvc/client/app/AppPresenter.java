package org.tessell.todomvc.client.app;

import static org.tessell.todomvc.client.views.AppViews.newAppView;
import static org.tessell.todomvc.client.views.AppViews.newInfoView;

import org.tessell.presenter.BasicPresenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.views.IsAppView;

public class AppPresenter extends BasicPresenter<IsAppView> {

  private final AppState state = new AppState();
  private final HeaderPresenter createTodo = addPresenter(new HeaderPresenter(state.allTodos));
  private final FooterPresenter statsTodo = addPresenter(new FooterPresenter(state));
  private final MainPresenter listTodos = addPresenter(new MainPresenter(state));

  public AppPresenter() {
    super(newAppView());
  }

  @Override
  public void onBind() {
    super.onBind();
    view.root().addAndReplaceElement(createTodo, view.headerPlaceholder());
    view.root().addAndReplaceElement(listTodos, view.mainPlaceholder());
    view.root().addAndReplaceElement(statsTodo, view.footerPlaceholder());
    view.root().addAndReplaceElement(newInfoView(), view.infoPlaceholder());
  }

}
