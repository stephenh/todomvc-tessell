package org.tessell.todomvc.client.app;

import static org.tessell.model.dsl.TakesValues.textOf;
import static org.tessell.todomvc.client.views.AppViews.newFooterView;

import org.tessell.model.properties.StringProperty;
import org.tessell.model.values.DerivedValue;
import org.tessell.presenter.BasicPresenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.views.IsFooterView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class FooterPresenter extends BasicPresenter<IsFooterView> {

  private final AppState state;

  public FooterPresenter(final AppState state) {
    super(newFooterView());
    this.state = state;
  }

  @Override
  public void onBind() {
    super.onBind();

    binder.bind(state.numberLeft.asString()).to(textOf(view.numberLeft()));
    binder.bind(leftText(state)).to(textOf(view.numberLeftWord()));
    binder.bind(clearText(state)).to(textOf(view.clearCompleted()));
    binder.when(state.doneTodos.size()).is(0).hide(view.clearCompleted());
    binder.when(state.allTodos.size()).is(0).hide(view.stats());

    view.clearCompleted().addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        state.removeDone();
      }
    });
  }

  /** @return "item" or "items" based on number left */
  private static StringProperty leftText(final AppState state) {
    return new StringProperty(new DerivedValue<String>() {
      public String get() {
        return state.numberLeft.get() == 1 ? "item" : "items";
      }
    });
  }

  /** @return "Clear X completed item(s) */
  private static StringProperty clearText(final AppState state) {
    return new StringProperty(new DerivedValue<String>() {
      public String get() {
        int done = state.doneTodos.get().size();
        String itemText = done == 1 ? "item" : "items";
        return "Clear " + done + " completed " + itemText;
      }
    });
  }
}
