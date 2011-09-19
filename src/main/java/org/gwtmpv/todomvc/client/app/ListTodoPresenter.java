package org.gwtmpv.todomvc.client.app;

import static org.gwtmpv.todomvc.client.views.AppViews.newListTodoView;

import org.gwtmpv.model.dsl.Binder;
import org.gwtmpv.model.dsl.ListPropertyBinder.ListPresenterFactory;
import org.gwtmpv.presenter.BasicPresenter;
import org.gwtmpv.presenter.Presenter;
import org.gwtmpv.todomvc.client.model.AppState;
import org.gwtmpv.todomvc.client.model.Todo;
import org.gwtmpv.todomvc.client.views.IsListTodoView;

public class ListTodoPresenter extends BasicPresenter<IsListTodoView> {

  private final AppState state;
  private final Binder binder = new Binder(this);

  public ListTodoPresenter(AppState state) {
    super(newListTodoView());
    this.state = state;
  }

  @Override
  public void onBind() {
    super.onBind();
    binder.bind(state.allTodos).to(this, view.ul(), new ListPresenterFactory<Todo>() {
      public Presenter create(Todo todo) {
        return new ListTodoItemPresenter(state, todo);
      }
    });
  }

}
