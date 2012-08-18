package org.tessell.todomvc.client.app;

import static org.tessell.todomvc.client.views.AppViews.newListTodoView;

import org.tessell.model.dsl.ListPropertyBinder.ListPresenterFactory;
import org.tessell.presenter.BasicPresenter;
import org.tessell.presenter.Presenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.IsListTodoView;

public class ListTodoPresenter extends BasicPresenter<IsListTodoView> {

  private final AppState state;

  public ListTodoPresenter(AppState state) {
    super(newListTodoView());
    this.state = state;
  }

  @Override
  public void onBind() {
    super.onBind();
    bind(state.allTodos).to(this, view.ul(), new ListPresenterFactory<Todo>() {
      public Presenter create(Todo todo) {
        return new ListTodoItemPresenter(state, todo);
      }
    });
  }

}
