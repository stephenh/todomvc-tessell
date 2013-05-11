package org.tessell.todomvc.client.app;

import static org.tessell.todomvc.client.views.AppViews.newMainView;

import org.tessell.model.dsl.ListPropertyBinder.ListPresenterFactory;
import org.tessell.presenter.BasicPresenter;
import org.tessell.presenter.Presenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.IsMainView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

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

    binder.bind(state.doneTodos.is(state.allTodos)).to(view.toggleAll());

    view.toggleAll().addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        boolean newValue = !view.toggleAll().getValue();
        for (Todo todo : state.allTodos.get()) {
          todo.done.set(newValue);
        }
      }
    });
  }

}
