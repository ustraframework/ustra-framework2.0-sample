<template>
  <div v-scroll.bottom="{ handler: onScrollDown, options: { duration: 1000 } }">
    <IntersectProduct
      v-for="item in products"
      :key="item.code"
      :product="item"
    />
  </div>
</template>
<script lang="ts">
import { Component } from "vue-property-decorator";
import { UstraComponent } from "@ustra/nuxt/src/vue/components/ustra-component";
import IntersectProduct from "~/components/intersect/intersect-product.vue";

// 상품 목록 데이터 정의 (정적 데이터)
const products = [
  {
    code: 1,
    name: "상품1",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
  {
    code: 2,
    name: "상품2",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
  {
    code: 3,
    name: "상품3",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
  {
    code: 4,
    name: "상품4",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
  {
    code: 5,
    name: "상품5",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
  {
    code: 6,
    name: "상품6",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
  {
    code: 7,
    name: "상품7",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
  {
    code: 8,
    name: "상품8",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
  {
    code: 9,
    name: "상품9",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
  {
    code: 10,
    name: "상품10",
    imgPath:
      "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
  },
];

@Component({
  components: { IntersectProduct },
})
export default class extends UstraComponent {
  // #region variables
  products = products;
  currentPageNo = 1;
  // #endregion
  // #region hooks
  // #endregion
  // #region methods
  // 무한 스크롤 로딩을 위한 메소드
  onScrollDown() {
    if (this.currentPageNo > 5) {
      return;
    }
    this.currentPageNo++;

    const addProducts = [];
    for (let i = 0; i < 10; i++) {
      // 추가할 상품 정보 생성
      addProducts.push({
        code: (this.currentPageNo - 1) * 10 + i + 1,
        name: "상품" + ((this.currentPageNo - 1) * 10 + i + 1),
        imgPath:
          "https://s.pstatic.net/shopping.phinf/20220518_3/ec962f8b-acb9-4c43-811e-e8f21bbd2679.jpg?type=f214_292",
      });
    }

    // 생성된 상품 정보 추가
    this.products.push(...addProducts);
  }
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
