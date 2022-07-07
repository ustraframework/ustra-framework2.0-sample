import path from 'path'
import { configProperties, env } from '@ustra/core'
import NuxtConfigLoader from '@ustra/nuxt/src/config/nuxt-config-loader'
import NuxtAppProperties from '@ustra/nuxt/src/config/nuxt-app-properties'

export default async () => {
  const config: NuxtAppProperties = {
    app: {
      processPath: __dirname,
      profile: process.env.CONFIG_ENV,
      configDir: 'config',
      deviceType: configProperties.DeviceType.MOBILE,
      title: 'U.STRA Framework Sample - BO',
      auth: {
        enabled: true,
        loginUrl: '/',
        jwt: {
          useCookie: false,
          accessTokenKey: 'X-BO-TOKEN',
          refreshTokenKey: 'X-BO-REF-TOKEN',
        },
      },
    },
    logger: {
      level: configProperties.LogLevel.Debug,
    },
    server: {
      type: configProperties.ServerType.NONE,
      middleware: {
        compress: true,
        bodyParser: true,
      },
    },
    nuxt: {
      // css: ['~/assets/font/fontagon-icons.sass'],
      module: {
        useCookie: true,
        useUstraDx: {},
        useUstraMngBo: {
          uiConfig: {
            appTitle: 'U.STRA Framework Sample - BO',
            useMaskingForList: true,
            tabMenu: {
              enabled: false,
            },
          },
          useInitialDataCache: true,
        },
        useUstraDxMngBo: {
          importSystemPage: true,
          useDefaultScreen: false,
        },
        extends: [],
      },
      head: {
        titleTemplate: 'U.STRA Framework Sample - BO',
        title: '',
      },
      generation: {
        generateDirPath: path.resolve(__dirname, '../java/src/main/resources/static'),
        generateProfiles: [env.Profile.DEV, env.Profile.STAGING, env.Profile.PRODUCTION],
      },
    },
  }

  return await NuxtConfigLoader.nuxtConfig(config, (_prop, _config) => {
    _config.env.SERVER_PROP_ENC_KEY = 'MTM0eHJhM2Z2aTM5ZTgzNQ=='
    _config.build.extractCSS = false
  })
}
