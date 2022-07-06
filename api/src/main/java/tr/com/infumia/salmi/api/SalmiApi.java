package tr.com.infumia.salmi.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
   * the user set type.
   */
  private final TypeReference<Set<User>> USER_SET_TYPE = new TypeReference<>() {};

  /**
   * fetches the online users.
   *
   * @return online users.
   */
  @NotNull
  public static Collection<User> onlineUsers() {
    return SalmiApi.parseUserList(
      //      connection.sync().hgetall(SalmiApi.ONLINE_USERS_KEY).values()
      Collections.emptyList()
    );
  }

  /**
   * updates the user to the database.
   *
   * @param server the server to update.
   * @param users the users to update.
   */
  @SneakyThrows
  public static void updateOnlineUsers(
    @NotNull final String server,
    @NotNull final Collection<User> users
  ) {
    synchronized (SalmiApi.JSON) {
      final var json = SalmiApi.JSON.writeValueAsString(users);
      //      connection.sync().hset(SalmiApi.ONLINE_USERS_KEY, server, json);
    }
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
    synchronized (SalmiApi.JSON) {
      final var set = new HashSet<User>();
      for (final var s : json) {
        set.addAll(SalmiApi.JSON.readValue(s, SalmiApi.USER_SET_TYPE));
      }
      return set;
    }
  }
}
