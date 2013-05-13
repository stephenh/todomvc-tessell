package org.tessell.todomvc.client.app;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static org.tessell.todomvc.client.views.AppViews.newHeaderView;
import static org.tessell.util.StringUtils.sanitizeIfString;

import org.tessell.model.properties.ListProperty;
import org.tessell.presenter.BasicPresenter;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.IsHeaderView;

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
    binder.onKeyDown(view.newTodo(), KEY_ENTER).execute(new Runnable() {
      public void run() {
        String newName = sanitizeIfString(view.newTodo().getText());
        if (newName != null) {
          allTodos.add(new Todo(newName));
          view.newTodo().setText(null);
        }
      }
    });
    // GWT doesn't have setPlaceholder yet
    view.newTodo().getIsElement().setAttribute("placeholder", "What needs to be done?");
  }

}
