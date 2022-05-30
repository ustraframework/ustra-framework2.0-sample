<template>
  <div>
    <u-button-bar>
      <u-field label="제목" :width="200" :full-width="true" />

      <dx-button text="검색" icon="search" type="success" class="right" />
      <dx-button text="신규" icon="plus" type="default" />
    </u-button-bar>
  </div>
</template>
<script lang="ts">
import { Component } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'

@Component
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
