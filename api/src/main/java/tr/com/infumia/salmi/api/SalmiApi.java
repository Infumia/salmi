package tr.com.infumia.salmi.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.velocitypowered.api.proxy.ProxyServer;
import io.lettuce.core.api.StatefulRedisConnection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods for salmi.
 */
@UtilityClass
public class SalmiApi {

  /**
   * the json.
   */
  private final JsonMapper JSON = new JsonMapper();

  /**
   * the key prefix.
   */
  private final String KEY_PREFIX = "salmi";

  /**
   * the online users key.
   */
  private final String ONLINE_USERS_KEY = SalmiApi.KEY_PREFIX + ":online_users";

  /**
   * the user list type.
   */
  private final TypeReference<List<User>> USER_LIST_TYPE = new TypeReference<>() {
  };

  /**
   * fetches the online users.
   *
   * @return online users.
   */
  @NotNull
  public static CompletableFuture<List<User>> onlineUsers() {
    return Redis.connect()
      .toCompletableFuture()
      .thenApply(StatefulRedisConnection::sync)
      .thenApply(commands -> commands.get(SalmiApi.ONLINE_USERS_KEY))
      .thenApply(SalmiApi::parseUserList);
  }

  /**
   * updates the user to the database.
   *
   * @param users the users to update.
   */
  @SneakyThrows
  public static void updateOnlineUsers(
    @NotNull final List<User> users
  ) {
    final var json = SalmiApi.JSON.writeValueAsString(users);
    Redis.connect()
      .toCompletableFuture()
      .thenApply(StatefulRedisConnection::sync)
      .thenAccept(commands -> commands.set(SalmiApi.ONLINE_USERS_KEY, json));
  }

  /**
   * updates the user to the database.
   *
   * @param server the server to update.
   */
  public static void updateOnlineUsers(
    @NotNull final ProxyServer server
  ) {
    SalmiApi.updateOnlineUsers(server.getAllPlayers().stream()
      .map(player -> new User(player.getUniqueId(), player.getUsername()))
      .toList());
  }

  /**
   * parses the user list from json content.
   *
   * @param json the json to parse.
   *
   * @return user list.
   */
  @NotNull
  @SneakyThrows
  private static List<User> parseUserList(
    @NotNull final String json
  ) {
    return SalmiApi.JSON.readValue(json, SalmiApi.USER_LIST_TYPE);
  }
}
