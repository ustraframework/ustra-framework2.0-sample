<template>
  <div>
    <u-button-bar>
      <u-field label="제목/내용" :label-width="80" :width="150" :full-width="true">
        <dx-text-box />
      </u-field>

      <dx-button text="검색" icon="search" type="success" class="right" />
      <dx-button text="신규" icon="plus" type="default" @click="e => $emit('click-new-button')" />
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
