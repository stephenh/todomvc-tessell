package org.tessell.todomvc.client.app;

import static org.tessell.todomvc.client.views.AppViews.newHeaderView;

import org.tessell.model.properties.ListProperty;
import org.tessell.presenter.BasicPresenter;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.IsHeaderView;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

/** Creates new tasks when the user adds them. */
public class HeaderPresenter extends BasicPresenter<IsHeaderView> {

  private final ListProperty<Todo> allTodos;

  public HeaderPresenter(ListProperty<Todo> allTodos) {
    super(newHeaderView());
    this.allTodos = allTodos;
  }

  @Override
  public void onBind() {
    super.onBind();
    view.newTodo().addKeyDownHandler(new KeyDownHandler() {
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          String newName = view.newTodo().getText().trim();
          if ("".equals(newName)) {
            return;
          }
          allTodos.add(new Todo(newName));
          view.newTodo().setText(null);
        }
      }
    });
    // GWT doesn't have setPlaceholder yet
    view.newTodo().getIsElement().setAttribute("placeholder", "What needs to be done?");
  }

}
