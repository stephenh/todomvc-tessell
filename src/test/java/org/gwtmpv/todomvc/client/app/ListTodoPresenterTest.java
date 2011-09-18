package org.gwtmpv.todomvc.client.app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.gwtmpv.todomvc.client.model.AppState;
import org.gwtmpv.todomvc.client.model.Todo;
import org.gwtmpv.todomvc.client.views.StubListTodoView;
import org.junit.Test;

public class ListTodoPresenterTest extends AbstractPresenterTest {

  final AppState state = new AppState();
  final ListTodoPresenter p = bind(new ListTodoPresenter(state));
  final StubListTodoView v = (StubListTodoView) p.getView();
  
  @Test
  public void initiallyNoLines() {
    assertThat(v.ul().getWidgetCount(), is(0));
  }
  
  @Test
  public void addsNewLineForNewTodo() {
    state.allTodos.add(new Todo("one"));
    assertThat(v.ul().getWidgetCount(), is(1));
  }
  
  @Test
  public void removesLineForDeletedTodo() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    assertThat(v.ul().getWidgetCount(), is(1));
    state.allTodos.remove(one);
    assertThat(v.ul().getWidgetCount(), is(0));
  }
}
