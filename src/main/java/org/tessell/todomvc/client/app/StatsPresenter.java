package org.tessell.todomvc.client.app;

import static org.tessell.model.dsl.TakesValues.textOf;
import static org.tessell.todomvc.client.views.AppViews.newStatsView;

import org.tessell.model.dsl.Binder;
import org.tessell.model.properties.StringProperty;
import org.tessell.model.values.DerivedValue;
import org.tessell.presenter.BasicPresenter;
import org.tessell.todomvc.client.model.AppState;
import org.tessell.todomvc.client.views.IsStatsView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class StatsPresenter extends BasicPresenter<IsStatsView> {

  private final AppState state;
  private final Binder binder = new Binder();
  private final StringProperty leftText;
  private final StringProperty clearText;

  public StatsPresenter(final AppState state) {
    super(newStatsView());
    this.state = state;
    this.leftText = createLeftProperty(state);
    this.clearText = createClearProperty(state);
  }

  @Override
  public void onBind() {
    super.onBind();
    binder.bind(state.numberLeft.asString()).to(textOf(view.numberLeft()));
    binder.bind(leftText).to(textOf(view.numberLeftWord()));
    binder.bind(clearText).to(textOf(view.clearCompletedAnchor()));
    binder.when(state.doneTodos.size()).is(0).hide(view.clearCompletedAnchor());
    binder.when(state.allTodos.size()).is(0).hide(view.stats());
    
    view.clearCompletedAnchor().addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        state.removeDone();
      }
    });
  }

  /** @return "item" or "items" based on number left */
  private static StringProperty createLeftProperty(final AppState state) {
    return new StringProperty(new DerivedValue<String>() {
      public String get() {
        return state.numberLeft.get() == 1 ? "item" : "items";
      }
    });
  }

  /** @return "Clear X completed item(s) */
  private static StringProperty createClearProperty(final AppState state) {
    return new StringProperty(new DerivedValue<String>() {
      public String get() {
        int done = state.doneTodos.get().size();
        String itemText = done == 1 ? "item" : "items";
        return "Clear " + done + " completed " + itemText;
      }
    });
  }
}
