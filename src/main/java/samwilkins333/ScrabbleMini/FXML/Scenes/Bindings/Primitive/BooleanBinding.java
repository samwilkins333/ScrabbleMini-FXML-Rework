package samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Primitive;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;

/**
 * A binding that accepts boolean properties that it
 * binds to an underlying boolean state.
 */
public class BooleanBinding {
  private SimpleBooleanProperty value = new SimpleBooleanProperty();

  /**
   * @return the current value of this
   * binding.
   */
  public boolean getValue() {
    return value.get();
  }

  private BooleanProperty getProperty() {
    return value;
  }

  /**
   * An edit to the value, that will
   * propagate through all registered bindings.
   *
   * @param value the new value to be assigned
   */
  public void setValue(boolean value) {
    this.value.set(value);
  }

  /**
   * Establishes a binding between the given property
   * and this value.
   *
   * @param source the property of a specific instance to bind
   * @param mode   the mode of the binding that will be created
   */
  public void bind(BooleanProperty source, BindingMode mode) {
    if (mode == BindingMode.BIDIRECTIONAL) {
      source.bindBidirectional(getProperty());
    } else {
      source.bind(getProperty());
    }
  }

  /**
   * Removes the binding between the given property
   * and this value.
   *
   * @param source the property of a specific instance to unbind
   * @param mode   the mode of the binding that will be disassociated
   */
  public void unbind(BooleanProperty source, BindingMode mode) {
    if (mode == BindingMode.BIDIRECTIONAL) {
      source.unbindBidirectional(getProperty());
    } else {
      source.unbind();
    }
  }
}
