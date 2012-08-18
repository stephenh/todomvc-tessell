package org.tessell.todomvc.client.model;

import static org.tessell.model.properties.NewProperty.integerProperty;
import static org.tessell.model.properties.NewProperty.listProperty;

import java.util.ArrayList;

import org.tessell.model.properties.IntegerProperty;
import org.tessell.model.properties.ListProperty;
import org.tessell.model.properties.ListProperty.ElementFilter;
import org.tessell.model.values.DerivedValue;

public class AppState {

  public final ListProperty<Todo> allTodos = listProperty("allTodos");

  public final ListProperty<Todo> doneTodos = allTodos.filter(new ElementFilter<Todo>() {
    public boolean matches(Todo todo) {
      return todo.done.isTrue();
    }
  });

  public final IntegerProperty numberLeft = integerProperty(new DerivedValue<Integer>() {
    public Integer get() {
      return allTodos.get().size() - doneTodos.get().size();
    }
  });

  public void removeDone() {
    for (Todo todo : new ArrayList<Todo>(allTodos.get())) {
      if (todo.done.isTrue()) {
        allTodos.remove(todo);
      }
    }
  }
}
