package org.tessell.todomvc.client.app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.tessell.testing.TessellMatchers.hidden;
import static org.tessell.testing.TessellMatchers.shown;

import org.junit.Test;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.StubAppView;

public class AppPresenterTest extends AbstractPresenterTest {

  final AppState state = new AppState();
  final AppPresenter p = bind(new AppPresenter(state));
  final StubAppView v = (StubAppView) p.getView();

  @Test
  public void mainAndFooterInitiallyHidden() {
    assertThat(v.main(), is(hidden()));
    assertThat(v.footer(), is(hidden()));
  }

  @Test
  public void mainAndFooterShownAfterTodoAdded() {
    state.allTodos.add(new Todo("one"));
    assertThat(v.main(), is(shown()));
    assertThat(v.footer(), is(shown()));
  }

}
