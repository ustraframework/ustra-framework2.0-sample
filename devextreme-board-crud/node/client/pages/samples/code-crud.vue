<template>
  <dx-box direction="row" height="100%" width="100%">
    <template #default>
      <dx-item :ratio="1">
        <template #default>
          <master-grid ref="masterGrid" @code-selected="masterCodeSelected" @code-loaded="onMasterCodeLoaded" />
        </template>
      </dx-item>
      <dx-item :ratio="1" :disabled="subFormDisabled">
        <template #default>
          <dx-box direction="col" width="100%">
            <template #default>
              <dx-item :ratio="1">
                <template #default>
                  <sub-grid ref="subGrid" @code-selected="subCodeSelected" />
                </template>
              </dx-item>
              <dx-item :ratio="0" :base-size="250">
                <template #default>
                  <input-form ref="inputForm" @saved="onSaved" @reset="onReset" />
                </template>
              </dx-item>
            </template>
          </dx-box>
        </template>
      </dx-item>
    </template>
  </dx-box>
</template>
<script lang="ts">
import { Component, Ref } from 'vue-property-decorator'
import { SayclubBoComponent } from '~/components/sayclub-bo-component'
import MasterGrid from '~/components/sample-menu/code-crud/master-grid.vue'
import SubGrid from '~/components/sample-menu/code-crud/sub-grid.vue'
import InputForm from '~/components/sample-menu/code-crud/input-form.vue'
import { SampleCrudModel } from '~/services/sample-crud-service'

@Component({
  components: { MasterGrid, SubGrid, InputForm },
})
export default class extends SayclubBoComponent {
  // #region variables
  @Ref() readonly masterGrid: MasterGrid
  @Ref() readonly subGrid: SubGrid
  @Ref() readonly inputForm: InputForm

  subFormDisabled: boolean = true
  masterCodes: SampleCrudModel[]

  // #endregion
  // #region hooks
  // #endregion
  // #region methods
  async masterCodeSelected(code: SampleCrudModel) {
    await this.subGrid.loadData(code)
    await this.inputForm.init('detailCode', code)
    this.subFormDisabled = false
  }

  subCodeSelected(code: SampleCrudModel) {
    this.inputForm.detail(code)
  }

  onMasterCodeLoaded() {
    this.subGrid.clear()
    this.subFormDisabled = true
  }

  onSaved(code: SampleCrudModel) {
    this.masterCodeSelected(code)
  }

  onReset() {
    this.subGrid.clearSelection()
  }
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
