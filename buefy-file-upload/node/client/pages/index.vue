<template>
  <div>
    <FileUploader fileGroupId="DEFAULT" :fileId.sync="fileId" />
  </div>
</template>
<script lang="ts">
import { Component } from 'vue-property-decorator'
import { UstraComponent } from '@ustra/nuxt/src/vue/components/ustra-component'
import FileUploader from '~/components/file-uploader.vue'

@Component({
  components: { FileUploader },
})
export default class extends UstraComponent {
  fileId = null
}
</script>