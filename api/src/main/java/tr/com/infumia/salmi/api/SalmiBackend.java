package tr.com.infumia.salmi.api;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine salmi backend.
 */
public interface SalmiBackend {
  /**
   * the instance.
   */
  AtomicReference<SalmiBackend> INSTANCE = new AtomicReference<>();

  /**
   * gets the salmi backend.
   *
   * @return salmi backend.
   */
  @NotNull
  static SalmiBackend get() {
    return Objects.requireNonNull(
      SalmiBackend.INSTANCE.get(),
      "backend not initiated!"
    );
  }

  /**
   * sends the header footer packet to the players.
   *
   * @param players the player to send.
   * @param header the header to send.
   * @param footer the footer to send.
   */
  void sendHeaderFooter(
    @NotNull final Collection<? extends Player> players,
    @NotNull final String header,
    @NotNull final String footer
  );
}
