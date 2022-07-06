package tr.com.infumia.salmi.nms.v1_18_R2;

import java.util.Collection;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.salmi.api.SalmiBackend;

public final class SalmiV1_18_R2 implements SalmiBackend {

  @Override
  public void sendHeaderFooter(
    @NotNull final Collection<? extends Player> players,
    @NotNull final String header,
    @NotNull final String footer
  ) {
    if (players.isEmpty()) {
      return;
    }
    final var packet = new ClientboundTabListPacket(null, null);
    packet.adventure$header = Component.text(header);
    packet.adventure$footer = Component.text(footer);
    players
      .stream()
      .map(CraftPlayer.class::cast)
      .map(CraftPlayer::getHandle)
      .map(player -> player.connection)
      .forEach(connection -> connection.send(packet));
  }
}
