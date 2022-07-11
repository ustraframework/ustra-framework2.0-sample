<template>
  <textarea id="editor1" ref="editorText" style="width: 100%; visibility: hidden"></textarea>
</template>
<script lang="ts">
import { Component, Ref, Model, Watch, Prop } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import { dxPopup } from '@ustra/nuxt-dx/src/components'

@Component({
  head: {
    script: [{ type: 'text/javascript', src: '/smarteditor2-2.10.0/js/service/HuskyEZCreator.js', charset: 'utf-8' }],
  },
})
export default class extends UstraBoComponent {
  @Ref() readonly editorText: HTMLTextAreaElement
  @Prop({ default: 1000 }) readonly syncDuration: number
  @Model('value', { default: null }) readonly value

  editorLoaded: boolean = false
  rawEditorObjects: any[] = []
  valueSyncTimer: any = null

  mounted() {
    this.createEditor()
    this.addEventForDxPopup()
  }

  beforeDestroy() {
    this.clearEditor()
  }

  /**
   * dx popup 컴포넌트 내에 있을 경우 처리
   */
  async addEventForDxPopup() {
    const dxPopupComponents = this.$ustra.utils.component.findParentComponents(this, 'dx-popup')

    if (dxPopupComponents.length > 0) {
      // @ts-ignore
      const dxPopupInstance: dxPopup = await this.$ustra.utils.core.getObjectAsync(() => dxPopupComponents[0].$_instance)

      dxPopupInstance.off('shown')
      // @ts-ignore
      dxPopupInstance.on(
        'shown',
        // @ts-ignore
        this.$ustra.utils.function.wrap(dxPopupComponents[0].$listeners.shown, (next, ...args) => {
          this.createEditor().then(() => next())
        }),
      )

      dxPopupInstance.off('hiding')
      // @ts-ignore
      dxPopupInstance.on(
        'hiding',
        // @ts-ignore
        this.$ustra.utils.function.wrap(dxPopupComponents[0].$listeners.hiding, (next, ...args) => {
          this.clearEditor()
          next()
        }),
      )

      // dxPopupInstance.off('hidden')
      // // @ts-ignore
      // dxPopupInstance.on(
      //   'hidden',
      //   // @ts-ignore
      //   this.$ustra.utils.function.wrap(dxPopupComponents[0].$listeners.hidden, (next, ...args) => {
      //     next()
      //     // this.$emit('value', null)
      //   }),
      // )
    }
  }

  /**
   * 에디터 객체 조회
   */
  async getRawEditorObject() {
    return await this.$ustra.utils.core.getObjectAsync(() =>
      this.rawEditorObjects && this.rawEditorObjects.length > 0 && this.rawEditorObjects[0].setRawHTMLContents ? this.rawEditorObjects[0] : null,
    )
  }

  /**
   * 에디터 생성
   */
  async createEditor() {
    try {
      this.clearEditor()

      this.editorLoaded = true

      const nhn = await this.$ustra.utils.core.getObjectAsync(() => window['nhn'])
      this.rawEditorObjects = []
      nhn.husky.EZCreator.createInIFrame({
        oAppRef: this.rawEditorObjects,
        elPlaceHolder: 'editor1',
        sSkinURI: '/smarteditor2-2.10.0/SmartEditor2Skin.html',
        fCreator: 'createSEditor2',
      })

      await this.startValueSyncTimer()
    } catch (e) {
      this.editorLoaded = false
    }
  }

  /**
   * 에디터 제거
   */
  clearEditor() {
    this.editorLoaded = false
    if (this.valueSyncTimer) {
      clearInterval(this.valueSyncTimer)
    }

    this.rawEditorObjects = []
    this.$el.parentElement.querySelectorAll('iframe').forEach(f => f.remove())
  }

  /**
   * 에디터와 value 동기화를 위한 타이머 구동
   */
  async startValueSyncTimer() {
    if (this.valueSyncTimer) {
      clearInterval(this.valueSyncTimer)
    }
    const editor = await this.getRawEditorObject()
    this.valueSyncTimer = setInterval(async () => {
      if (!this.editorLoaded) {
        return
      }

      const editor = await this.getRawEditorObject()

      const currentValue = editor.getRawHTMLContents()
      const oldValue = this.value

      if (currentValue !== oldValue) {
        this.$emit('valueChanged', currentValue, oldValue)
        this.$emit('value', currentValue)
      }
    }, this.syncDuration)
  }

  /**
   * value 값 변경 시의 처리
   */
  @Watch('value', { immediate: true })
  async valueChanged(v) {
    setTimeout(async () => {
      if (!this.editorLoaded) {
        return
      }
      const editor = await this.getRawEditorObject()
      const oldValue = editor.getRawHTMLContents()
      if (v !== oldValue) {
        editor.setRawHTMLContents(!v ? '' : v)
        this.editorText.value = v
        this.$emit('valueChanged', v, oldValue)
      }
    }, 0)
  }
}
</script>