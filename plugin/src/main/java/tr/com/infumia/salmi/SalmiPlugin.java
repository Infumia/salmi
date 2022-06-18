package tr.com.infumia.salmi;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import java.nio.file.Path;
import java.time.Duration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import tr.com.infumia.salmi.api.Redis;
import tr.com.infumia.salmi.api.SalmiApi;
import tr.com.infumia.salmi.api.SalmiConfig;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalmiPlugin {

  @Inject
  @Named("salmi")
  PluginContainer container;

  @Inject
  @DataDirectory
  Path dataDirectory;

  @Inject
  Logger logger;

  @Inject
  ProxyServer server;

  @Subscribe
  public void onJoin(final ServerConnectedEvent event) {
    SalmiApi.updateOnlineUsers(this.server);
  }

  @Subscribe
  @SneakyThrows
  public void onProxyInitialization(final ProxyInitializeEvent event) {
    SalmiConfig.init(this.dataDirectory);
    Redis.init();
    this.server.getScheduler()
      .buildTask(this, () ->
        SalmiApi.onlineUsers().thenAccept(users -> {
        }))
      .repeat(Duration.ofSeconds(3L))
      .delay(Duration.ofSeconds(1L))
      .schedule();
  }
}
