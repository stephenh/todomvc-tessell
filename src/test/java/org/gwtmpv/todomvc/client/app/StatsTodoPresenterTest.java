package org.gwtmpv.todomvc.client.app;

import static org.gwtmpv.testing.MpvMatchers.hidden;
import static org.gwtmpv.testing.MpvMatchers.shown;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.gwtmpv.todomvc.client.model.AppState;
import org.gwtmpv.todomvc.client.model.Todo;
import org.gwtmpv.todomvc.client.views.StubTodoStatsView;
import org.junit.Test;

public class StatsTodoPresenterTest extends AbstractPresenterTest {

  final AppState state = new AppState();
  final StatsTodoPresenter p = bind(new StatsTodoPresenter(state));
  final StubTodoStatsView v = (StubTodoStatsView) p.getView();

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

    state.doneTodos.add(one);
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
    assertThat(v.clearCompletedAnchor(), is(hidden()));
  }

  @Test
  public void clearCompletedIsShownWhenSomethingIsDone() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    state.doneTodos.add(one);
    assertThat(v.clearCompletedAnchor(), is(shown()));
  }

  @Test
  public void clearCompletedIsRehiddenWhenDoneIsEmpty() {
    Todo one = new Todo("one");
    state.allTodos.add(one);
    state.doneTodos.add(one);
    assertThat(v.clearCompletedAnchor(), is(shown()));

    state.doneTodos.remove(one);
    assertThat(v.clearCompletedAnchor(), is(hidden()));
  }
  
  @Test
  public void clearCompletedRemovesDoneTodos() {
    Todo one = new Todo("one");
    Todo two = new Todo("two");
    state.allTodos.add(one);
    state.allTodos.add(two);
    // two isn't done
    state.doneTodos.add(one);
    v.clearCompletedAnchor().click();

    assertThat(state.allTodos.get().size(), is(1));
    assertThat(state.doneTodos.get().size(), is(0));
  }

  @Test
  public void initiallyHidden() {
    assertThat(v.stats(), is(hidden()));
  }

  @Test
  public void shownAfterTodoAdded() {
    state.allTodos.add(new Todo("one"));
    assertThat(v.stats(), is(shown()));
  }
}
