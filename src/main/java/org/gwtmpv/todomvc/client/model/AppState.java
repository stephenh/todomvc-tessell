package org.gwtmpv.todomvc.client.model;

import static org.gwtmpv.model.properties.NewProperty.integerProperty;
import static org.gwtmpv.model.properties.NewProperty.listProperty;

import org.gwtmpv.model.properties.IntegerProperty;
import org.gwtmpv.model.properties.ListProperty;
import org.gwtmpv.model.values.DerivedValue;

public class AppState {

  // notably crappy encapsulation
  public final ListProperty<Todo> allTodos = listProperty("allTodos");
  public final ListProperty<Todo> doneTodos = listProperty("doneTodos");
  public final IntegerProperty numberLeft;

  public AppState() {
    numberLeft = integerProperty(new DerivedValue<Integer>() {
      public Integer get() {
        return allTodos.get().size() - doneTodos.get().size();
      }
    });
  }

  public void removeDone() {
    for (Todo done : doneTodos.get()) {
      allTodos.remove(done);
    }
    doneTodos.clear();
  }

  public void destroy(Todo todo) {
    allTodos.remove(todo);
    doneTodos.remove(todo);
  }

}
