<template>
  <div id="management-root">
    <div :class="cssClasses">
      <div class="side-nav-outer-toolbar">
        <main-header :title="title" :menu-toggle-enabled="true" @menu-toggle="toggleMenu" />

        <dx-drawer
          class="layout-body"
          position="before"
          template="menu"
          :opened.sync="menuOpened"
          :opened-state-mode="drawerOptions.menuMode"
          :reveal-mode="drawerOptions.menuRevealMode"
          :min-size="drawerOptions.minMenuSize"
          :shading="drawerOptions.shaderEnabled"
          :close-on-outside-click="drawerOptions.closeOnOutsideClick"
        >
          <template #menu>
            <side-menu :compact-mode="!menuOpened" :navigation="navigation" @click="handleSideBarClick"></side-menu>
          </template>
          <div v-show="!isSamplePage && !useTabMenu" class="app-title">
            <!-- <div>
              <i class="mi mi-laptop-mac"></i>
            </div> -->
            <div class="app-name">
              <span>{{ appTitle }}</span>
              <menu-favorite v-if="useFavorite" />
            </div>
          </div>
          <div class="content-box">
            <nuxt v-if="!useTabMenu" />
            <dx-tab-panel
              v-else
              :items="openedTabNavigations"
              :selected-index.sync="selectedMenuTabIndex"
              :show-nav-buttons="true"
              :repaint-changes-only="true"
              :scrolling-enabled="true"
              item-title-template="title"
              item-template="item"
              width="100%"
              height="100%"
              no-data-text=""
            >
              <template #title="{ data: menu, index }">
                <div>
                  <menu-favorite :size="20" :path="menu.path" v-if="useFavorite && index === selectedMenuTabIndex" />
                  <span>{{ menu.text }}</span>
                  <i v-if="menu.closable" class="dx-icon dx-icon-close" @click="closeTab(menu)" />
                </div>
              </template>
              <template #item="{ data: menu }">
                <div style="width: 100%; height: 100%">
                  <component :is="menu.component" />
                </div>
              </template>
            </dx-tab-panel>
          </div>
        </dx-drawer>
      </div>
    </div>
    <u-alert />
    <u-loading />
    <post-popup
      v-for="(item, index) in popupPosts"
      :key="item.postId"
      :show.sync="item.visible"
      :post-grp-id="item.postGrpId"
      :post-id="item.postId"
      @closed="
        () => {
          if (popupPosts.length > index + 1) {
            $nextTick(() => (popupPosts[index + 1].visible = true))
          }
        }
      "
    />
  </div>
</template>

<script lang="ts">
// import '@ustra/nuxt-dx-mng-bo/src/assets/default-screen.scss'
import { Component, Watch } from 'vue-property-decorator'
import { UstraBoComponent } from '@ustra/nuxt-mng-bo/src/components/ustra-bo-component'
import { sizes, subscribe, unsubscribe } from '@ustra/nuxt-dx/src/utils/media-query-utils'
import startsWith from 'lodash/startsWith'
import DxDrawer from 'devextreme-vue/drawer'
import DxTabs from 'devextreme-vue/tabs'
import DxScrollView from 'devextreme-vue/scroll-view'
import ULoading from '@ustra/nuxt-dx/src/components/ui/ustra-loading.vue'

import MainHeader from '@ustra/nuxt-dx-mng-bo/src/components/default/main/main-header.vue'
import MainFooter from '@ustra/nuxt-dx-mng-bo/src/components/default/main/main-footer.vue'
import SideMenu from '@ustra/nuxt-dx-mng-bo/src/components/default/main/side-menu.vue'
import MenuFavorite from '@ustra/nuxt-dx-mng-bo/src/components/default/main/menu-favorite.vue'
import UAlert from '@ustra/nuxt-dx/src/components/ui/ustra-alert.vue'
import PostPopup from '@ustra/nuxt-dx-mng-bo/src/components/default/popup/post-popup.vue'

interface ScreenSize {
  isXSmall: boolean
  isLarge: boolean
  cssClasses: string[]
}

@Component({
  name: 'defaultLayout',
  components: { DxDrawer, MainHeader, MainFooter, SideMenu, UAlert, ULoading, MenuFavorite, DxTabs, DxScrollView, PostPopup },
})
export default class extends UstraBoComponent {
  title: string = null
  screen: ScreenSize = {
    isXSmall: false,
    isLarge: true,
    cssClasses: ['screen-large'],
  }

  menuOpened: boolean = true
  menuTemporaryOpened: boolean = false
  loaded: boolean = false
  isSamplePage: boolean = false
  selectedMenuTabIndex: number = -1

  get appTitle() {
    return this.$ustra.bo.store.cmData.currentProgramMenu?.mnuFullNm
  }

  get cssClasses() {
    return ['app'].concat(!this.screen ? [] : this.screen.cssClasses)
  }

  get drawerOptions() {
    const shaderEnabled = !this.screen.isLarge
    return {
      menuMode: this.screen.isLarge ? 'shrink' : 'overlap',
      menuRevealMode: this.screen.isXSmall ? 'slide' : 'expand',
      minMenuSize: this.screen.isXSmall ? 0 : 60,
      menuOpened: this.screen.isLarge,
      closeOnOutsideClick: shaderEnabled,
      shaderEnabled,
    }
  }

  get headerMenuTogglerEnabled() {
    return this.screen.isXSmall
  }

  get navigation() {
    return this.$ustra.bo.store.cmData.navigation
  }

  get authenticated() {
    return this.$ustra.auth.isAuthenticated()
  }

  get useFavorite() {
    return this.$ustra.bo.store.cmData.appProp.useUserFavoriteMenu
  }

  get useTabMenu() {
    return this.$ustra.env.appProp.nuxt.module.useUstraMngBo.uiConfig.tabMenu.enabled
  }

  get openedTabNavigations() {
    return this.$ustra.bo.store.mainData.openedTabNavigations
  }

  get displayHomeMenu() {
    return this.$ustra.env.appProp.nuxt.module.useUstraMngBo.uiConfig.displayHomeMenu
  }

  get mainPageUrl() {
    return this.$ustra.env.appProp.nuxt.module.useUstraMngBo.uiConfig.mainPageUrl
  }

  get popupPosts() {
    return this.$ustra.bo.store.mainData.popupPosts
  }

  created() {
    this.isSamplePage = startsWith(this.$route.path, '/sample/')
  }

  beforeMount() {
    if (process.client) {
      this.screen = this.getScreenSizeInfo()
      this.menuOpened = this.screen.isLarge
    }
  }

  mounted() {
    // @ts-ignore
    this.loaded = true
    subscribe(this.screenSizeChanged)
  }

  beforeDestroy() {
    unsubscribe(this.screenSizeChanged)
  }

  getScreenSizeInfo() {
    const screenSizes = sizes()

    return {
      isXSmall: screenSizes['screen-x-small'],
      isLarge: screenSizes['screen-large'],
      cssClasses: Object.keys(screenSizes).filter(cl => screenSizes[cl]),
    }
  }

  screenSizeChanged() {
    this.screen = this.getScreenSizeInfo()
  }

  toggleMenu(e) {
    const pointerEvent = e.event
    pointerEvent.stopPropagation()
    if (this.menuOpened) {
      this.menuTemporaryOpened = false
    }
    this.menuOpened = !this.menuOpened
  }

  handleSideBarClick() {
    if (this.menuOpened === false) this.menuTemporaryOpened = true
    this.menuOpened = true
  }

  closeTab(tabData) {
    const index = this.openedTabNavigations.indexOf(tabData)

    this.$ustra.bo.store.mainData.setOpenedTabNavigations(this.openedTabNavigations.filter(e => e !== tabData))
    const nextIndex = Math.max(0, Math.max(this.openedTabNavigations.length - 1, index - 1))
    if (this.openedTabNavigations.length > 0) {
      this.selectedMenuTabIndex = nextIndex
    }
  }

  @Watch('screen.isLarge')
  isLargeChanged() {
    if (!this.menuTemporaryOpened) {
      this.menuOpened = this.screen.isLarge
    }
  }

  @Watch('$route', { immediate: true })
  async routeChanged() {
    if (this.menuTemporaryOpened || !this.screen.isLarge) {
      this.menuOpened = false
      this.menuTemporaryOpened = false
    }

    this.isSamplePage = startsWith(this.$route.path, '/sample')

    // if main page
    if (this.mainPageUrl === this.$route.path) {
      await this.$nextTick()
      if (this.popupPosts && this.popupPosts.length > 0) {
        this.popupPosts[0].visible = true
      }
    }

    // this.scrollView.instance.scrollTo(0)
  }

  @Watch('openedTabNavigations', { deep: true })
  tabNavigationChanged(nav) {
    if (nav.length < 1) {
      this.selectedMenuTabIndex = -1
    } else {
      this.selectedMenuTabIndex = this.openedTabNavigations.findIndex(nav => nav.activeTab)
    }
  }

  @Watch('selectedMenuTabIndex')
  selectedMenuTabIndexChanged(index) {
    if (index < 0) {
      this.$ustra.bo.store.cmData.setCurrentProgramMenu(null)
    } else {
      this.$ustra.bo.store.cmData.setCurrentProgramMenu(this.openedTabNavigations[index].path)
    }
  }
}
</script>
