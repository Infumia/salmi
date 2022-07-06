package tr.com.infumia.salmi;

import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.event.common.Plugins;
import tr.com.infumia.event.paper.Events;
import tr.com.infumia.event.paper.PaperEventManager;
import tr.com.infumia.salmi.api.Redis;
import tr.com.infumia.salmi.api.SalmiApi;
import tr.com.infumia.salmi.api.SalmiBackend;
import tr.com.infumia.salmi.api.SalmiConfig;
import tr.com.infumia.salmi.api.User;
import tr.com.infumia.salmi.nms.v1_18_R2.SalmiV1_18_R2;
import tr.com.infumia.terminable.CompositeTerminable;
import tr.com.infumia.terminable.TerminableConsumer;
import tr.com.infumia.terminable.TerminableModule;
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
      .runTaskTimerAsynchronously(
        this,
        () -> {
          final var users = Bukkit
            .getOnlinePlayers()
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
        },
        20L,
        20L
      );
    Bukkit
      .getScheduler()
      .runTaskTimerAsynchronously(
        this,
        () -> {
          final var players = Bukkit.getOnlinePlayers();
          final var users = SalmiApi.onlineUsers();
          SalmiBackend.get().sendHeaderFooter(players, "", "");
        },
        20L,
        20L * 3L
      );
  }
}
