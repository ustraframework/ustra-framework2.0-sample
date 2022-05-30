<template>
  <dx-box direction="col">
    <dx-item :ratio="1">
      <template #default>
        <dx-box direction="col">
          <dx-item :ratio="1">
            <template #default>
              <dx-data-grid ref="grid" :data-source="codes" @selection-changed="rowSelected">
                <dx-column data-field="grpCd" caption="코드" />
                <dx-column data-field="cdNm" caption="코드명" />
                <dx-column />
              </dx-data-grid>
            </template>
          </dx-item>
          <dx-item base-size="auto">
            <template #default>
              <div>
                <u-help-box :is-grid-total="false" :show-icon="false">
                  검색 건수 : <span class="emphasis-text">{{ codes.length }}</span> 건
                </u-help-box>
              </div>
            </template>
          </dx-item>
        </dx-box>
      </template>
    </dx-item>
    <dx-item :ratio="0" base-size="auto">
      <template #default>
        <u-button-bar>
          <dx-button text="신규" class="right" icon="plus" @click="newGroupCode"></dx-button>
          <dx-button text="수정" icon="edit" :visible="hasSelectedRow" @click="editGroupCode"></dx-button>

          <dx-popup :width="500" :height="400" :visible.sync="isOpenedInputForm" title="마스터 코드 등록" @shown="inputForm.focus()">
            <dx-box direction="col" height="100%">
              <dx-item :ratio="1">
                <template #default>
                  <input-form ref="inputForm" @saved="onSaved" />
                </template>
              </dx-item>
            </dx-box>
          </dx-popup>
        </u-button-bar>
      </template>
    </dx-item>
  </dx-box>
</template>
<script lang="ts">
import { Component, Ref, Emit } from 'vue-property-decorator'
import { DxDataGrid } from '@ustra/nuxt-dx/src/components'
import { UstraDxBoComponent } from '@ustra/nuxt-dx-mng-bo/src/components/ustra-dx-bo-component'
import InputForm from './input-form.vue'
import { SampleCrudModel, sampleCrudService } from '~/services/sample-crud-service'

@Component({
  components: { InputForm },
})
/**
 * @vuese
 * @group component group
 * component description
 */
export default class extends UstraDxBoComponent {
  // #region variables

  codes: SampleCrudModel[] = []
  isOpenedInputForm: boolean = false
  hasSelectedRow: boolean = false

  @Ref() readonly inputForm: InputForm
  @Ref() readonly grid: DxDataGrid
  // #endregion
  // #region hooks
  created() {
    this.loadCode()
  }

  // #endregion
  // #region methods
  @Emit()
  codeSelected(code: SampleCrudModel) {
    return code
  }

  @Emit()
  codeLoaded() {}

  newGroupCode() {
    this.isOpenedInputForm = true
    this.inputForm.init('groupCode')
  }

  editGroupCode() {
    this.newGroupCode()
    this.inputForm.detail(this.grid.instance.getSelectedRowsData()[0])
  }

  async loadCode() {
    this.codes = await sampleCrudService.getGroups()
    this.codeLoaded()
  }

  rowSelected(e) {
    this.hasSelectedRow = e.selectedRowsData.length > 0

    if (e.selectedRowsData.length < 1) {
      return
    }
    this.codeSelected(e.selectedRowsData[0] as SampleCrudModel)
  }

  onSaved() {
    this.isOpenedInputForm = false
    this.loadCode()
  }

  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
