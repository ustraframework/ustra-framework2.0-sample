import { ApiResponse } from '@ustra/data/src/models/api-model'
import { HttpMethod } from '@ustra/core/src/server/http/const'
import { UtraService } from '@ustra/nuxt/src/services/ustra-service'

export class SampleService extends UtraService {
  async getList() {
    const result = await this.$ustra.api.call<ApiResponse<any>>({
      url: '/api/sample/code/list',
      method: HttpMethod.POST,
      data: { sortItem: 'CD_DESC', sortDirection: 'DESC' },
    })

    return result.data.body
  }
}

export const sampleService = new SampleService()
export default sampleService
