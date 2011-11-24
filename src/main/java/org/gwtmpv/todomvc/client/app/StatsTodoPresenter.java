package org.gwtmpv.todomvc.client.app;

import static org.gwtmpv.model.dsl.TakesValues.textOf;
import static org.gwtmpv.todomvc.client.views.AppViews.newTodoStatsView;

import org.gwtmpv.model.dsl.Binder;
import org.gwtmpv.model.properties.StringProperty;
import org.gwtmpv.model.values.DerivedValue;
import org.gwtmpv.presenter.BasicPresenter;
import org.gwtmpv.todomvc.client.model.AppState;
import org.gwtmpv.todomvc.client.views.IsTodoStatsView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class StatsTodoPresenter extends BasicPresenter<IsTodoStatsView> {

  private final AppState state;
  private final Binder binder = new Binder();
  private final StringProperty leftText;
  private final StringProperty clearText;

  public StatsTodoPresenter(final AppState state) {
    super(newTodoStatsView());
    this.state = state;
    this.leftText = createLeftProperty();
    this.clearText = createClearProperty();
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
  private StringProperty createLeftProperty() {
    return new StringProperty(new DerivedValue<String>() {
      public String get() {
        return state.numberLeft.get() == 1 ? "item" : "items";
      }
    }).depends(state.numberLeft);
  }

  /** @return "Clear X completed item(s) */
  private StringProperty createClearProperty() {
    return new StringProperty(new DerivedValue<String>() {
      public String get() {
        String itemText = state.doneTodos.get().size() == 1 ? "item" : "items";
        return "Clear " + state.doneTodos.get().size() + " completed " + itemText;
      }
    }).depends(state.doneTodos);
  }
}
