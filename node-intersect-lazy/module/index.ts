import { Module } from '@nuxt/types'
import { logger } from '@ustra/core'

const module: Module = function (_option: any) {
  // const { nuxt } = this

  logger().info({
    message: 'build modules....[@ustra/demo-nuxt-buefy]',
    args: [_option],
  })
}

export default module
