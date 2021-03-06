import path from 'path'
import { configProperties } from '@ustra/core'
import NuxtConfigLoader from '@ustra/nuxt/src/config/nuxt-config-loader'
import NuxtAppProperties from '@ustra/nuxt/src/config/nuxt-app-properties'
import { NuxtModuleType } from '@ustra/nuxt/src/config/modules/nuxt-modules'

export default async () => {
  const config: NuxtAppProperties = {
    app: {
      processPath: __dirname,
      profile: process.env.CONFIG_ENV,
      configDir: 'config',
      deviceType: configProperties.DeviceType.ALL,
      title: 'Demo Nuxt Buefy App',
      auth: {
        enabled: false
      },
    },
    logger: {
      level: configProperties.LogLevel.Debug,
      file: true,
      datePattern: 'YYYY-MM-DD-HH',
    },
    server: {
      type: configProperties.ServerType.NONE,
      middleware: {
        compress: true,
        bodyParser: true
      },
    },
    nuxt: {
      module: {
        useUstraBuefy: {
        },
        extends: [
          {
            type: NuxtModuleType.MODULE,
            moduleName: path.resolve(__dirname, './module/index'),
            enabled: true,
            scope: 'all',
            option: {
              text: 'test option',
            },
          },
        ],
      },
      head: {
        titleTemplate: 'Demo Nuxt Buefy App',
        title: '',
      },
    },
  }

  return await NuxtConfigLoader.nuxtConfig(config, (_prop, _config) => {
    _config.env.SERVER_PROP_ENC_KEY = 'Z3NjLWNyeXB0by1rZXkxMQ=='
  })
}
