<template>
  <DxHtmlEditor :value="value" height="725px">
    <DxMediaResizing :enabled="true" />
    <DxToolbar :multiline="true">
      <DxItem name="undo" />
      <DxItem name="redo" />
      <DxItem name="separator" />
      <DxItem :accepted-values="sizeValues" name="size" />
      <DxItem :accepted-values="fontValues" name="font" />
      <DxItem name="separator" />
      <DxItem name="bold" />
      <DxItem name="italic" />
      <DxItem name="strike" />
      <DxItem name="underline" />
      <DxItem name="separator" />
      <DxItem name="alignLeft" />
      <DxItem name="alignCenter" />
      <DxItem name="alignRight" />
      <DxItem name="alignJustify" />
      <DxItem name="separator" />
      <DxItem name="orderedList" />
      <DxItem name="bulletList" />
      <DxItem name="separator" />
      <DxItem :accepted-values="headerValues" name="header" />
      <DxItem name="separator" />
      <DxItem name="color" />
      <DxItem name="background" />
      <DxItem name="separator" />
      <DxItem name="link" />
      <DxItem name="image" />
      <DxItem name="separator" />
      <DxItem name="clear" />
      <DxItem name="codeBlock" />
      <DxItem name="blockquote" />
      <DxItem name="separator" />
      <DxItem name="insertTable" />
      <DxItem name="deleteTable" />
      <DxItem name="insertRowAbove" />
      <DxItem name="insertRowBelow" />
      <DxItem name="deleteRow" />
      <DxItem name="insertColumnLeft" />
      <DxItem name="insertColumnRight" />
      <DxItem name="deleteColumn" />
    </DxToolbar>
  </DxHtmlEditor>
</template>
<script lang="ts">
import { Component } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import { DxHtmlEditor, DxToolbar, DxMediaResizing, DxItem } from 'devextreme-vue/html-editor'

@Component({
  components: { DxHtmlEditor, DxToolbar, DxMediaResizing, DxItem },
})
export default class extends UstraBoComponent {
  // #region variables
  value: string = null
  sizeValues = ['8pt', '10pt', '12pt', '14pt', '18pt', '24pt', '36pt']
  fontValues = ['Arial', 'Courier New', 'Georgia', 'Impact', 'Lucida Console', 'Tahoma', 'Times New Roman', 'Verdana']
  headerValues = [false, 1, 2, 3, 4, 5]

  // #endregion
  // #region hooks
  // #endregion
  // #region methods
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss">
.dx-htmleditor-content img {
  vertical-align: middle;
  padding-right: 10px;
}

.dx-htmleditor-content table {
  width: 50%;
}

.options {
  padding: 20px;
  background-color: rgba(191, 191, 191, 0.15);
  margin-top: 20px;
}

.caption {
  font-size: 18px;
  font-weight: 500;
}

.option {
  margin-top: 10px;
}
</style>
