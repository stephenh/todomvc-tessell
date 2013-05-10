package org.tessell.todomvc.client.app;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.tessell.testing.TessellMatchers.hasStyle;

import org.junit.Before;
import org.junit.Test;
import org.tessell.model.events.PropertyChangedEvent;
import org.tessell.model.events.PropertyChangedHandler;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.resources.BaseStyle;
import org.tessell.todomvc.client.views.StubTodoView;

import com.google.gwt.event.dom.client.KeyCodes;

public class TodoPresenterTest extends AbstractPresenterTest {

  final AppState state = new AppState();
  final Todo todo = new Todo("todo");
  final TodoPresenter p = bind(new TodoPresenter(state, todo));
  final StubTodoView v = (StubTodoView) p.getView();
  final BaseStyle s = v.bs();
  
  @Before
  public void addTodoToState() {
    state.allTodos.add(todo);
  }


  @Test
  public void contentIsSetInitially() {
    assertThat(v.label().getIsElement().getInnerText(), is("todo"));
  }

  @Test
  public void editBoxContentIsSetCorrectly() {
    assertThat(v.editBox().getText(), is("todo"));
  }

  @Test
  public void enterKeyInEditBoxSetsTheNewName() {
    v.editBox().keyDown(KeyCodes.KEY_ENTER);
    // being hidden fires change
    v.editBox().setValue("new name", true);
    assertThat(todo.name.get(), is("new name"));
    assertThat(v.label().getIsElement().getInnerText(), is("new name"));
  }
  
  @Test
  public void escapeKeyInEditBoxSetsTheNewName() {
    v.editBox().keyDown(KeyCodes.KEY_ESCAPE);
    // being hidden fires change
    v.editBox().setValue("new name", true);
    assertThat(todo.name.get(), is("new name"));
    assertThat(v.label().getIsElement().getInnerText(), is("new name"));
  }

  @Test
  public void checkDoneChangesTheModel() {
    assertThat(todo.done.get(), is(false));
    v.checkBox().check();
    assertThat(todo.done.get(), is(true));
  }

  @Test
  public void checkDoneAddsStyle() {
    v.checkBox().check();
    assertThat(v.li(), hasStyle(s.completed()));
  }
  
  @Test
  public void checkUndoneRemovesStyle() {
    v.checkBox().check();
    assertThat(v.li(), hasStyle(s.completed()));
    v.checkBox().uncheck();
    assertThat(v.li(), not(hasStyle(s.completed())));
  }
  
  @Test
  public void checkDoneChangesAppStatesNumberLeft() {
    // use an event handler to show the value is being pushed correctly
    final int[] numberLeft = { state.numberLeft.get() };
    state.numberLeft.addPropertyChangedHandler(new PropertyChangedHandler<Integer>() {
      public void onPropertyChanged(PropertyChangedEvent<Integer> event) {
        numberLeft[0] = event.getNewValue();
      }
    });
    assertThat(numberLeft[0], is(1));
    v.checkBox().check();
    assertThat(numberLeft[0], is(0));
  }

  @Test
  public void destroyRemovesTodoFromModel() {
    assertThat(state.allTodos.get().size(), is(1));
    v.destroy().click();
    assertThat(state.allTodos.get().size(), is(0));
  }

}
