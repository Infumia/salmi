package tr.com.infumia.salmi.api;

import java.nio.file.Path;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
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

  /**
   * the instance.
   */
  @NotNull
  private static SalmiConfig INSTANCE = new SalmiConfig();

  /**
   * the redis.
   */
  @Setting
  @Comment("Redis configurations")
  Redis redis = new Redis();

  /**
   * the tab.
   */
  @Setting
  @Comment("Tab configurstions")
  Tab tab = new Tab();

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
    @Comment("Host of redis.")
    String host = "127.0.0.0";

    /**
     * the password.
     */
    @Setting
    @Comment("Password of redis.")
    String password = "password";

    /**
     * the port.
     */
    @Setting
    @Comment("Port of redis.")
    int port = 6379;

    /**
     * the username.
     */
    @Nullable
    @Setting
    @Comment("Username of redis.")
    String username = null;
  }

  /**
   * a class that represents tab configurations.
   */
  @Getter
  @Setter
  @ConfigSerializable
  @Accessors(fluent = true)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class Tab {
    /*
    1.  Header ve Footer özelliği tabda.
    2. Bu header ve footer'a animasyon ekleme.
    3. Bu header ve footer'da tam placeholderapi desteği (her şeyi desteklemeli)
    4. Grup sıralama (yani developer en tepede olcak gibi)
    5. Gruplara özel prefix - suffix - belowname gibi şeyler tanımlama.
    6. https://github.com/NEZNAMY/TAB/wiki/Feature-guide:-Aligned-Tabsuffix
    7. https://github.com/NEZNAMY/TAB/wiki/Feature-guide:-Belowname
    8. Her rank grubu için ayrı ayrı header ve footer deiğştirme özelliği
     */
  }
}
