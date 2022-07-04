<template>
  <div>
    <div>
      <dx-button text="value 값 업데이트" @click="updateValue"></dx-button>
    </div>
    <Editor v-model="editorValue" />
    <div>{{ editorValue }}</div>
  </div>
</template>
<script lang="ts">
import { Component } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import Editor from '~/components/board/editor.vue'

@Component({
  components: { Editor },
})
export default class extends UstraBoComponent {
  editorValue: string = '<p>초기 값</p>'

  updateValue() {
    this.editorValue = '<p>테스트 값</p>'
  }
}
</script>
<style lang="scss"></style>
