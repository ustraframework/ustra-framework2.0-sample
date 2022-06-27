<template>
  <div>
    <b-datepicker />
  </div>
</template>
<script lang="ts">
import { Component, Watch } from 'vue-property-decorator'
import DemoComponent from '~/components/demo-component'

@Component
export default class extends DemoComponent {
  // #region variables

  dialog: boolean = false
  // #endregion
  // #region hooks
  created() {}
  // #endregion
  // #region methods

  hide() {}

  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
