package tr.com.infumia.salmi;

import java.nio.file.Path;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

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
  Metrics.Factory metricsFactory;

  @Inject
  ProxyServer server;

  @Subscribe
  public void onProxyInitialization(final ProxyInitializeEvent event) {
  }

  @Subscribe
  public void onProxyShutdown(final ProxyShutdownEvent event) {
  }
}
