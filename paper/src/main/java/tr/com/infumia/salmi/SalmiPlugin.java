package tr.com.infumia.salmi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tr.com.infumia.salmi.api.Redis;
import tr.com.infumia.salmi.api.SalmiApi;
import tr.com.infumia.salmi.api.SalmiConfig;

public final class SalmiPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    SalmiConfig.initUnchecked(this.getDataFolder().toPath());
    Redis.init();
    Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::updateTabList, 20L, 20L * 3L);
  }

  private void updateTabList() {
    final var players = Bukkit.getOnlinePlayers();
    SalmiApi.onlineUsers().thenAccept(users -> {
    });
  }
}
