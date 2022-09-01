import { FileModule } from '~/store/file-module'

declare module '@ustra/nuxt/src/vue/store/index' {
  interface StoreModules {
    file?: () => FileModule
  }
}
