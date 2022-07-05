import { configProperties, env } from "@ustra/core";
import NuxtConfigLoader from "@ustra/nuxt/src/config/nuxt-config-loader";
import NuxtAppProperties from "@ustra/nuxt/src/config/nuxt-app-properties";

export default async () => {
  const config: NuxtAppProperties = {
    app: {
      processPath: __dirname,
      profile: process.env.CONFIG_ENV,
      configDir: "config",
      deviceType: configProperties.DeviceType.MOBILE,
      title: "U.STRA Framework Demo - nuxt devexteme bo",
      auth: {
        enabled: true,
        loginUrl: "/admin",
        jwt: {
          useCookie: false,
          accessTokenKey: "mng-bo-token",
          refreshTokenKey: "mng-bo-rtoken",
        },
      },
    },
    logger: {
      level: configProperties.LogLevel.Debug,
      file: true,
      datePattern: "YYYY-MM-DD-HH",
    },
    server: {
      type: configProperties.ServerType.NONE,
      middleware: {
        compress: true,
        bodyParser: {
          limit: '10mb'
        },
        multipart: {
          enabled: false,
          excludeUrlPatterns: ["/external-if/billkey-register-result*"],
        },
      },
    },
    nuxt: {
      module: {
        useCookie: true,
        useUstraDx: {},
        useUstraMngBo: {},
        useUstraDxMngBo: {
          importSystemPage: true,
          useDefaultScreen: true,
        },
      },
      head: {
        titleTemplate: "Demo Nuxt App",
        title: "",
      },
      generation: {
        generateProfiles: [
          env.Profile.DEV,
          env.Profile.STAGING,
          env.Profile.PRODUCTION,
        ],
      },
    },
  };

  return await NuxtConfigLoader.nuxtConfig(config, (_prop, _config) => {
    _config.env.SERVER_PROP_ENC_KEY = "Z3NjLWNyeXB0by1rZXkxMQ==";

    _config.build.babel.plugins = [
      ["@babel/plugin-proposal-private-property-in-object", { loose: true }],
      ["@babel/plugin-proposal-private-methods", { loose: true }],
    ];
  });
};
