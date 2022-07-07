<template>
  <div>
    <div>파일 업로드 테스트 페이지 (로컬)</div>
    <u-file-upload-box
      ref="fileUploadBox"
      v-model="fileInfo"
      file-grp-id="test-local-upload"
      upload-type="multi"
      :deletable="true"
      :editable="true"
      :downable="true"
    ></u-file-upload-box>
    <dx-button text="Upload" @click="$refs.fileUploadBox.upload()"></dx-button>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Ref } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import UFileUploadBox from '@ustra/nuxt-dx-mng-bo/src/components/common/ustra-file-upload-box'

@Component
export default class extends UstraBoComponent {
  // #region variables
  fileInfo: any = {}

  @Ref() readonly fileUploadBox: UFileUploadBox
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
