<template>
  <textarea id="editor1" ref="editorText"></textarea>
</template>
<script lang="ts">
import { Component, Ref, Model, Watch } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import { clearInterval, setInterval } from 'timers'

declare var nhn: any

@Component({
  components: {},
  head: {
    script: [{ type: 'text/javascript', src: '/smarteditor2-2.10.0/js/service/HuskyEZCreator.js', charset: 'utf-8' }],
  },
})
export default class extends UstraBoComponent {
  @Ref() readonly editorText: HTMLTextAreaElement
  @Model('value', { default: null }) readonly value

  editorLoaded: boolean = false
  rawEditorObject: any = null
  rawEditorObjects: any[] = []
  valueSyncTimer: any = null

  mounted() {
    this.createEditor()
  }

  async getRawEditorObject() {
    return await this.$ustra.utils.core.getObjectAsync(() =>
      this.rawEditorObjects && this.rawEditorObjects.length > 0 && this.rawEditorObjects[0].setRawHTMLContents ? this.rawEditorObjects[0] : null,
    )
  }

  async createEditor() {
    if (this.editorLoaded) {
      return
    }

    this.editorLoaded = true

    const nhn = await this.$ustra.utils.core.getObjectAsync(() => window['nhn'])

    nhn.husky.EZCreator.createInIFrame({
      oAppRef: this.rawEditorObjects,
      elPlaceHolder: 'editor1',
      sSkinURI: '/smarteditor2-2.10.0/SmartEditor2Skin.html',
      fCreator: 'createSEditor2',
    })

    this.startValueSyncTimer()
  }

  async startValueSyncTimer() {
    if (this.valueSyncTimer) {
      clearInterval(this.valueSyncTimer)
    }

    const editor = await this.getRawEditorObject()
    setInterval(() => {
      const currentValue = editor.getRawHTMLContents()
      const oldValue = this.value

      if (currentValue !== oldValue) {
        this.$emit('valueChanged', currentValue, oldValue)
        this.$emit('value', editor.getRawHTMLContents())
      }
    }, 1500)
  }

  beforeDestroy() {
    if (this.valueSyncTimer) {
      clearInterval(this.valueSyncTimer)
    }
  }

  @Watch('value', { immediate: true })
  async valueChanged(v) {
    const editor = await this.getRawEditorObject()
    const oldValue = editor.getRawHTMLContents()
    if (v !== oldValue) {
      editor.setRawHTMLContents(!v ? '' : v)
      this.$emit('valueChanged', v, oldValue)
    }
  }
}
</script>