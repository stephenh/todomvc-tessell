package org.tessell.todomvc.client.app;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import static org.tessell.model.dsl.TakesValues.innerTextOf;
import static org.tessell.model.properties.NewProperty.booleanProperty;
import static org.tessell.todomvc.client.views.AppViews.newTodoView;

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

    binder.bind(todo.name).to(view.editBox());
    binder.bind(todo.name).to(innerTextOf(view.label()));

    binder.bind(todo.done).to(view.checkBox());
    binder.when(todo.done).is(true).set(bs.completed()).on(view.li());

    binder.onDoubleClick(wrap(view.label())).set(editing).to(true);
    binder.onDoubleClick(wrap(view.label())).focus(view.editBox());

    binder.onKeyDown(view.editBox(), KEY_ENTER, KEY_ESCAPE).set(editing).to(false);

    binder.onClick(view.destroy()).remove(todo).from(state.allTodos);
  }
  
  // Hack to enable double-clicking on a widget that doesn't implement HasDoubleClickHandlers
  private static HasDoubleClickHandlers wrap(final IsWidget w) {
    return new HasDoubleClickHandlers() {
      public void fireEvent(GwtEvent<?> event) {
        w.fireEvent(event);
      }

      @Override
      public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
        return w.addDomHandler(handler, DoubleClickEvent.getType());
      }
    };
  }

}
