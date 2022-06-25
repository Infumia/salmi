package tr.com.infumia.salmi.integration;

import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LuckPermsIntegration {

  @Nullable
  static String rank(
    @NotNull final Player player
  ) {
    final var user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
    if (user == null) {
      return null;
    }
    return user.getPrimaryGroup();
  }
}
