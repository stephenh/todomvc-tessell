package org.tessell.todomvc.client.app;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import static org.tessell.model.dsl.TakesValues.textOf;
import static org.tessell.model.properties.NewProperty.booleanProperty;
import static org.tessell.todomvc.client.views.AppViews.newListTodoItemView;

import org.tessell.model.dsl.Binder;
import org.tessell.model.properties.BooleanProperty;
import org.tessell.presenter.BasicPresenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.IsListTodoItemView;
import org.tessell.todomvc.client.views.ListTodoItemStyle;

public class ListTodoItemPresenter extends BasicPresenter<IsListTodoItemView> {

  private final BooleanProperty editing = booleanProperty("editing", false);
  private final Binder binder = new Binder();
  private final AppState state;
  private final Todo todo;
  private final ListTodoItemStyle s;

  public ListTodoItemPresenter(AppState state, Todo todo) {
    super(newListTodoItemView());
    this.state = state;
    this.todo = todo;
    s = view.style();
  }

  @Override
  public void onBind() {
    super.onBind();

    binder.when(editing).is(true).show(view.editPanel());
    binder.when(editing).is(true).hide(view.displayPanel());
    binder.when(editing).is(true).set(s.editing()).on(view.li());

    binder.bind(todo.name).to(view.editBox());
    binder.bind(todo.name).to(textOf(view.content()));

    binder.bind(todo.done).to(view.checkBox());
    binder.when(todo.done).is(true).set(s.done()).on(view.li());

    binder.onDoubleClick(view.content()).set(editing).to(true);
    binder.onDoubleClick(view.content()).focus(view.editBox());

    binder.onKeyDown(view.editBox(), KEY_ENTER, KEY_ESCAPE).set(editing).to(false);

    binder.onClick(view.destroyAnchor()).remove(todo).from(state.allTodos);
  }

}
