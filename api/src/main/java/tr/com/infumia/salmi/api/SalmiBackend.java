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
   * sends the packet to the playes.
   *
   * @param players the player to send.
   * @param users the users to send.
   */
  void sendPacket(
    @NotNull final Collection<? extends Player> players,
    @NotNull final Collection<User> users
  );
}
