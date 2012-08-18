package org.tessell.todomvc.client.model;

import static org.tessell.model.properties.NewProperty.integerProperty;
import static org.tessell.model.properties.NewProperty.listProperty;

import java.util.ArrayList;

import org.tessell.model.properties.IntegerProperty;
import org.tessell.model.properties.ListProperty;
import org.tessell.model.values.DerivedValue;

public class AppState {

  public final ListProperty<Todo> allTodos = listProperty("allTodos");
  
  public final IntegerProperty numberDone = integerProperty(new DerivedValue<Integer>() {
    public Integer get() {
      int done = 0;
      for (Todo todo : allTodos.get()) {
        if (todo.done.isTrue()) {
          done++;
        }
      }
      return done;
    }
  });
  
  public final IntegerProperty numberLeft = integerProperty(new DerivedValue<Integer>() {
    public Integer get() {
      return allTodos.get().size() - numberDone.get();
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
