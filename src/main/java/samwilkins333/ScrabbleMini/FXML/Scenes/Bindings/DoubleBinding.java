package main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DoubleBinding {
  private SimpleDoubleProperty value = new SimpleDoubleProperty();

  public double getValue() {
    return value.get();
  }

  public DoubleProperty getProperty() {
    return value;
  }

  public void setValue(double value) {
    this.value.set(value);
  }

  public void bind(DoubleProperty source, BindingMode mode) {
    if (mode == BindingMode.BIDIRECTIONAL) source.bindBidirectional(getProperty());
    else source.bind(getProperty());
  }

  public void unbind(DoubleProperty source, BindingMode mode) {
    if (mode == BindingMode.BIDIRECTIONAL) source.unbindBidirectional(getProperty());
    else source.unbind();
  }
}