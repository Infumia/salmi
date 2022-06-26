package tr.com.infumia.salmi.nms.v1_18_R2;

import java.util.Collection;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.salmi.api.SalmiBackend;
import tr.com.infumia.salmi.api.User;

public final class SalmiV1_18_R2 implements SalmiBackend {

  @Override
  public void sendPacket(@NotNull final Collection<? extends Player> players, @NotNull final Collection<User> users) {
    final var packet = new ClientboundTabListPacket(null, null);
    packet.adventure$header = Component
      .text("header -> ")
      .append(Component.text(players.size()));
    packet.adventure$footer = Component
      .text("footer -> ")
      .append(Component.text(users.size()));
    for (final var player : players) {
      final var serverPlayer = ((CraftPlayer) player).getHandle();
      serverPlayer.connection.send(packet);
    }
  }
}
