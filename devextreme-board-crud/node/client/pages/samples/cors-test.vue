<template>
  <div>
    <dx-button text="302 redirect test" @click="() => goToGroupWare()" />
  </div>
</template>
<script lang="ts">
import { HttpMethod } from '@ustra/core/src/server/http/const'
import { Component } from 'vue-property-decorator'
import { UstraDxBoComponent } from '@ustra/nuxt-dx-mng-bo/src/components/ustra-dx-bo-component'

@Component({
  config: {
    auth: {
      required: false,
    },
  },
})
export default class extends UstraDxBoComponent {
  // #region variables
  // #endregion

  // #region hooks
  // #endregion

  // #region methods
  async goToGroupWare() {
    await this.$ustra.api.call({
      url: '/api/redirect',
      method: HttpMethod.POST,
    })
  }
  // #endregion

  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
