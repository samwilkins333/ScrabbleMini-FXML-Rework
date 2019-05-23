package main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Primitive;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;

/**
 * A binding that accepts double properties that it
 * binds to an underlying double state.
 */
public class DoubleBinding {
  private SimpleDoubleProperty value = new SimpleDoubleProperty();

  /**
   * @return the current value of this
   * binding.
   */
  public double getValue() {
    return value.get();
  }

  private DoubleProperty getProperty() {
    return value;
  }

  /**
   * An edit to the value, that will
   * propagate through all registered bindings.
   * @param value the new value to be assigned
   */
  public void setValue(double value) {
    this.value.set(value);
  }

  /**
   * Establishes a binding between the given property
   * and this value.
   * @param source the property of a specific instance to bind
   * @param mode the mode of the binding that will be created
   */
  public void bind(DoubleProperty source, BindingMode mode) {
    if (mode == BindingMode.BIDIRECTIONAL) {
      source.bindBidirectional(getProperty());
    } else {
      source.bind(getProperty());
    }
  }

  /**
   * Removes the binding between the given property
   * and this value.
   * @param source the property of a specific instance to unbind
   * @param mode the mode of the binding that will be disassociated
   */
  public void unbind(DoubleProperty source, BindingMode mode) {
    if (mode == BindingMode.BIDIRECTIONAL) {
      source.unbindBidirectional(getProperty());
    } else {
      source.unbind();
    }
  }
}
