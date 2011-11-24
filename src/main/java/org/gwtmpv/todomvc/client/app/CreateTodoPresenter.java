package org.gwtmpv.todomvc.client.app;

import static org.gwtmpv.todomvc.client.views.AppViews.newCreateTodoView;

import org.gwtmpv.model.properties.ListProperty;
import org.gwtmpv.presenter.BasicPresenter;
import org.gwtmpv.todomvc.client.model.Todo;
import org.gwtmpv.todomvc.client.views.IsCreateTodoView;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

/** Creates new tasks when the user adds them. */
public class CreateTodoPresenter extends BasicPresenter<IsCreateTodoView> {

  private final ListProperty<Todo> allTodos;

  public CreateTodoPresenter(ListProperty<Todo> allTodos) {
    super(newCreateTodoView());
    this.allTodos = allTodos;
  }

  @Override
  public void onBind() {
    super.onBind();
    view.newTodo().addKeyDownHandler(new KeyDownHandler() {
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          String newName = view.newTodo().getText();
          if ("".equals(newName)) {
            return;
          }
          Todo task = new Todo(newName);
          allTodos.add(task);
          view.newTodo().setText(null);
        }
      }
    });
    // GWT doesn't have setPlaceholder yet
    view.newTodo().getIsElement().setAttribute("placeholder", "What needs to be done?");
  }

}
