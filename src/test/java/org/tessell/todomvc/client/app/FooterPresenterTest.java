package org.tessell.todomvc.client.app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.tessell.testing.TessellMatchers.hidden;
import static org.tessell.testing.TessellMatchers.shown;

import org.junit.Test;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.StubFooterView;

public class FooterPresenterTest extends AbstractPresenterTest {

  final AppState state = new AppState();
  final FooterPresenter p = bind(new FooterPresenter(state));
  final StubFooterView v = (StubFooterView) p.getView();

  @Test
  public void numberLeftDefaultsToZero() {
    assertThat(v.numberLeft().getText(), is("0"));
  }

  @Test
  public void numberLeftChangesOnNewTodo() {
    state.allTodos.add(new Todo("one"));
    assertThat(v.numberLeft().getText(), is("1"));
  }

  @Test
  public void numberLeftChangesOnRemovedTodo() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    assertThat(v.numberLeft().getText(), is("1"));
    state.allTodos.remove(one);
    assertThat(v.numberLeft().getText(), is("0"));
  }

  @Test
  public void numberLeftChangesOnCompletedTodo() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    assertThat(v.numberLeft().getText(), is("1"));
    assertThat(v.numberLeftWord().getText(), is("item"));

    one.done.set(true);
    assertThat(v.numberLeft().getText(), is("0"));
    assertThat(v.numberLeftWord().getText(), is("items"));
  }

  @Test
  public void numberLeftWordWhenSingular() {
    state.allTodos.add(new Todo("one"));
    assertThat(v.numberLeftWord().getText(), is("item"));
  }

  @Test
  public void numberLeftWordWhenPlural() {
    state.allTodos.add(new Todo("one"));
    state.allTodos.add(new Todo("two"));
    assertThat(v.numberLeftWord().getText(), is("items"));
  }

  @Test
  public void clearCompletedIsInitiallyHidden() {
    assertThat(v.clearCompleted(), is(hidden()));
  }

  @Test
  public void clearCompletedIsShownWhenSomethingIsDone() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    one.done.set(true);
    assertThat(v.clearCompleted(), is(shown()));
  }

  @Test
  public void clearCompletedIsRehiddenWhenDoneIsEmpty() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    one.done.set(true);
    assertThat(v.clearCompleted(), is(shown()));

    one.done.set(false);
    assertThat(v.clearCompleted(), is(hidden()));
  }
  
  @Test
  public void clearCompletedRemovesDoneTodos() {
    Todo one = new Todo("one");
    Todo two = new Todo("two");
    state.allTodos.add(one);
    state.allTodos.add(two);
    // two isn't done
    one.done.set(true);
    v.clearCompleted().click();

    assertThat(state.allTodos.get().size(), is(1));
    assertThat(state.doneTodos.get().size(), is(0));
  }

}
