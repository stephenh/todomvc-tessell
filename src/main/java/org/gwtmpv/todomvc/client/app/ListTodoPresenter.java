package org.gwtmpv.todomvc.client.app;

import static org.gwtmpv.todomvc.client.views.AppViews.newListTodoView;

import java.util.ArrayList;
import java.util.List;

import org.gwtmpv.model.events.ValueAddedEvent;
import org.gwtmpv.model.events.ValueAddedHandler;
import org.gwtmpv.model.events.ValueRemovedEvent;
import org.gwtmpv.model.events.ValueRemovedHandler;
import org.gwtmpv.presenter.BasicPresenter;
import org.gwtmpv.todomvc.client.model.AppState;
import org.gwtmpv.todomvc.client.model.Todo;
import org.gwtmpv.todomvc.client.views.IsListTodoView;

public class ListTodoPresenter extends BasicPresenter<IsListTodoView> {

  private final AppState state;
  private final List<ListTodoItemPresenter> items = new ArrayList<ListTodoItemPresenter>();

  public ListTodoPresenter(AppState state) {
    super(newListTodoView());
    this.state = state;
  }

  @Override
  public void onBind() {
    super.onBind();
    for (Todo todo : state.allTodos.get()) {
      addTodo(todo);
    }
    // add/remove lines for Todos that are added/removed
    state.allTodos.addValueAddedHandler(new ValueAddedHandler<Todo>() {
      public void onValueAdded(ValueAddedEvent<Todo> event) {
        addTodo(event.getValue());
      }
    });
    state.allTodos.addValueRemovedHandler(new ValueRemovedHandler<Todo>() {
      public void onValueRemoved(ValueRemovedEvent<Todo> event) {
        removeTodo(event.getValue());
      }
    });
  }

  private void addTodo(Todo todo) {
    ListTodoItemPresenter item = addPresenter(new ListTodoItemPresenter(state, todo));
    view.ul().add(item.getView());
    items.add(item);
  }

  private void removeTodo(Todo todo) {
    for (ListTodoItemPresenter item : items) {
      if (item.isFor(todo)) {
        view.ul().remove(item.getView());
        items.remove(item);
        break;
      }
    }
  }

}
