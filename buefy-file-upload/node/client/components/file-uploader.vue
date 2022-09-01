<template>
  <div style="margin: 50px">
    <b-upload v-model="tempSelectedFiles" :accept="policy ? policy.extenLmt : ''" @input="onFileSelected" multiple>
      <span class="file-cta">
        <b-icon class="file-icon" icon="upload"></b-icon>
        <span class="file-label">Click to upload</span>
      </span>
    </b-upload>
    <b-button type="is-success is-light" @click="upload">Upload</b-button>
    <b-table :data="files">
      <b-table-column field="name" label="name" v-slot="props">
        {{ props.row.name }}
      </b-table-column>

      <b-table-column field="size" label="size" v-slot="props" :width="100">
        {{ props.row.size | fileSizeFormat }}
      </b-table-column>

      <b-table-column field="size" label="" v-slot="props">
        <b-button @click="removeFile(props)" type="is-danger is-light">삭제</b-button>
        <b-button v-if="props.row.no" @click="downloadFile(props.row)" type="is-warning is-light">다운로드</b-button>
      </b-table-column>
    </b-table>
    <div v-if="files.length < 1">등록된 파일이 없습니다.</div>
  </div>
</template>
<script lang="ts">
import { Component, Prop, PropSync, Watch } from 'vue-property-decorator'
import { UstraComponent } from '@ustra/nuxt/src/vue/components/ustra-component'
import { fileService, FileGroupPolicy } from '~/services/file-service'

@Component({
  components: {},
})
export default class extends UstraComponent {
  /**
   * 파일 그룹 아이디
   */
  @Prop({ type: String, required: true }) readonly fileGroupId: string

  /**
   * 파일 아이디(수정 시 사용)
   */
  @PropSync('fileId') syncFileId: string

  get store() {
    return this.$ustra.store.file()
  }

  files = []
  tempSelectedFiles = []
  policy: FileGroupPolicy = null

  created() {
    if (process.server) {
      return
    }

    this.policy = this.store.fileGroupPolices[this.fileGroupId]

    if (!this.policy) {
      throw new Error('파일 그룹 정책을 찾을 수 없습니다.')
    }
  }

  onFileSelected(selectedFiles: File[]) {
    this.tempSelectedFiles = []

    for (const selectedFile of selectedFiles) {
      if (this.policy.maxSz > 0 && this.policy.maxSz > selectedFile.size) {
        alert('파일 사이즈가 너무 큽니다.')
        return
      }

      this.files.push({
        name: selectedFile.name,
        size: selectedFile.size,
        fileObject: selectedFile,
      })
    }
  }

  removeFile(p) {
    this.files.splice(p.index, 1)
  }

  async upload() {
    // craete FormData
    const formData = new FormData()
    formData.append('fileGrpId', this.fileGroupId)

    if (this.syncFileId) {
      formData.append('fileId', this.syncFileId)
    }

    let index = 0
    this.files.forEach(file => {
      formData.append(`file-${++index}`, file.fileObject ?? file.no)
    })

    const fileId = await fileService.uploadFile(formData)
    this.syncFileId = null

    await this.$nextTick()

    this.syncFileId = fileId
  }

  downloadFile(row) {
    fileService.downloadFile(this.fileGroupId, this.syncFileId, row.no, row.name)
  }

  @Watch('fileId', { immediate: true })
  async onfileIdChanged(v) {
    this.files = []
    await this.$nextTick()

    if (v) {
      const files = await fileService.getFiles(this.fileGroupId, v)
      files.forEach(f => {
        this.files.push({
          name: f.orgName,
          size: f.fileSize,
          no: f.fileNo,
        })
      })
    }
  }
}
</script>
<style>
.table-wrapper td {
  vertical-align: middle;
}
</style>