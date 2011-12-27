package org.tessell.todomvc.client.model;

import static org.tessell.model.properties.NewProperty.booleanProperty;
import static org.tessell.model.properties.NewProperty.stringProperty;

import org.tessell.model.properties.BooleanProperty;
import org.tessell.model.properties.StringProperty;

public class Todo {

  public final BooleanProperty done = booleanProperty("done", false);
  public final StringProperty name = stringProperty("name");

  public Todo(String name) {
    this.name.set(name);
  }

}
