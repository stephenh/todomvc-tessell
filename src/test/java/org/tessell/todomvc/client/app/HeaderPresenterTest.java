package org.tessell.todomvc.client.app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.tessell.model.properties.NewProperty.listProperty;

import org.junit.Test;
import org.tessell.model.properties.ListProperty;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.StubHeaderView;

import com.google.gwt.event.dom.client.KeyCodes;

public class HeaderPresenterTest extends AbstractPresenterTest {

  final ListProperty<Todo> todos = listProperty("todos");
  final HeaderPresenter p = bind(new HeaderPresenter(todos));
  final StubHeaderView v = (StubHeaderView) p.getView();

  @Test
  public void enterOnEmptyContentDoesNothing() {
    v.newTodo().keyDown(KeyCodes.KEY_ENTER);
    assertThat(todos.get().size(), is(0));
  }
  
  @Test
  public void enterOnSomeContentCreatesTask() {
    v.newTodo().type("new task");
    v.newTodo().keyDown(KeyCodes.KEY_ENTER);
    assertThat(todos.get().size(), is(1));
    assertThat(todos.get().get(0).name.get(), is("new task"));
  }

  @Test
  public void enterOnSomeContentClearsBoxForNextEntry() {
    v.newTodo().type("new task");
    v.newTodo().keyDown(KeyCodes.KEY_ENTER);
    assertThat(v.newTodo().getText(), is(""));
  }

}
