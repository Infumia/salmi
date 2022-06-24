package tr.com.infumia.salmi.api;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/**
 * a config class that covers Salmi's config.
 */
@Getter
@Setter
@ConfigSerializable
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SalmiConfig {
/*
1. Header ve Footer özelliği tabda.
2. Bu header ve footer'a animasyon ekleme.
3. Bu header ve footer'da tam placeholderapi desteği (her şeyi desteklemeli)
4. Grup sıralama (yani developer en tepede olcak gibi)
5. Gruplara özel prefix - suffix - belowname gibi şeyler tanımlama.
6. https://github.com/NEZNAMY/TAB/wiki/Feature-guide:-Aligned-Tabsuffix
7. https://github.com/NEZNAMY/TAB/wiki/Feature-guide:-Belowname
8. Her rank grubu için ayrı ayrı header ve footer deiğştirme özelliği
*/

  /**
   * the instance.
   */
  @NotNull
  private static SalmiConfig INSTANCE = new SalmiConfig();

  /**
   * the animations.
   */
  @Setting
  Map<String, Animation> animations = Map.of(
    "animation-1", new Animation(
      List.of(
        "&a_",
        "&aA",
        "&aAD",
        "&aADM",
        "&aADMI",
        "&aADMIN",
        "&aADMIN",
        "&aADMI",
        "&aADM",
        "&aAD",
        "&aA"
      ),
      100
    )
  );

  /**
   * the debug.
   */
  @Setting
  boolean debug = false;

  /**
   * the group prefix.
   */
  @Setting
  Map<String, GroupedPrefix> groupPrefix = Map.of(
    "admin", new GroupedPrefix(
      "&c&lSTAFF TEAM",
      "",
      "admin",
      "&8[&aAdmin&8]",
      "",
      "&8[&aAdmin&8]"
    )
  );

  /**
   * the header footer.
   */
  @Setting
  HeaderFooter headerFooter = new HeaderFooter();

  /**
   * the ping spoof.
   */
  @Setting
  int pingSpoof = -1;

  /**
   * the placeholders.
   */
  @Setting
  Placeholders placeholders = new Placeholders();

  /**
   * the placement.
   */
  @Setting
  Map<String, Integer> placement = Map.of(
    "admin", 10,
    "developer", 9,
    "default", 0
  );

  /**
   * the plugin metrics.
   */
  @Setting
  boolean pluginMetrics = true;

  /**
   * the redis.
   */
  @Setting
  Redis redis = new Redis();

  /**
   * the update checker.
   */
  @Setting
  boolean updateChecker = true;

  /**
   * the use online unique id.
   */
  @Setting
  boolean useOnlineUniqueId = true;

  /**
   * initiates the config.
   *
   * @param directory the directory to initiate.
   *
   * @throws ConfigurateException if something goes wrong when initiating the configuration file.
   */
  public static void init(@NotNull final Path directory) throws ConfigurateException {
    final var loader = JacksonConfigurationLoader.builder()
      .path(directory.resolve("config.json"))
      .defaultOptions(options -> options
        .implicitInitialization(false))
      .build();
    final var node = loader.load();
    SalmiConfig.INSTANCE = node.get(SalmiConfig.class, new SalmiConfig());
    node.set(SalmiConfig.class, SalmiConfig.INSTANCE);
    loader.save(node);
  }

  /**
   * initiates the config.
   *
   * @param directory the directory to initiate.
   */
  @SneakyThrows
  public static void initUnchecked(@NotNull final Path directory) {
    SalmiConfig.init(directory);
  }

  /**
   * obtains the instance.
   *
   * @return instance.
   */
  @NotNull
  public static SalmiConfig instance() {
    return SalmiConfig.INSTANCE;
  }

  /**
   * a class that represents animations.
   */
  @ConfigSerializable
  @RequiredArgsConstructor
  @Accessors(fluent = true)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class Animation {

    /**
     * the frames.
     */
    @NotNull
    @Setting
    List<String> frames;

    /**
     * the interval
     */
    @Setting
    int interval;
  }

  /**
   * a class that represents grouped header and footer.
   */
  @ConfigSerializable
  @RequiredArgsConstructor
  @Accessors(fluent = true)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class GroupedHeaderFooter {

    /**
     * the footer.
     */
    @NotNull
    @Setting
    List<String> footer;

    /**
     * the group.
     */
    @NotNull
    @Setting
    String group;

    /**
     * the header.
     */
    @NotNull
    @Setting
    List<String> header;

    /**
     * the worlds.
     */
    @Nullable
    @Setting
    List<String> worlds;
  }

  /**
   * a class that represents grouped prefix.
   */
  @ConfigSerializable
  @RequiredArgsConstructor
  @Accessors(fluent = true)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class GroupedPrefix {

    /**
     * teh above name.
     */
    @NotNull
    @Setting
    String aboveName;

    /**
     * the below name.
     */
    @NotNull
    @Setting
    String belowName;

    /**
     * the group.
     */
    @NotNull
    @Setting
    String group;

    /**
     * the tab prefix.
     */
    @NotNull
    @Setting
    String tabPrefix;

    /**
     * the tab suffix.
     */
    @NotNull
    @Setting
    String tabSuffix;

    /**
     * the tag prefix.
     */
    @NotNull
    @Setting
    String tagPrefix;
  }

  /**
   * a class that represents header and footer configurations.
   */
  @Getter
  @Setter
  @ConfigSerializable
  @Accessors(fluent = true)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class HeaderFooter {

    /**
     * the disabled worlds.
     */
    @Setting
    Set<String> disabledWorlds = Set.of("world1", "world2");

    /**
     * the enabled.
     */
    @Setting
    boolean enabled = true;

    /**
     * the footer.
     */
    @Setting
    List<String> footer = List.of(
      "&r",
      "&a&l&nSALMI FOOTER&r",
      "&r"
    );

    /**
     * the grouped.
     */
    @Setting
    Map<String, GroupedHeaderFooter> grouped = Map.of(
      "admin", new GroupedHeaderFooter(
        List.of(
          "&7",
          "&c&lADMIN FOOTER",
          "&7"
        ),
        "admin",
        List.of(
          "&7",
          "&c&lADMIN HEADER",
          "&7"
        ),
        List.of("world1")
      )
    );

    /**
     * the header.
     */
    @Setting
    List<String> header = List.of(
      "&r",
      "&a&l&nSALMI HEADER&r",
      "&r"
    );
  }

  /**
   * a class that represents placeholder configurations.
   */
  @Getter
  @Setter
  @ConfigSerializable
  @Accessors(fluent = true)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class Placeholders {

    /**
     * the date format.
     */
    @Setting
    String dateFormat = "dd.MM.yyyy";

    /**
     * the placeholder api.
     */
    @Setting
    PlaceholderApi placeholderApi = new PlaceholderApi();

    /**
     * the register tab expansion.
     */
    @Setting
    boolean registerTabExpansion = false;

    /**
     * the replacements.
     */
    @Setting
    Map<String, Map<String, String>> replacements = Map.of(
      "%essentials_vanished%", Map.of(
        "true", "Vanished",
        "false", "Vanished"
      )
    );

    /**
     * the time format.
     */
    @Setting
    String timeFormat = "[HH:mm:ss / h:mm a]";

    /**
     * the time offset.
     */
    @Setting
    int timeOffset = 0;

    /**
     * a class that represents placeholder api configurations.
     */
    @Getter
    @Setter
    @ConfigSerializable
    @Accessors(fluent = true)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static final class PlaceholderApi {

      /**
       * the default refresh interval.
       */
      @Setting
      long defaultRefreshInterval = 500L;

      /**
       * the placeholders.
       */
      @Setting
      Map<String, Long> placeholders = Map.of(
        "%player_health%", 200L,
        "%player_ping%", 1000L
      );
    }
  }

  /**
   * a class that represents redis configurations.
   */
  @Getter
  @Setter
  @ConfigSerializable
  @Accessors(fluent = true)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class Redis {

    /**
     * the host.
     */
    @Setting
    String host = "127.0.0.0";

    /**
     * the password.
     */
    @Setting
    String password = "password";

    /**
     * the port.
     */
    @Setting
    int port = 6379;

    /**
     * the username.
     */
    @Nullable
    @Setting
    String username = null;
  }
}
