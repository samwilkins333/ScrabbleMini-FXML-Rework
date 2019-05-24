package main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * A list that contains special iterators
 * allowing for actions to occur at various points
 * during or after the iteration.
 * @param <T> the type of element contained in the list
 */
public class UtilityArrayList<T> extends ArrayList<T> {

  /**
   * Constructor.
   * @param initialCapacity the list's initial capacity
   */
  public UtilityArrayList(int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Constructor.
   * @param c a collection of elements used to
   *          initialize the list
   */
  public UtilityArrayList(Collection<? extends T> c) {
    super(c);
  }

  /**
   * A modified forEach that receives a Consumer to
   * apply to all elements in the list, as well as a
   * second consumer to be executed at the end of the iteration.
   * @param action action applied to all elements
   * @param done action applied at the end of iteration,
   *                     optionally receiving the last element
   */
  public void forEach(Consumer<? super T> action, Consumer<? super T> done) {
    int size = size();
    IntStream.range(0, size).forEach(i -> {
      T element = get(i);
      action.accept(element);
      if (i == size - 1) {
        done.accept(element);
      }
    });
  }

}
