package org.tessell.todomvc.client.app;

import static org.tessell.todomvc.client.views.AppViews.newMainView;

import org.tessell.model.dsl.ListPropertyBinder.ListPresenterFactory;
import org.tessell.presenter.BasicPresenter;
import org.tessell.presenter.Presenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.IsMainView;

public class MainPresenter extends BasicPresenter<IsMainView> {

  private final AppState state;

  public MainPresenter(AppState state) {
    super(newMainView());
    this.state = state;
  }

  @Override
  public void onBind() {
    super.onBind();
    binder.bind(state.allTodos).to(this, view.ul(), new ListPresenterFactory<Todo>() {
      public Presenter create(Todo todo) {
        return new TodoPresenter(state, todo);
      }
    });
  }

}
