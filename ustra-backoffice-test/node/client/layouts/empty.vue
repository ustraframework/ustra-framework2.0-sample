<template>
  <div style="width: 100%; height: 100%">
    <nuxt />
    <u-loading />
    <u-alert />
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'

@Component({
  components: { ULoading: () => import('@ustra/nuxt-dx/src/components/ui/ustra-loading.vue'), UAlert: () => import('@ustra/nuxt-dx/src/components/ui/ustra-alert.vue') },
})
export default class extends UstraBoComponent {
  loaded: boolean = false

  async mounted() {
    await this.$nextTick
    this.loaded = true
  }

  beforeCreate() {}
  beforeMount() {}
}
</script>
