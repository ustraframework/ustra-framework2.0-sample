<template>
  <div class="card" style="width: 400px; height: 400px" v-intersect="{ handler: onObserve, options: { threshold: 1 } }">
    <div class="card-image">
      <figure v-if="isActivated" class="image is-4by3">
        <img :src="product.imgPath" alt="Placeholder image" />
      </figure>
      <b-skeleton v-else width="100%" :height="300" />
    </div>
    <div class="card-content">
      <div v-if="isActivated" class="content">{{ product.name }}</div>
      <b-skeleton v-else width="100%" />
    </div>
  </div>
</template>
<script lang="ts">
import { Component, Prop } from 'vue-property-decorator'
import { UstraComponent } from '@ustra/nuxt/src/vue/components/ustra-component'

@Component
export default class extends UstraComponent {
  // #region variables
  @Prop() readonly product: any

  isActivated: boolean = false
  // #endregion
  // #region hooks
  // #endregion
  // #region methods
  onObserve(entries: IntersectionObserverEntry[] = [], observer: IntersectionObserver, isIntersecting: boolean) {
    if (isIntersecting) {
      this.isActivated = isIntersecting
    }
  }
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
