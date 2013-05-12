package org.tessell.todomvc.client.app;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import static org.tessell.model.dsl.TakesValues.innerTextOf;
import static org.tessell.model.dsl.WhenConditions.nullValue;
import static org.tessell.model.properties.NewProperty.booleanProperty;
import static org.tessell.todomvc.client.views.AppViews.newTodoView;
import static org.tessell.util.StringUtils.sanitizeIfString;

import org.tessell.gwt.user.client.ui.IsWidget;
import org.tessell.model.properties.BooleanProperty;
import org.tessell.presenter.BasicPresenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.resources.BaseStyle;
import org.tessell.todomvc.client.views.IsTodoView;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class TodoPresenter extends BasicPresenter<IsTodoView> {

  private final BooleanProperty editing = booleanProperty("editing", false);
  private final BaseStyle bs = view.bs();
  private final AppState state;
  private final Todo todo;

  public TodoPresenter(AppState state, Todo todo) {
    super(newTodoView());
    this.state = state;
    this.todo = todo;
  }

  @Override
  public void onBind() {
    super.onBind();

    binder.when(editing).is(true).set(bs.editing()).on(view.li());

    binder.bind(todo.name).to(innerTextOf(view.label()));
    binder.when(todo.name).is(nullValue()).remove(todo).from(state.allTodos);

    binder.bind(todo.done).to(view.checkBox());
    binder.when(todo.done).is(true).set(bs.completed()).on(view.li());

    binder.onDoubleClick(view.label()).set(editing).to(true);
    binder.onDoubleClick(view.label()).focus(view.editBox());

    // reset the editBox to todo.name each time we start editing
    binder.when(editing).is(true).set(view.editBox()).to(todo.name);

    binder.onKeyDown(view.editBox(), KEY_ESCAPE).set(editing).to(false);
    binder.onKeyDown(view.editBox(), KEY_ENTER).execute(saveNewValue);
    binder.onBlur(view.editBox()).execute(saveNewValue);

    binder.onClick(view.destroy()).remove(todo).from(state.allTodos);
  }

  private final Runnable saveNewValue = new Runnable() {
    public void run() {
      // check for editing in case the user already hit escape
      if (editing.isTrue()) {
        todo.name.set(sanitizeIfString(view.editBox().getValue()));
        editing.set(false);
      }
    }
  };

}
