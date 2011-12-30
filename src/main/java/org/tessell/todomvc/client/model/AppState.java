package org.tessell.todomvc.client.model;

import static org.tessell.model.properties.NewProperty.integerProperty;
import static org.tessell.model.properties.NewProperty.listProperty;

import org.tessell.model.properties.IntegerProperty;
import org.tessell.model.properties.ListProperty;
import org.tessell.model.values.DerivedValue;

public class AppState {

  // notably crappy encapsulation
  public final ListProperty<Todo> allTodos = listProperty("allTodos");
  public final ListProperty<Todo> doneTodos = listProperty("doneTodos");
  public final IntegerProperty numberLeft = integerProperty(new DerivedValue<Integer>() {
    public Integer get() {
      return allTodos.get().size() - doneTodos.get().size();
    }
  });

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
