<template>
  <div>
    <u-field-set ref="fieldSet">
      <u-field-row>
        <u-field label="코드">
          <dx-text-box ref="codeInput" v-model="inputData.dtlCd" :disabled="inputMode === 'update'">
            <dx-validator>
              <dx-required-rule />
              <dx-async-rule message="이미 사용 중인 코드입니다." :validation-callback="validateCode" />
            </dx-validator>
          </dx-text-box>
        </u-field>
      </u-field-row>

      <u-field-row>
        <u-field label="코드명">
          <dx-text-box v-model="inputData.cdNm">
            <dx-validator>
              <dx-required-rule />
            </dx-validator>
          </dx-text-box>
        </u-field>
      </u-field-row>

      <u-field-row>
        <u-field label="설명">
          <dx-text-area v-model="inputData.cdDesc"></dx-text-area>
        </u-field>
      </u-field-row>

      <u-field-row>
        <u-field label="사용여부">
          <dx-radio-group v-model="inputData.useYn" :items="useYnList" display-expr="name" value-expr="code"></dx-radio-group>
        </u-field>
      </u-field-row>
    </u-field-set>
    <u-button-bar position="bottom">
      <dx-button text="신규" icon="plus" :disabled="inputMode === 'new'" @click="newForm"></dx-button>

      <dx-button text="저장" class="right" icon="floppy" type="default" @click="save" />
      <dx-button text="삭제" icon="close" :disabled="inputMode === 'new'" @click="remove" />
    </u-button-bar>
  </div>
</template>
<script lang="ts">
import { Component, Ref, Emit } from 'vue-property-decorator'
import { SayclubBoComponent } from '~/components/sayclub-bo-component'
import { Validatable } from '@ustra/nuxt-dx/src/components/dx/dx-validation-group'
import { DxTextBox } from '@ustra/nuxt-dx/src/components/dx/dx-text-box'
import { CodeInfo } from '@ustra/nuxt-mng/src/system/models/ustra-code-model'
import { UseYnDataList } from '@ustra/nuxt-mng/src/data'
import { FormMode } from '@ustra/data/src/models/base-models'
import { OnError } from '@ustra/nuxt/src/vue/decorators'
import { REMOVE_QUESTION } from '@ustra/data/src/data/common-messages'
import { SampleCrudModel, sampleCrudService } from '~/services/sample-crud-service'

type InputType = 'groupCode' | 'detailCode'

@Component
/**
 * @vuese
 * @group component group
 * component description
 */
export default class extends SayclubBoComponent {
  // #region variables
  inputData: SampleCrudModel = null
  inputMode: FormMode = null
  inputType: InputType = null
  groupCode: SampleCrudModel = {
    useYn: 'Y',
  }

  useYnList = UseYnDataList

  @Ref() readonly fieldSet: Validatable
  @Ref() readonly codeInput: DxTextBox
  // #endregion
  // #region hooks
  created() {
    this.init('groupCode')
  }

  // #endregion
  // #region methods
  async init(inputType: InputType = 'detailCode', groupCode?: CodeInfo) {
    if (this.fieldSet) {
      await this.fieldSet.reset()
    }

    this.inputData = {
      grpCd: null,
      dtlCd: null,
      cdNm: null,
      cdDesc: null,
      useYn: 'Y',
    }

    this.inputType = inputType
    this.groupCode = null
    this.inputMode = 'new'

    if (inputType === 'detailCode' && !groupCode) {
      throw new Error('groupCode 값이 없음.')
    }

    this.groupCode = groupCode
    this.focus()
  }

  async detail(code?: CodeInfo) {
    const detail = await sampleCrudService.getCode(code.grpCd, code.dtlCd)

    if (detail === null) {
      throw new Error('코드 정보가 없습니다.')
    }

    if (detail.dtlCd === '*') {
      detail.dtlCd = detail.grpCd
    }

    await this.fieldSet.reset()
    this.inputData = detail
    this.inputMode = 'update'

    this.focus()
  }

  @OnError()
  async save() {
    const saveData = this.$ustra.utils.core.deepMerge({}, this.inputData)

    if (this.inputType === 'groupCode') {
      saveData.grpCd = saveData.dtlCd
      saveData.dtlCd = '*'
    } else {
      saveData.grpCd = this.groupCode.grpCd
    }

    const validateResult = await this.fieldSet.validate(true)
    if (!validateResult || !validateResult.isValid) {
      // alert('입력 값을 확인해주세요.')
      return
    }

    if (this.inputMode === 'new') {
      await sampleCrudService.add(saveData)
    } else {
      await sampleCrudService.edit(saveData)
    }
    this.saved(this.groupCode)
  }

  @OnError()
  async remove() {
    if (await confirm(REMOVE_QUESTION)) {
      await sampleCrudService.remove(this.inputData.grpCd, this.inputData.dtlCd)
      this.saved(this.groupCode)
    }
  }

  newForm() {
    this.init('detailCode', this.groupCode)
    this.reset()
  }

  focus() {
    try {
      this.codeInput.instance.focus()
    } catch (e) {}
  }

  // #region validation
  async validateCode(e) {
    const code = await sampleCrudService.getCode(e.value, '*')
    return code === null
  }
  // #endregion

  // #region event method
  @Emit()
  saved(groupCode: CodeInfo) {
    return groupCode
  }

  @Emit()
  reset() {}
  // #endregion

  // #endregion
  // #region watches
  // #endregion
}
</script>
<style lang="scss"></style>
