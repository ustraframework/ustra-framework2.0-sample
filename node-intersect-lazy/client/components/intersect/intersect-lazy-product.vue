<template>
  <!-- v-intersect 역할 수행 -->
  <u-lazy :width="400" :height="400" :options="{ threshold: 1 }">
    <!-- 반복되는 컨텐츠 영역 -->
    <div class="card" style="width: 400px; height: 400px">
      <!-- 이미지 컨텐츠 영역 -->
      <div class="card-image">
        <figure class="image is-4by3">
          <img :src="product.imgPath" alt="Placeholder image" />
        </figure>
      </div>
      <!-- 텍스트 컨텐츠 영역 -->
      <div class="card-content">
        <div class="content">{{ product.name }}</div>
      </div>
    </div>
  </u-lazy>
</template>
<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { UstraComponent } from "@ustra/nuxt/src/vue/components/ustra-component";

@Component
export default class extends UstraComponent {
  // #region variables
  @Prop() readonly product: any;

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
