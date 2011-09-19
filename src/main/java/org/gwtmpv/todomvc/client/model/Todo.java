package org.gwtmpv.todomvc.client.model;

import static org.gwtmpv.model.properties.NewProperty.booleanProperty;
import static org.gwtmpv.model.properties.NewProperty.stringProperty;

import org.gwtmpv.model.properties.BooleanProperty;
import org.gwtmpv.model.properties.StringProperty;

public class Todo {

  public final BooleanProperty done = booleanProperty("done", false);
  public final StringProperty name = stringProperty("name");

  public Todo(String name) {
    this.name.set(name);
  }

}
