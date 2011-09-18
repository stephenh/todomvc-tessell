package org.gwtmpv.todomvc.client.model;

public class Todo {

  private boolean done;
  private String name;

  public Todo(String name) {
    this.name = name;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
