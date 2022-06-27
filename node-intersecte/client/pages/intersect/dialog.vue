<template>
  <div>
    <b-button @click="showAlert">Alert</b-button>
    <b-button @click="showConfirm">Confirm</b-button>

    <b-button @click="showLoadingBar">LoadingBar</b-button>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop, Model, Ref, Watch, Inject, InjectReactive, Provide, ProvideReactive, PropSync, Emit } from 'vue-property-decorator'
import { UstraComponent } from '@ustra/nuxt/src/vue/components/ustra-component'

@Component
export default class extends UstraComponent {
  // #region variables
  // #endregion
  // #region hooks
  // #endregion

  // #region methods
  async showAlert() {
    // await alert('메시지 테스트')
    await this.$ustra.ui.dialog.alert('메시지 테스트')
    console.log('alert 닫힘.')
  }

  async showConfirm() {
    const result = await confirm('메시지 테스트')
    console.log('confirm 닫힘. 결과 값 : ' + result)
  }

  async showLoadingBar() {
    this.$ustra.ui.progress.show()
    await this.$ustra.utils.core.sleep(3000)
    this.$ustra.ui.progress.hide()
  }

  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
