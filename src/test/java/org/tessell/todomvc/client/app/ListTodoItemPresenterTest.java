package org.tessell.todomvc.client.app;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.tessell.testing.TessellMatchers.hasStyle;
import static org.tessell.testing.TessellMatchers.hidden;
import static org.tessell.testing.TessellMatchers.shown;

import org.junit.Before;
import org.junit.Test;
import org.tessell.model.events.PropertyChangedEvent;
import org.tessell.model.events.PropertyChangedHandler;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.model.Todo;
import org.tessell.todomvc.client.views.ListTodoItemStyle;
import org.tessell.todomvc.client.views.StubListTodoItemView;

import com.google.gwt.event.dom.client.KeyCodes;

public class ListTodoItemPresenterTest extends AbstractPresenterTest {

  final AppState state = new AppState();
  final Todo todo = new Todo("todo");
  final ListTodoItemPresenter p = bind(new ListTodoItemPresenter(state, todo));
  final StubListTodoItemView v = (StubListTodoItemView) p.getView();
  final ListTodoItemStyle s = v.style();
  
  @Before
  public void addTodoToState() {
    state.allTodos.add(todo);
  }

  @Test
  public void displayPanelIsInitiallyShown() {
    assertThat(v.displayPanel(), is(shown()));
  }

  @Test
  public void editPanelIsInitiallyHidden() {
    assertThat(v.editPanel(), is(hidden()));
  }

  @Test
  public void doubleClickOnContentShowsEditPanel() {
    v.content().doubleClick();
    assertThat(v.displayPanel(), is(hidden()));
    assertThat(v.editPanel(), is(shown()));
    assertThat(v.li(), hasStyle(s.editing()));
  }

  @Test
  public void contentIsSetInitially() {
    assertThat(v.content().getText(), is("todo"));
  }

  @Test
  public void editBoxContentIsSetCorrectly() {
    v.content().doubleClick();
    assertThat(v.editBox().getText(), is("todo"));
  }

  @Test
  public void enterKeyInEditBoxSetsTheNewName() {
    v.content().doubleClick();
    v.editBox().type("new name");
    v.editBox().keyDown(KeyCodes.KEY_ENTER);
    assertThat(todo.name.get(), is("new name"));
    assertThat(v.content().getText(), is("new name"));
  }

  @Test
  public void enterKeyInEditBoxCancelsEditing() {
    v.content().doubleClick();
    v.editBox().keyDown(KeyCodes.KEY_ENTER);
    assertThat(v.displayPanel(), is(shown()));
    assertThat(v.editPanel(), is(hidden()));
    assertThat(v.li(), not(hasStyle(s.editing())));
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
    assertThat(v.li(), hasStyle(s.done()));
  }
  
  @Test
  public void checkUndoneRemovesStyle() {
    v.checkBox().check();
    assertThat(v.li(), hasStyle(s.done()));
    v.checkBox().uncheck();
    assertThat(v.li(), not(hasStyle(s.done())));
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
    v.destroyAnchor().click();
    assertThat(state.allTodos.get().size(), is(0));
  }

}
