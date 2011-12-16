
A port of the [todomvc](https://github.com/addyosmani/todomvc) JS sample application to [gwt-mpv](http://www.gwtmpv.org).

See [todomvc in gwt-mpv](http://www.draconianoverlord.com/2011/12/10/todomvc-in-gwt-mpv.html) for a more detailed write up.

Building
========

This app can be built with Eclipse and either Apache [Buildr](http://buildr.apache.org) or Apache [Ant](http://ant.apache.org).

Eclipse
-------

1. Install [IvyDE](http://ant.apache.org/ivy/ivyde/).
  * IvyDE is not strictly required for using gwt-mpv, but the sample app uses it for downloading and managing jar dependencies (similar to Maven/m2eclipse).
  * If you really don't want to install IvyDE, you can run either Buildr or Ant to download the jars and manage the `.classpath` file by hand.
2. Install [Google Plugin for Eclipse](http://code.google.com/eclipse/).
  * GPE is also not strictly required for using gwt-mpv, but provides some nice GWT-specific features in Eclipse.
3. Import the project into Eclipse
  * There is a custom builder configured that should generate the necessary `IsXxx` files automatically
  * The custom builder should rerun automatically on save any time you change a `ui.xml` file
4. Run the `todomvc.launch` target to start the application in DevMode

Buildr
------

1. Install [Buildr](http://buildr.apache.org)
2. Install [ivy4r](https://github.com/klaas1979/ivy4r)
3. Run `buildr` to compile the war file

Ant
---

1. Install [Ant](http://ant.apache.org)
2. Run `ant war`

