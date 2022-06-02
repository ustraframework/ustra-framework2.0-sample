<template>
  <div>
    <u-button-bar>
      <u-field label="제목/내용" :label-width="80" :width="150" :full-width="true">
        <dx-text-box v-model="criteria.keyword" />
      </u-field>

      <u-field label="기간" :width="400" :full-width="true">
        <dx-date-period-box v-model="searchDttm" width="100%" mode="date" />
      </u-field>

      <dx-button text="검색" icon="search" type="success" class="right" @click="e => $emit('click-search-button', this.criteria)" />
      <dx-button text="신규" icon="plus" type="default" @click="e => $emit('click-new-button')" />
    </u-button-bar>
  </div>
</template>
<script lang="ts">
import { Component } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import { Criteria } from '~/services/board-sevice'

@Component
export default class extends UstraBoComponent {
  // #region variables
  criteria: Criteria = {
    srtDt: this.$ustra.utils.date.addYears(new Date(), -1),
    endDt: new Date(),
    keyword: null,
  }

  get searchDttm() {
    return [this.criteria.srtDt, this.criteria.endDt]
  }

  set searchDttm(dates) {
    this.criteria.srtDt = dates[0]
    this.criteria.endDt = dates[1]
  }

  // #endregion
  // #region hooks
  // #endregion
  // #region methods
  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
