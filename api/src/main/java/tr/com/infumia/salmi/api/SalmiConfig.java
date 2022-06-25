package tr.com.infumia.salmi.api;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/**
 * a config class that covers Salmi's config.
 */
@ConfigSerializable
public record SalmiConfig(
  @Setting String serverId,
  @Setting Map<String, Animation> animations,
  @Setting boolean debug,
  @Setting Map<String, GroupedPrefix> groupPrefix,
  @Setting HeaderFooter headerFooter,
  @Setting int pingSpoof,
  @Setting Placeholders placeholders,
  @Setting Map<String, Integer> placement,
  @Setting boolean pluginMetrics,
  @Setting Redis redis,
  @Setting boolean updateChecker,
  @Setting boolean useOnlineUniqueId
) {
  /**
   * the instance.
   */
  @NotNull
  private static SalmiConfig INSTANCE = new SalmiConfig();

  /**
   * ctor.
   */
  public SalmiConfig() {
    this(
      UUID.randomUUID().toString(),
      Map.of(
        "animation-1",
        new Animation(
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
      ),
      false,
      Map.of(
        "admin",
        new GroupedPrefix(
          "&c&lSTAFF TEAM",
          "",
          "admin",
          "&8[&aAdmin&8]",
          "",
          "&8[&aAdmin&8]"
        )
      ),
      new HeaderFooter(),
      -1,
      new Placeholders(),
      Map.of("admin", 10, "developer", 9, "default", 0),
      true,
      new Redis(),
      true,
      true
    );
  }

  /**
   * initiates the config.
   *
   * @param directory the directory to initiate.
   *
   * @throws ConfigurateException if something goes wrong when initiating the configuration file.
   */
  public static void init(@NotNull final Path directory)
    throws ConfigurateException {
    final var loader = JacksonConfigurationLoader
      .builder()
      .path(directory.resolve("config.json"))
      .defaultOptions(options -> options.implicitInitialization(false))
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
  public record Animation(
    @Setting List<String> frames,
    @Setting int interval
  ) {}

  /**
   * a class that represents grouped header and footer.
   */
  @ConfigSerializable
  public record GroupedHeaderFooter(
    @Setting List<String> footer,
    @Setting String group,
    @Setting List<String> header,
    @Nullable @Setting List<String> worlds
  ) {}

  /**
   * a class that represents grouped prefix.
   */
  @ConfigSerializable
  public record GroupedPrefix(
    @Setting String group,
    @Setting String aboveName,
    @Setting String belowName,
    @Setting String tabPrefix,
    @Setting String tabSuffix,
    @Setting String tagPrefix
  ) {}

  /**
   * a class that represents header and footer configurations.
   */
  @ConfigSerializable
  public record HeaderFooter(
    @Setting Set<String> disabledWorlds,
    @Setting boolean enabled,
    @Setting List<String> footer,
    @Setting Map<String, GroupedHeaderFooter> grouped,
    @Setting List<String> header
  ) {
    /**
     * ctor.
     */
    public HeaderFooter() {
      this(
        Set.of("world1", "world2"),
        true,
        List.of("&r", "&a&l&nSALMI FOOTER&r", "&r"),
        Map.of(
          "admin",
          new GroupedHeaderFooter(
            List.of("&7", "&c&lADMIN FOOTER", "&7"),
            "admin",
            List.of("&7", "&c&lADMIN HEADER", "&7"),
            List.of("world1")
          )
        ),
        List.of("&r", "&a&l&nSALMI HEADER&r", "&r")
      );
    }
  }

  /**
   * a class that represents placeholder configurations.
   */
  @ConfigSerializable
  public record Placeholders(
    @Setting String dateFormat,
    @Setting PlaceholderApi placeholderApi,
    @Setting boolean registerTabExpansion,
    @Setting Map<String, Map<String, String>> replacements,
    @Setting String timeFormat,
    @Setting int timeOffset
  ) {
    /**
     * ctor.
     */
    public Placeholders() {
      this(
        "dd.MM.yyyy",
        new PlaceholderApi(),
        false,
        Map.of(
          "%essentials_vanished%",
          Map.of("true", "Vanished", "false", "Vanished")
        ),
        "[HH:mm:ss / h:mm a]",
        0
      );
    }

    /**
     * a class that represents placeholder api configurations.
     */
    @ConfigSerializable
    public record PlaceholderApi(
      @Setting long defaultRefreshInterval,
      @Setting Map<String, Long> placeholders
    ) {
      /**
       * ctor.
       */
      public PlaceholderApi() {
        this(500L, Map.of("%player_health%", 200L, "%player_ping%", 1000L));
      }
    }
  }

  /**
   * a class that represents redis configurations.
   */
  @ConfigSerializable
  public record Redis(
    @Setting String host,
    @Setting String password,
    @Setting int port,
    @Setting @Nullable String username
  ) {
    /**
     * ctor.
     */
    public Redis() {
      this("127.0.0.0", "password", 6379, null);
    }
  }
}
