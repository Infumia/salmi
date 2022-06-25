package tr.com.infumia.salmi.api;

import org.jetbrains.annotations.NotNull;

/**
 * an interface that contains utility methods for salmi.
 */
public interface SalmiUtilities {

  /**
   * checks if the class exist or not.
   *
   * @param cls the cls to check.
   *
   * @return {@code true} if the class exists.
   */
  static boolean isClassExist(
    @NotNull final String cls
  ) {
    try {
      Class.forName(cls);
      return true;
    } catch (final Exception ignored) {
    }
    return false;
  }
}
