package tr.com.infumia.salmi;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.salmi.api.SalmiUtilities;
import tr.com.infumia.salmi.integration.LuckPermsIntegration;

public interface Ranks {
  @Nullable
  static String get(@NotNull final Player player) {
    if (SalmiUtilities.isClassExist("net.luckperms.api.LuckPerms")) {
      return LuckPermsIntegration.rank(player);
    }
    throw new IllegalStateException(
      "Integration not found to get rank of players!"
    );
  }
}
