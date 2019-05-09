package main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Primitive;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;

public class BooleanBinding {
  private SimpleBooleanProperty value = new SimpleBooleanProperty();

  public boolean getValue() {
    return value.get();
  }

  private BooleanProperty getProperty() {
    return value;
  }

  public void setValue(boolean value) {
    this.value.set(value);
  }

  public void bind(BooleanProperty source, BindingMode mode) {
    if (mode == BindingMode.BIDIRECTIONAL) source.bindBidirectional(getProperty());
    else source.bind(getProperty());
  }

  public void unbind(BooleanProperty source, BindingMode mode) {
    if (mode == BindingMode.BIDIRECTIONAL) source.unbindBidirectional(getProperty());
    else source.unbind();
  }
}