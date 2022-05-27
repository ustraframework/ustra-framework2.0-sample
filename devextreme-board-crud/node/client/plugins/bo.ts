import { Context } from '@nuxt/types'
import { BoMainModule } from '../store/bo-main'
import { $ustra } from '@ustra/nuxt/src/utils/nuxt-utils'

/**
 * BO 플러그인
 */
export class BoPlugin {
  private context: Context

  constructor(context: Context) {
    this.context = context
  }

  public store = {
    get boMain() {
      return $ustra().store.boMain() as BoMainModule
    },
  }
}

export default (context: Context, inject) => {
  inject('bo', new BoPlugin(context))
}
