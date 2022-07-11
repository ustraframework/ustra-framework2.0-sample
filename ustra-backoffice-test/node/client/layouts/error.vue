<template>
  <div class="error-contianer">
    <div>
      <div v-if="error && error.statusCode === 404">
        <h1 style="text-align: center">{{ pageNotFound }}</h1>
        <dx-scroll-view :height="100"> 요청하신 경로를 찾을 수 없습니다. </dx-scroll-view>
      </div>
      <div v-else>
        <h1 style="text-align: center">{{ otherError }}</h1>

        <dx-scroll-view :height="300">
          {{ errorMessage }}
        </dx-scroll-view>
      </div>

      <div>
        <dx-button icon="home" text="홈 화면으로 이동" @click="$router.push('/')" />
        <dx-button icon="back" text="이전 화면으로 이동" @click="$router.back()" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator'

@Component({
  layout: ctx => {
    return ctx.$ustra.auth.store.authenticated ? 'default' : 'empty'
  },
})
export default class extends Vue {
  @Prop({
    type: Object,
    default: () => {
      return { statusCode: 500 }
    },
  })
  readonly error

  pageNotFound = '404 Not Found'
  otherError = '시스템 오류가 발생하였습니다. 동일한 오류가 발생하는 경우 관리자에게 문의하시기 바랍니다.'

  get errorMessage() {
    return this.error ? this.error.message : ''
  }
}
// export {
//   layout: 'login',
//   props: {
//     error: {
//       type: Object,
//       default: null,
//     },
//   },
//   data() {
//     return {
//       pageNotFound: '404 Not Found',
//       otherError: 'An error occurred',
//       errorMessage: this.error ? this.error.message : '',
//     }
//   },
//   head() {
//     const title = this.error.statusCode === 404 ? this.pageNotFound : this.otherError
//     return {
//       title,
//     }
//   },
// }
</script>

<style lang="scss" scoped>
.error-contianer {
  position: absolute;
  height: 100%;
  width: 100%;
  background-size: cover;
  background-image: url(/img/auth-background.jpg);
  background-position: center;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  color: #fff;

  & > div {
    background-color: rgba(0, 0, 0, 0.25);
    justify-content: center;
    align-items: center;
    flex-direction: column;
    display: flex;
    position: relative;
    overflow: hidden;
    flex: 1;
  }
}

h1 {
  font-size: 20px;
}
</style>
