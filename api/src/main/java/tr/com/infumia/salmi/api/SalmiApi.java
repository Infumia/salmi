package tr.com.infumia.salmi.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.lettuce.core.api.StatefulRedisConnection;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
   * @param connection the connection to fetch.
   *
   * @return online users.
   */
  @NotNull
  public static Collection<User> onlineUsers(
    @NotNull final StatefulRedisConnection<String, String> connection
  ) {
    return SalmiApi.parseUserList(connection.sync().hgetall(SalmiApi.ONLINE_USERS_KEY).values());
  }

  /**
   * updates the user to the database.
   *
   * @param connection the connection to update.
   * @param server the server to update.
   * @param users the users to update.
   */
  @SneakyThrows
  public static void updateOnlineUsers(
    @NotNull final StatefulRedisConnection<String, String> connection,
    @NotNull final String server,
    @NotNull final Collection<User> users
  ) {
    final var json = SalmiApi.JSON.writeValueAsString(users);
    connection.sync().hset(SalmiApi.ONLINE_USERS_KEY, server, json);
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
  private static Collection<User> parseUserList(
    @NotNull final Collection<String> json
  ) {
    final var set = new HashSet<User>();
    for (final var s : json) {
      set.addAll(SalmiApi.JSON.readValue(s, SalmiApi.USER_LIST_TYPE));
    }
    return set;
  }
}
