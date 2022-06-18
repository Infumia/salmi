package tr.com.infumia.salmi.api;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * a record class that represents users.
 *
 * @param uniqueId the unique id.
 * @param name the name.
 */
public record User(
  @NotNull UUID uniqueId,
  @NotNull String name
) {

}
