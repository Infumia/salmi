package tr.com.infumia.salmi;

import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tr.com.infumia.salmi.api.Redis;
import tr.com.infumia.salmi.api.SalmiApi;
import tr.com.infumia.salmi.api.SalmiBackend;
import tr.com.infumia.salmi.api.SalmiConfig;
import tr.com.infumia.salmi.api.User;
import tr.com.infumia.salmi.nms.v1_18_R2.SalmiV1_18_R2;
import tr.com.infumia.versionmatched.VersionMatched;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SalmiPlugin extends JavaPlugin {

  VersionMatched<SalmiBackend> salmiBackend = new VersionMatched<>(
    SalmiV1_18_R2.class
  );

  @Override
  public void onEnable() {
    this.salmiBackend.of().create().ifPresent(SalmiBackend.INSTANCE::set);
    SalmiConfig.initUnchecked(this.getDataFolder().toPath());
    Redis.init();
    Bukkit
      .getScheduler()
      .runTaskTimerAsynchronously(this, this::updateOnlineUsers, 20L, 20L);
    Bukkit
      .getScheduler()
      .runTaskTimerAsynchronously(this, this::updateTabList, 20L, 20L * 3L);
  }

  private void updateOnlineUsers() {
    final var players = Bukkit.getOnlinePlayers();
    final var users = players
      .stream()
      .map(player ->
        new User(
          player.getUniqueId(),
          player.getName(),
          player.getWorld().getName(),
          Ranks.get(player)
        )
      )
      .collect(Collectors.toSet());
    SalmiApi.updateOnlineUsers(SalmiConfig.instance().serverId(), users);
  }

  private void updateTabList() {
    final var players = Bukkit.getOnlinePlayers();
    SalmiApi
      .onlineUsers()
      .thenAccept(u -> SalmiBackend.get().sendPacket(players, u))
      .whenComplete((unused, throwable) -> {
        if (throwable != null) {
          throwable.printStackTrace();
        }
      });
  }
}
