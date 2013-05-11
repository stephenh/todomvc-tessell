package org.tessell.todomvc.client.app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.StubMainView;

public class MainPresenterTest extends AbstractPresenterTest {

  final AppState state = new AppState();
  final MainPresenter p = bind(new MainPresenter(state));
  final StubMainView v = (StubMainView) p.getView();

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

  @Test
  public void toggleAllIsInitiallyTrueBecauseDoneAndAllMatch() {
    assertThat(v.toggleAll().isChecked(), is(true));
  }

  @Test
  public void toggleAllIsFalseWhenOneUnchecked() {
    state.allTodos.add(new Todo("one"));
    assertThat(v.toggleAll().isChecked(), is(false));
  }

  @Test
  public void toggleAllIsTrueWhenAllChecked() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    one.done.set(true);
    assertThat(v.toggleAll().isChecked(), is(true));
  }

  @Test
  public void toggleAllSetsDoneForAllTodos() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    v.toggleAll().check();
    assertThat(one.done.get(), is(true));
  }

  @Test
  public void toggleAllUnsetsDoneForAllTodos() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    one.done.set(true);
    v.toggleAll().uncheck();
    assertThat(one.done.get(), is(false));
  }
}
