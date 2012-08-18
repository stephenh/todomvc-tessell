package org.tessell.todomvc.client.app;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import static org.tessell.model.dsl.Binder.onClick;
import static org.tessell.model.dsl.Binder.onDoubleClick;
import static org.tessell.model.dsl.Binder.onKeyDown;
import static org.tessell.model.dsl.Binder.when;
import static org.tessell.model.dsl.TakesValues.textOf;
import static org.tessell.model.properties.NewProperty.booleanProperty;
import static org.tessell.todomvc.client.views.AppViews.newListTodoItemView;

import org.tessell.model.properties.BooleanProperty;
import org.tessell.presenter.BasicPresenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.IsListTodoItemView;
import org.tessell.todomvc.client.views.ListTodoItemStyle;

public class ListTodoItemPresenter extends BasicPresenter<IsListTodoItemView> {

  private final BooleanProperty editing = booleanProperty("editing", false);
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

    when(editing).is(true).show(view.editPanel());
    when(editing).is(true).hide(view.displayPanel());
    when(editing).is(true).set(s.editing()).on(view.li());

    bind(todo.name).to(view.editBox());
    bind(todo.name).to(textOf(view.content()));

    bind(todo.done).to(view.checkBox());
    when(todo.done).is(true).set(s.done()).on(view.li());

    onDoubleClick(view.content()).set(editing).to(true);
    onDoubleClick(view.content()).focus(view.editBox());

    onKeyDown(view.editBox(), KEY_ENTER, KEY_ESCAPE).set(editing).to(false);

    onClick(view.destroyAnchor()).remove(todo).from(state.allTodos);
  }

}
