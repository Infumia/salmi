package tr.com.infumia.salmi.api;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a record class that represents users.
 *
 * @param uniqueId the unique id.
 * @param name the name.
 * @param world the world.
 * @param rank the rank.
 */
public record User(
  @NotNull UUID uniqueId,
  @NotNull String name,
  @NotNull String world,
  @Nullable String rank
) {}
