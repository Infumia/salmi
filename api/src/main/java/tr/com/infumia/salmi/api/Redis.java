package tr.com.infumia.salmi.api;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.event.connection.ConnectionDeactivatedEvent;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that contains utility methods for redis.
 */
@UtilityClass
public class Redis {

  /**
   * the connection.
   */
  private final AtomicReference<StatefulRedisConnection<String, String>> CONNECTION = new AtomicReference<>();

  /**
   * the client.
   */
  @Nullable
  private RedisClient client;

  /**
   * the uri.
   */
  @Nullable
  private RedisURI uri;

  /**
   * gets the connections.
   *
   * @return connections.
   */
  @NotNull
  public StatefulRedisConnection<String, String> connection() {
    return Objects.requireNonNull(
      Redis.CONNECTION.get(),
      "connection not found"
    );
  }

  /**
   * initiates the redis.
   */
  public void init() {
    final var redis = SalmiConfig.instance().redis();
    Redis.init(redis.host(), redis.port(), redis.username(), redis.password());
  }

  /**
   * obtains the redis client.
   *
   * @return redis client.
   */
  @NotNull
  private RedisClient get() {
    return Objects.requireNonNull(Redis.client, "init redis first!");
  }

  /**
   * initiates the redis.
   *
   * @param host the host to init.
   * @param port the port to init.
   * @param username the username to init.
   * @param password the password to init.
   */
  private void init(
    @NotNull final String host,
    final int port,
    @Nullable final String username,
    @NotNull final String password
  ) {
    final var builder = RedisURI.Builder.redis(host, port);
    if (username == null) {
      builder.withPassword(password.toCharArray());
    } else {
      builder.withAuthentication(username, password);
    }
    Redis.uri = builder.build();
    Redis.client = RedisClient.create(Redis.uri);
    Redis.initConnection();
    final var subscribe = Redis.client
      .getResources()
      .eventBus()
      .get()
      .subscribe(e -> {
        if (e instanceof ConnectionDeactivatedEvent event) {
          Redis.initConnection();
        }
      });
  }

  /**
   * initiates the connection.
   */
  private void initConnection() {
    if (Redis.client != null) {
      Redis.CONNECTION.set(Redis.client.connect());
    }
  }
}
