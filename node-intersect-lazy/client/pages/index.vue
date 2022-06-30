<template>
  <div v-scroll.bottom="{ handler: onScrollDown, options: { duration: 1000 } }">
    <div>
      - Intersect Sample Test URL =>
      <a href="/intersect">http://hostname:port/intersect</a>
    </div>
    <div>
      - Intersect-Lazy Sample Test URL =>
      <a href="/lazy">http://hostname:port/lazy</a>
    </div>
    <div>
      - Intersect-skeleton Sample Test URL =>
      <a href="/skeleton">http://hostname:port/skeleton</a>
    </div>
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
