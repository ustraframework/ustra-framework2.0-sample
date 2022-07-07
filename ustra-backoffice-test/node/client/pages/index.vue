<template>
  <div id="login-box">
    <div class="inner">
      <img src="@ustra/nuxt-dx-mng-bo/src/static/img/gsitm.png" style="margin-bottom: 25px" />
      <div class="top-text">{{ appTitle }}</div>
      <div class="login-form">
        <dx-validation-group ref="validator">
          <dx-text-box ref="idBox" v-model="inputData.userNm" :height="30" placeholder="ID" @initialized="e => e.component.focus()" @enter-key="() => $refs.passBox.instance.focus()">
            <dx-validator>
              <dx-required-rule />
            </dx-validator>
          </dx-text-box>
          <dx-text-box
            ref="passBox"
            v-model="inputData.userPw"
            :height="30"
            placeholder="Password"
            mode="password"
            :input-attr="{
              autocomplete: 'off',
            }"
            @enter-key="() => login()"
          >
            <dx-validator>
              <dx-required-rule />
            </dx-validator>
          </dx-text-box>

          <dx-button text="Login" icon="check" type="default" :height="30" width="100%" @click="login"></dx-button>
        </dx-validation-group>
        <div class="password-link"><a @click="showPasswordEditPopup = true">Change Password</a></div>
      </div>
      <div class="bottom-text">㉿ GSITM 2020-2021</div>
    </div>
    <password-edit-popup :show.sync="showPasswordEditPopup" :usr-id="passwordUsrId" @closed="() => idBox.instance.focus()" />
    <approval-popup ref="approvalPopup" />
  </div>
</template>
<script lang="ts">
import { Component, Ref } from 'vue-property-decorator'
import { DxTextBox, DxValidationGroup, DxValidator } from '@ustra/nuxt-dx/src/components'
import { UserApproval } from '@ustra/nuxt-mng/src/system/models/user-approval-model'
import { ustraUserApprovalService } from '@ustra/nuxt-mng/src/system/services/ustra-user-approval-service'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import { LoginResponseAction } from '@ustra/nuxt-mng-bo/src/services/ustra-bo-login-service'
import { OnError } from '@ustra/nuxt/src/vue/decorators'
// @ts-ignore
import ApprovalPopup, { ApprovalPopupResult } from '@ustra/nuxt-dx-mng-bo/src/components/default/main/common/approval-popup.vue'
import PasswordEditPopup from '@ustra/nuxt-dx-mng-bo/src/components/default/popup/password-edit-popup.vue'

@Component({
  components: { DxValidationGroup, DxValidator, PasswordEditPopup, ApprovalPopup },
  layout: 'empty',
  config: {
    auth: {
      required: false,
    },
  },
})
export default class extends UstraBoComponent {
  // #region variables
  @Ref() readonly idBox: DxTextBox
  @Ref() readonly validator: DxValidationGroup
  @Ref() readonly approvalPopup: ApprovalPopup

  showPasswordEditPopup = false
  passwordUsrId = null

  inputData = {
    userNm: null,
    userPw: null,
  }

  get appTitle() {
    return this.$ustra.env.appProp.nuxt.module.useUstraMngBo.uiConfig.appTitle || 'U.STRA Back Office System'
  }

  // #endregion

  // #region hooks
  async mounted() {
    await this.$nextTick()
    await this.$ustra.bo.auth.logout(false)
    this.$nextTick(() => {
      if (this.idBox?.instance) {
        this.idBox.instance.focus()
      }
    })
  }
  // #endregion

  // #region methods
  @OnError()
  async login() {
    const result = this.validator.instance.validate()

    if (!result.isValid) {
      return
    }

    const loginResult = await this.$ustra.bo.auth.login(this.inputData.userNm, this.inputData.userPw)
    // this.$logger().warn('loginResult', loginResult)
    // 성공
    // @ts-ignore
    if (loginResult.body.actionResponse.action === LoginResponseAction.SUCCESS) {
      await this.$ustra.bo.store.cmData.createNavigation()
      this.$router.push('/admin')
    }
    // 비밀번호 변경 권장
    // @ts-ignore
    else if (loginResult.body.actionResponse.action === LoginResponseAction.SUCCESS_RECOMMEND_CHANGE_PASSWORD) {
      if (await confirm(loginResult.body.actionResponse.message)) {
        this.passwordUsrId = loginResult.body.usrKey
        this.showPasswordEditPopup = true
      } else {
        await this.$ustra.bo.store.cmData.createNavigation()
        this.$router.push('/admin')
      }
    }
    // 비밀번호 변경 (필수)
    // @ts-ignore
    else if (loginResult.body.actionResponse.action === LoginResponseAction.REQUIRE_CHANGE_PASSWORD) {
      await alert(loginResult.body.actionResponse.message)
      this.passwordUsrId = loginResult.body.usrKey
      this.showPasswordEditPopup = true
    }
    // 대화 창 표시
    // @ts-ignore
    else if (loginResult.body.actionResponse.action === LoginResponseAction.SHOW_DIALOG) {
      await alert(loginResult.body.actionResponse.message)
    }
    // 승인 필요
    // @ts-ignore
    else if (loginResult.body.actionResponse.action === LoginResponseAction.FAIL_REQUIRE_APPROVAL) {
      if (await confirm(loginResult.body.actionResponse.message)) {
        await this.requestApproval(this.idBox.value, loginResult.body.actionResponse.approvalType, loginResult.body.actionResponse.approvalName)
      }
    }
  }

  @OnError()
  private async requestApproval(usrId: string, usrApvTyCd: string, title: string) {
    // @ts-ignore
    const result: ApprovalPopupResult = await this.approvalPopup.open(title)

    if (!result.applied) {
      window.toast('승인 요청이 취소 되었습니다.')
      return
    }

    const userApprovalData: UserApproval = {
      reqUsrId: usrId,
      usrApvTyCd,
      apvReqOpnCont: result.text,
      regUsrId: usrId,
      updUsrId: usrId,
    }
    await ustraUserApprovalService.requestApproval(userApprovalData)
    window.toast('승인 요청이 완료되었습니다. 처리가 완료되면 이메일로 통지됩니다.', 5000)
  }
  // #endregion

  // #region watches
  // #endregion
}
</script>
<style lang="scss">
#login-box {
  position: absolute;
  height: 100%;
  width: 100%;
  background-size: cover;
  background-image: url(@ustra/nuxt-dx-mng-bo/src/static/img/main-background.jpg);
  background-position: center;
  display: flex;
  flex-direction: column;
  overflow: hidden;

  & .login-form {
    width: 256px;
    flex-direction: column;
    flex-shrink: 0;
    display: flex;
    overflow: hidden;
    background-color: rgba(18, 61, 64, 0.5);
    padding: 24px;
    justify-content: flex-start;
    align-items: stretch;

    & .dx-validationgroup > * {
      // padding-bottom: 5px;
      margin-bottom: 8px;
    }
  }

  & .bottom-text {
    margin-top: 10px;
    color: #fff;
    font-size: 13px;
  }

  & .top-text {
    color: #fff;
    margin-bottom: 20px;
    font-size: 20px;
  }

  & .password-link {
    text-align: center;
    & > a {
      color: #fff;
      text-decoration: underline;
      cursor: pointer;
      margin: auto;
      background: none;
    }
  }
}

#login-box .inner {
  background-color: rgba(0, 0, 0, 0.25);
  justify-content: center;
  align-items: center;
  flex-direction: column;
  display: flex;
  position: relative;
  overflow: hidden;
  flex: 1;
}
</style>
