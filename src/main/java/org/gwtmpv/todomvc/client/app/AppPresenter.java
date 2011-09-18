package org.gwtmpv.todomvc.client.app;

import static org.gwtmpv.todomvc.client.views.AppViews.newAppView;
import static org.gwtmpv.todomvc.client.views.AppViews.newCreditsView;

import org.gwtmpv.presenter.BasicPresenter;
import org.gwtmpv.todomvc.client.model.AppState;
import org.gwtmpv.todomvc.client.views.IsAppView;

public class AppPresenter extends BasicPresenter<IsAppView> {

  private final AppState state = new AppState();
  private final CreateTodoPresenter createTodo;
  private final StatsTodoPresenter statsTodo;
  private final ListTodoPresenter listTodos;

  public AppPresenter() {
    super(newAppView());
    createTodo = addPresenter(new CreateTodoPresenter(state.allTodos));
    statsTodo = addPresenter(new StatsTodoPresenter(state));
    listTodos = addPresenter(new ListTodoPresenter(state));
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
