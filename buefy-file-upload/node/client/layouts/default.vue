<template>
  <div>
    <nuxt />
    <UBLoading />
    <UBDialog />
  </div>
</template>
<script lang="ts">
import { Component } from 'vue-property-decorator'
import { UstraComponent } from '@ustra/nuxt/src/vue/components/ustra-component'
import UBLoading from '@ustra/nuxt-buefy/src/components/u-b-loading.vue'
import UBDialog from '@ustra/nuxt-buefy/src/components/u-b-dialog.vue'

@Component({
  components: { UBLoading, UBDialog },
})
export default class extends UstraComponent {}
</script>
<style lang="scss"></style>
