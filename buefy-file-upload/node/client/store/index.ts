import { createStore } from '@ustra/nuxt/src/vue/store'
import { FileModule } from './file-module'

export default () =>
  createStore({
    file: FileModule,
  })
