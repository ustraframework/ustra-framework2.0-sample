import { VuexModule, Module, Mutation, Action } from 'vuex-module-decorators'
import { CombinedContext } from '@ustra/nuxt/src/vue/store'
import { Storage } from '@ustra/nuxt/src/vue/store/decorators'
import { fileService, FileGroupPolicy } from '~/services/file-service'

@Module({
  name: 'file',
  namespaced: true,
  stateFactory: true,
})
export class FileModule extends VuexModule {
  fileGroupPolices: Record<string, FileGroupPolicy> = {}

  @Mutation
  @Storage({ type: 'session', propertyKey: 'fileGroupPolicies', defaultValue: [] })
  setFileGroupPolices(fileGroupPolices: FileGroupPolicy[]) {
    const fileGroupMap = {}
    fileGroupPolices.forEach(p => (fileGroupMap[p.fileGrpId] = p))

    this.fileGroupPolices = fileGroupMap
  }

  @Action
  async nuxtClientInit(ctx: CombinedContext) {
    this.setFileGroupPolices(await fileService.getFileGroupPolicies())
  }
}
