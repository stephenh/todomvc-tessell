package org.gwtmpv.todomvc.client.app;

import static org.gwtmpv.model.properties.NewProperty.listProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.gwtmpv.model.properties.ListProperty;
import org.gwtmpv.todomvc.client.model.Todo;
import org.gwtmpv.todomvc.client.views.StubCreateTodoView;
import org.junit.Test;

import com.google.gwt.event.dom.client.KeyCodes;

public class CreateTodoPresenterTest extends AbstractPresenterTest {

  final ListProperty<Todo> todos = listProperty("todos");
  final CreateTodoPresenter p = bind(new CreateTodoPresenter(todos));
  final StubCreateTodoView v = (StubCreateTodoView) p.getView();

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
    assertThat(todos.get().get(0).getName(), is("new task"));
  }

  @Test
  public void enterOnSomeContentClearsBoxForNextEntry() {
    v.newTodo().type("new task");
    v.newTodo().keyDown(KeyCodes.KEY_ENTER);
    assertThat(v.newTodo().getText(), is(""));
  }

}
