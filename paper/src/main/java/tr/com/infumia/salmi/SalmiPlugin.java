package tr.com.infumia.salmi;

import java.util.Collection;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.salmi.api.Redis;
import tr.com.infumia.salmi.api.SalmiApi;
import tr.com.infumia.salmi.api.SalmiConfig;
import tr.com.infumia.salmi.api.User;

public final class SalmiPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    SalmiConfig.initUnchecked(this.getDataFolder().toPath());
    Redis.init();
    Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::updateTabList, 20L, 20L * 3L);
  }

  private void sendPacket(
    @NotNull final Player player,
    @NotNull final Collection<User> users
  ) {
  }

  private void updateTabList() {
    final var players = Bukkit.getOnlinePlayers();
    final var users = players.stream()
      .map(player -> new User(player.getUniqueId(), player.getName(), player.getWorld().getName(), Ranks.get(player)))
      .collect(Collectors.toSet());
    SalmiApi.updateOnlineUsers(SalmiConfig.instance().serverId(), users)
      .whenComplete((connection, throwable) -> {
        SalmiApi.onlineUsers()
          .thenAccept(u -> players.forEach(player -> this.sendPacket(player, u)));
      });
  }
}
