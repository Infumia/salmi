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
    1. Header ve Footer özelliği tabda.
    2. Bu header ve footer'a animasyon ekleme.
    3. Bu header ve footer'da tam placeholderapi desteği (her şeyi desteklemeli)
    4. Grup sıralama (yani developer en tepede olcak gibi)
    5. Gruplara özel prefix - suffix - belowname gibi şeyler tanımlama.
    6. https://github.com/NEZNAMY/TAB/wiki/Feature-guide:-Aligned-Tabsuffix
    7. https://github.com/NEZNAMY/TAB/wiki/Feature-guide:-Belowname
    8. Her rank grubu için ayrı ayrı header ve footer deiğştirme özelliği

    # ----> General Plugin Settings
    update-checker: true
    plugin-metrics: true
    debug: false
    use-bukkit-permissions-manager: false
    use-online-uuid-in-tablist: true

    # ----> Redis Database Settings
    redis:
      enabled: false
      host: 127.0.0.1
      port: 31
      database: asdasdasd
      username: aasdsaqda
      password: 31

    # ----> Header & Footer Settings
    header-footer:
      enabled: true
      disabled-worlds:
        - world1
        - world2
      header:
        - '&r'
        - '&a&l&nINFUMIA NETWORK&r'
        - '&r'
      footer:
        - '&r'
        - '&7www.vipaldiktansonraserverikapattik.com'
        - '&r'
      special-group-tab:
        admin: # Eğer oyuncu adminse bu tab'ı görecek.
          header:
            - '&r'
            - '&c&lSEN ADMINSIN'
            - '&r'
          footer:
            - '&r'
            - '&7ye'
            - '&r'
      special-worlds-tab:
        lobbyworld:
          header:
            - '&r'
            - '&a&lLOBBY WORLD TAB'
            - '&r'
          footer:
            - '&r'
            - '&7.'
            - '&r'

    # ----> Grup Sıralaması (Oyuncunun tabdaki sıralaması yani)
    # ---> Vault rank desteklesin, luckperms desteklesin, ultrapermission desteklesin yeterli.
    # ---> Aşağıda admin/developer/helper yazanlar bu izin eklentilerindeki isimler. Sağdaki de
    # ---> hiyerarşi sıralaması. Yani 10'sa en tepede, 3'se en altta gibi.
    siralama-sistemi:
      admin: 10
      developer: 9
      helper: 8
      mod: 7
      vip++++: 6
      vip+: 5
      vip-: 4
      default/oyuncu: 3

    # ----> Oyuncunun tabda custom prefix'e falan sahip olması.
    # ----> Burda placeholder destekleyebilsin. Yani adam belki %lucpkerms-prefix% yazıp geçicek.
    group-prefix-settings:
      admin:
        tab-prefix: '&8[&aAdmin&8]'
        tab-suffix: ''
        tag-prefix: '&8[&aAdmin&8]' # Oyuncunun oyunda göründüğü ismin başındaki etiket. https://prnt.sc/fjvQ5G4vk36T
        above-name: '&c&lSTAFF TEAM' #https://prnt.sc/u3WX0xpyzfnk
        below-name: '' #burası da above gibi ama alt tarafında duracak. yukarıdaki fotodan anlarsın.
      developer:
        tab-prefix: '&8[&cDeveloper&8]'
        tab-suffix: ''
        tag-prefix: '&8[&cDeveloper&8]' # Oyuncunun oyunda göründüğü ismin başındaki etiket. https://prnt.sc/fjvQ5G4vk36T
        above-name: '&c&lSTAFF TEAM' #https://prnt.sc/u3WX0xpyzfnk
        below-name: '' #burası da above gibi ama alt tarafında duracak. yukarıdaki fotodan anlarsın.

    # ---> Animasyon bölümü
    animation-systems:
      # Bunu tab'a şöyle entegr edicekler; %animation_test-animation-1% gibi.
      # Yazınca böyle olcak.
      test-animation-1:
        change-interval: 100
        texts:
          - '&a_'
          - '&aA'
          - '&aAY'
          - '&aAYB'
          - '&aAYBE'
          - '&aAYBER'
          - '&aAYBERK'
          - '&aAYBER'
          - '&aAYBE'
          - '&aAYB'
          - '&aAY'
          - '&aA'
          - '&a_'

    # ---> Placeholder ve diğer önemli şeyleri. tab'dan direkt ekledim bunu. çok kullanıyolar ama ben kullanmıyom.
    ping-spoof:
      enabled: false
      value: 0
    placeholders:
      date-format: dd.MM.yyyy
      time-format: '[HH:mm:ss / h:mm a]'
      time-offset: 0
      register-tab-expansion: false
    placeholder-output-replacements:
      '%essentials_vanished%':
        'yes': '&7| Vanished'
        'no': ''
      '%afk%':
        '%afk%': ''
    placeholderapi-refresh-intervals:
      default-refresh-interval: 500
      server:
        '%server_uptime%': 1000
        '%server_tps_1_colored%': 1000
      player:
        '%player_health%': 200
        '%player_ping%': 1000
        '%vault_prefix%': 1000
      relational:
        '%rel_factionsuuid_relation_color%': 1000
     */
  }
}
