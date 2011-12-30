package org.tessell.todomvc.client.app;

import static org.tessell.todomvc.client.views.AppViews.newAppView;
import static org.tessell.todomvc.client.views.AppViews.newCreditsView;

import org.tessell.presenter.BasicPresenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.views.IsAppView;

public class AppPresenter extends BasicPresenter<IsAppView> {

  private final AppState state = new AppState();
  private final CreateTodoPresenter createTodo = addPresenter(new CreateTodoPresenter(state.allTodos));
  private final StatsPresenter statsTodo = addPresenter(new StatsPresenter(state));
  private final ListTodoPresenter listTodos = addPresenter(new ListTodoPresenter(state));

  public AppPresenter() {
    super(newAppView());
  }

  @Override
  public void onBind() {
    super.onBind();
    view.createPanel().add(createTodo.getView());
    view.listPanel().add(listTodos.getView());
    view.statsPanel().add(statsTodo.getView());
    view.creditsPanel().add(newCreditsView());
  }

}
