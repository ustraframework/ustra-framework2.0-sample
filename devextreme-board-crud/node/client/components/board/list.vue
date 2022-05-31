<template>
  <div style="width: 100%; height: 100%">
    <dx-data-grid
      ref="grid"
      width="100%"
      height="100%"
      :data-source="gridDataSource"
      :column-auto-width="false"
      :paging="{ pageSize: 10 }"
      :pager="{
        visible: true,
        displayMode: 'full',
        showPageSizeSelector: true,
        showInfo: true,
      }"
      @rowDblClick="
        e => {
          selectedBoard = e.data
          visiblePopup = true
        }
      "
    >
      <dx-column data-field="postId" data-type="number" caption="No" :width="70" sort-order="desc"></dx-column>
      <dx-column data-field="postDvCd" caption="유형" alignment="center" :width="80" :calculate-cell-value="data => getCmnCodeVal('POST_DV_CD', data.postDvCd)"></dx-column>
      <dx-column data-field="title" caption="제목" width="100%"></dx-column>
      <dx-column data-field="autNm" caption="작성자" alignment="center" :width="100"></dx-column>
      <dx-column data-field="regDttm" caption="작성일" alignment="center" :width="130" :calculate-cell-value="data => formatters.datetime(data.regDttm)"></dx-column>
      <dx-column data-field="inqNum" data-type="number" caption="조회수" :width="80"></dx-column>
    </dx-data-grid>
    <form-popup
      :visible.sync="visiblePopup"
      :board="selectedBoard"
      @hidden="() => $refs.grid.instance.clearSelection()"
      @updated="
        () => {
          this.gridDataSource.reload()
          this.visiblePopup = false
        }
      "
    ></form-popup>
  </div>
</template>
<script lang="ts">
import { Component, Ref } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import CustomStore from 'devextreme/data/custom_store'
import DataSource from 'devextreme/data/data_source'
import { createPagingParameter } from '@ustra/nuxt-dx/src/utils/pagination-utils'
import { DxDataGrid } from '@ustra/nuxt-dx/src/components'
import { boardService, Board } from '~/services/board-sevice'
import FormPopup from '~/components/board/form-popup.vue'

@Component({
  components: { FormPopup },
})
export default class extends UstraBoComponent {
  // #region variables
  gridDataSource: DataSource = null
  selectedBoard: Board = null
  visiblePopup: boolean = false

  @Ref() readonly grid: DxDataGrid

  // #endregion
  // #region hooks
  created() {
    this.gridDataSource = new DataSource({
      store: new CustomStore({
        key: ['postId'],
        load: async loadOptions => {
          const paginationRequest = createPagingParameter(loadOptions)
          const result = await boardService.getBoards(paginationRequest.header, null)

          return {
            data: result.body,
            totalCount: result.header.totalRecords,
          }
        },
      }),
    })
  }
  // #endregion
  // #region methods
  async initForm() {
    this.selectedBoard = null
    this.visiblePopup = true

    // clear selection
    this.$ustra.utils.core.getObjectAsync(() => this.grid.instance).then(ins => ins.clearSelection())
  }
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
