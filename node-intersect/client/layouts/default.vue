<template>
  <div>
    <nuxt />
  </div>
</template>
<script lang="ts">
import { Component } from "vue-property-decorator";
import { UstraComponent } from "@ustra/nuxt/src/vue/components/ustra-component";

@Component({
  components: {},
})
export default class extends UstraComponent {
  // #region variables
  showDrawer: boolean = false;
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
