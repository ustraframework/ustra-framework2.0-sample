import { BoPlugin } from '../client/plugins/bo'
import { BoMainModule } from '../client/store/bo-main'

declare module '@ustra/nuxt/src/vue/store/index' {
  interface StoreModules {
    boMain?: () => BoMainModule
  }
}

declare module '@nuxt/types' {
  // context 객체
  interface Context {
    $bo: BoPlugin
  }

  // app 객체
  interface NuxtAppOptions {
    $bo: BoPlugin
  }
}

// store 객체
declare module 'vuex/types/index' {
  interface Store<S> {
    $bo: BoPlugin
  }
}

// vue 객체
declare module 'vue/types/vue' {
  interface Vue {
    $bo: BoPlugin
  }
}
