import { createStore } from '@ustra/nuxt/src/vue/store'
import { BoMainModule } from './bo-main'

export default () => createStore({ boMain: BoMainModule })
