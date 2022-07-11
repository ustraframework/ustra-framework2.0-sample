<template>
  <dx-popup
    :width="800"
    :height="700"
    :visible.sync="isVisible"
    :title="windowTitle"
    v-on="$listeners"
    @shown="
      () => {
        $refs.titleBox.instance.focus()
        this.fieldSet.initValidation()

        $ustra.dom.window.setTimeout(() => (this.board ? this.loadDetail() : this.init()), 0)
      }
    "
  >
    <dx-box direction="col" height="100%">
      <dx-item :ratio="1">
        <template #default>
          <u-field-set ref="fieldSet">
            <u-field-row>
              <u-field label="유형">
                <u-code-select-box v-model="inputData.postDvCd" grp-cd="POST_DV_CD" :required="true" value-expr="dtlCd"></u-code-select-box>
              </u-field>
            </u-field-row>
            <u-field-row>
              <u-field label="제목">
                <dx-text-box ref="titleBox" v-model="inputData.title">
                  <dx-validator>
                    <dx-required-rule />
                  </dx-validator>
                </dx-text-box>
              </u-field>
            </u-field-row>
            <u-field-row>
              <u-field label="작성자">
                <div>{{ inputData.autNm }}</div>
              </u-field>
            </u-field-row>
            <u-field-row>
              <u-field>
                <Editor ref="editor" v-model="inputData.cont" />
                <!-- <dx-text-area v-model="inputData.cont" :height="300">
                  <dx-validator>
                    <dx-required-rule />
                  </dx-validator>
                </dx-text-area> -->
              </u-field>
            </u-field-row>
            <u-field-row>
              <u-field>
                <u-file-upload-box v-model="uploadFileInfo" ref="fileUploader" upload-type="multi" file-grp-id="board-file" :editable="true" :downable="true"></u-file-upload-box>
              </u-field>
            </u-field-row>
          </u-field-set>
        </template>
      </dx-item>
      <dx-item :ratio="0" base-size="auto">
        <template #default>
          <u-button-bar position="bottom">
            <dx-button text="저장" icon="floppy" type="default" class="right" @click="save" />
            <dx-button v-if="!isNewForm" text="삭제" icon="trash" @click="remove" />
          </u-button-bar>
        </template>
      </dx-item>
    </dx-box>
  </dx-popup>
</template>
<script lang="ts">
import { Component, Prop, PropSync, Watch, Ref } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import { UFieldSet } from '@ustra/nuxt-dx/src/components'
import { OnError } from '@ustra/nuxt/src/vue/decorators'
import UFileUploadBox from '@ustra/nuxt-dx-mng-bo/src/components/common/ustra-file-upload-box'
import { boardService, Board } from '~/services/board-sevice'
import Editor from '~/components/board/editor.vue'

@Component({
  components: { Editor },
})
export default class extends UstraBoComponent {
  // #region variables
  @PropSync('visible', { default: false }) isVisible: boolean
  @Prop({ default: null }) readonly board: Board

  @Ref() readonly fieldSet: UFieldSet
  @Ref() readonly fileUploader: UFileUploadBox

  inputData: Board = {}
  isNewForm: boolean = true
  uploadFileInfo: any = {}

  get windowTitle() {
    return this.inputData.title || '새 글 작성'
  }

  // #endregion
  // #region hooks
  // #endregion
  // #region methods
  async init() {
    this.isNewForm = true
    this.inputData = {
      postId: null,
      autNm: this.currentUser.name,
      title: null,
      cont: null,
      postDvCd: this.getCmnCodes('POST_DV_CD').length > 0 ? this.getCmnCodes('POST_DV_CD')[0].dtlCd : null,
      fileId: null,
    }
    this.uploadFileInfo = {}
  }

  @OnError({
    message: '상세 정보 조회 중 오류가 발생하였습니다.',
    onError: function () {
      this.$emit('update:visible', false)
    },
  })
  async loadDetail() {
    const boardInfo = await boardService.get(this.board.postId)

    if (!boardInfo) {
      throw new Error()
    }

    this.inputData = boardInfo

    this.isNewForm = false
    this.uploadFileInfo = {
      fileId: boardInfo.fileId,
    }
  }

  @OnError({ message: '저장 중 오류가 발생하였습니다.' })
  async save() {
    const result = await this.fieldSet.validate(true)

    if (!result.isValid) {
      return
    }
    await this.fileUploader.upload()

    this.inputData.fileId = this.uploadFileInfo?.fileId

    if (this.isNewForm) {
      await boardService.add(this.inputData)
    } else {
      await boardService.edit(this.inputData)
    }
    this.init()
    this.$emit('updated')
  }

  @OnError({ message: '삭제 중 오류가 발생하였습니다.' })
  async remove() {
    if (await confirm('삭제하시겠습니까?')) {
      await boardService.remove(this.board.postId)
      this.$emit('updated')
    }
  }
  // #endregion
  // #region watches
  @Watch('board', { immediate: true })
  boardChanged(v: Board) {
    // if (!v) {
    //   this.init()
    // } else {
    //   this.loadDetail()
    // }
  }
  // #endregion
}
</script>
<style lang="scss"></style>
