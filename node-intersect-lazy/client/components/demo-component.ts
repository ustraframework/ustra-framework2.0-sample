import { Component } from 'vue-property-decorator'
import { UstraComponent } from '@ustra/nuxt/src/vue/components/ustra-component'

@Component
export default class extends UstraComponent {
  // #region variables
  // #endregion

  // #region hooks
  created() {
    console.log('this.$nuxt.context.app.config', this.$ustra.config)
  }
  // #endregion
  // #region methods
  // #endregion
  // #region watches
  // #endregion
}
