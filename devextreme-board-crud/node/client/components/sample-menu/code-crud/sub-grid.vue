<template>
  <dx-data-grid ref="grid" height="100%" :data-source="codes" @selection-changed="rowSelected">
    <dx-column data-field="dtlCd" caption="코드" />
    <dx-column data-field="cdNm" caption="코드명" />
  </dx-data-grid>
</template>
<script lang="ts">
import { Component, Ref, Emit } from 'vue-property-decorator'
import { UstraDxBoComponent } from '@ustra/nuxt-dx-mng-bo/src/components/ustra-dx-bo-component'
import { OnError } from '@ustra/nuxt/src/vue/decorators'
import { DxDataGrid } from '@ustra/nuxt-dx/src/components/dx/dx-data-grid'
import { SampleCrudModel, sampleCrudService } from '~/services/sample-crud-service'

@Component
/**
 * @vuese
 * @group component group
 * component description
 */
export default class extends UstraDxBoComponent {
  // #region variables
  codes: SampleCrudModel[] = null
  @Ref() readonly grid: DxDataGrid
  // #endregion
  // #region hooks
  // #endregion
  // #region methods
  @OnError({ message: '코드 목록 조회 중 오류가 발생하였습니다.' })
  async loadData(groupCode: SampleCrudModel) {
    this.codes = (await sampleCrudService.getCodes(null, groupCode.grpCd)).body.filter(code => code.dtlCd !== '*')
  }

  @Emit()
  codeSelected(code: SampleCrudModel) {
    return code
  }

  rowSelected(e) {
    if (e.selectedRowsData.length < 1) {
      return
    }

    this.codeSelected(e.selectedRowsData[0] as SampleCrudModel)
  }

  clearSelection() {
    this.grid.instance.clearSelection()
  }

  clear() {
    this.codes = []
  }
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
