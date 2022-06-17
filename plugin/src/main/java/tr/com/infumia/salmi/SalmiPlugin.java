package tr.com.infumia.salmi;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import java.nio.file.Path;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;

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
  public void onProxyInitialization(final ProxyInitializeEvent event) {
  }

  @Subscribe
  public void onProxyShutdown(final ProxyShutdownEvent event) {
  }
}
