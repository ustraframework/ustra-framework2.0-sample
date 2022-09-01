import { configProperties } from '@ustra/core'
import NuxtConfigLoader from '@ustra/nuxt/src/config/nuxt-config-loader'
import NuxtAppProperties from '@ustra/nuxt/src/config/nuxt-app-properties'

export default async () => {
  const config: NuxtAppProperties = {
    app: {
      processPath: __dirname,
      profile: process.env.CONFIG_ENV,
      configDir: 'config',
      deviceType: configProperties.DeviceType.ALL,
      title: 'Demo Nuxt Buefy App',
      auth: {
        enabled: false,
      },
    },
    server: {
      type: configProperties.ServerType.CONNECT,
    },
    nuxt: {
      module: {
        useUstraBuefy: {},
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
