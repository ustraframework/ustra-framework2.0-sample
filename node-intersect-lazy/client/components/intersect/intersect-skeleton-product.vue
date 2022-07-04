<template>
  <!-- 글로벌 디렉티브로 v-intersect 키워드를 사용한다. Option은 object 또는 function 값이며, function 값일 경우는 handler로서 사용된다 -->
  <!-- threshold : observer 콜백이 실행되는 백분율 또는 배열을 설정한다. 50% 노출 시에 콜백이 실행되기 원한다면 0.5로 설정한다. 25% 노출 시마다 콜백이 실행되길 원한다면 [0, 0.25, 0.5, 0.75, 1]등과 같이 설정할 수 있다. 기본 값은 0이며, 이는 1픽셀 이상 노출 시, 콜백이 호출되는 옵션이다. 1로 설정할 경우는 지정 DOM이 모두 노출되었을 경우, 콜백이 설정된다. -->
  <div
    class="card"
    style="width: 400px; height: 400px"
    v-intersect="{ handler: onObserve, options: { threshold: 1 } }"
  >
    <!-- 반복되는 이미지 컨텐츠 영역 -->
    <div class="card-image">
      <!-- 리소스가 없을 경우, 높이가 300px 이미지 영역의 뼈대 생성 -->
      <b-skeleton width="100%" :height="300" />
    </div>

    <!-- 반복되는 텍스트 컨텐츠 영역 -->
    <div class="card-content">
      <!-- 리소스가 없을 경우, 텍스트 영역의 뼈대 생성 -->
      <b-skeleton width="100%" />
    </div>
  </div>
</template>
<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { UstraComponent } from "@ustra/nuxt/src/vue/components/ustra-component";

@Component
export default class extends UstraComponent {
  // #region variables
  @Prop() readonly product: any;

  // isIntersecting 인자 값으로 상태 변경을 처리
  isActivated: boolean = false;

  // #endregion
  // #region hooks
  // #endregion
  // #region methods
  onObserve(
    entries: IntersectionObserverEntry[] = [],
    observer: IntersectionObserver,
    isIntersecting: boolean
  ) {
    if (isIntersecting) {
      this.isActivated = isIntersecting;
    }
  }
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
