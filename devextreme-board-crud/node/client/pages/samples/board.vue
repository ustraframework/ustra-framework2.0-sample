<template>
  <dx-box direction="col" height="100%" width="100%">
    <dx-item :ratio="0" base-size="auto">
      <template #default>
        <search @click-new-button="$refs.list.initForm()" />
      </template>
    </dx-item>
    <dx-item :ratio="1">
      <template #default>
        <list ref="list" />
      </template>
    </dx-item>
  </dx-box>
</template>
<script lang="ts">
import { Component } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import Search from '~/components/board/search.vue'
import List from '~/components/board/list.vue'

@Component({
  components: { Search, List },
})
export default class extends UstraBoComponent {
  // #region variables
  // #endregion
  // #region hooks
  // #endregion
  // #region methods
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
