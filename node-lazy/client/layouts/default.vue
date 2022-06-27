<template>
  <div>
    <nuxt />
    <u-b-dialog />
    <u-b-loading />
  </div>
</template>
<script lang="ts">
import { Component } from 'vue-property-decorator'
import { UstraComponent } from '@ustra/nuxt/src/vue/components/ustra-component'
import UBDialog from '@ustra/nuxt-buefy/src/components/u-b-dialog.vue'
import UBLoading from '@ustra/nuxt-buefy/src/components/u-b-loading.vue'

@Component({
  components: { UBDialog, UBLoading },
})
export default class extends UstraComponent {
  // #region variables
  showDrawer: boolean = false
  // #endregion
  // #region hooks
  created() {}

  hide() {}
  // #endregion
  // #region methods
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
