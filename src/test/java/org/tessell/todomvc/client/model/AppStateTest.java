package org.tessell.todomvc.client.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.tessell.model.events.PropertyChangedEvent;
import org.tessell.model.events.PropertyChangedHandler;

public class AppStateTest {

  private final AppState state = new AppState();

  @Test
  public void doneTodosNoticesDoneChanged() {
    Todo a = new Todo("a");
    state.allTodos.add(a);

    final boolean[] changed = { false };
    state.doneTodos.addPropertyChangedHandler(new PropertyChangedHandler<List<Todo>>() {
      public void onPropertyChanged(PropertyChangedEvent<List<Todo>> event) {
        changed[0] = true;
      }
    });

    a.done.set(true);
    assertThat(changed[0], is(true));
  }
}
