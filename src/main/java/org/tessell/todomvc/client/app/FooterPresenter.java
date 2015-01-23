package org.tessell.todomvc.client.app;

import static org.tessell.model.dsl.TakesValues.textOf;
import static org.tessell.model.properties.NewProperty.stringProperty;
import static org.tessell.todomvc.client.views.AppViews.newFooterView;

import org.tessell.model.properties.DerivedProperty;
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
    binder.bind(new DerivedProperty<String>() {
      public String getDerivedValue() {
        return state.numberLeft.get() == 1 ? "item" : "items";
      }
    }).to(textOf(view.numberLeftWord()));

    binder.when(state.doneTodos.size()).is(0).hide(view.clearCompleted());
    binder.bind(stringProperty(new DerivedValue<String>() {
      public String get() {
        String itemText = state.doneTodos.get().size() == 1 ? "item" : "items";
        return "Clear " + state.doneTodos.get().size() + " completed " + itemText;
      }
    })).to(textOf(view.clearCompleted()));

    view.clearCompleted().addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        state.removeDone();
      }
    });
  }
}
