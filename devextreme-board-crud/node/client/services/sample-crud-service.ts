import { BaseModel } from '@ustra/data/src/models/base-models'
import { ApiResponse } from '@ustra/data/src/models/api-model'
import { PaginationRequest } from '@ustra/data/src/models/pagination-model'
import { UtraService } from '@ustra/nuxt/src/services/ustra-service'
import { HttpMethod } from '@ustra/core/src/server/http/const'

/**
 * crud sample 모델
 */
export interface SampleCrudModel extends BaseModel {
  /**
   * 그룹 코드
   */
  grpCd?: string

  /**
   * 상세 코드
   */
  dtlCd?: string

  /**
   * 코드 명
   */
  cdNm?: string

  /**
   * 코드 상세
   */
  cdDesc?: string

  /**
   * 사용 여부
   */
  useYn?: string

  /**
   * 비고
   */
  rmk?: string
}

export class SampleCrudService extends UtraService {
  /**
   * 코드 그룹 목록 조회
   */
  async getGroups() {
    return (
      await this.$ustra.api.call<ApiResponse<SampleCrudModel[]>>({
        url: '/api/sample/crud-code/groups',
        method: HttpMethod.GET,
      })
    ).data.body
  }

  /**
   * 코드 목록 조회
   * @param pagination 페이징 요청 정보
   * @param grpCd 그룹 코드
   */
  async getCodes(pagination: PaginationRequest, grpCd: string = null) {
    const url = this.$ustra.api
      .urlBuilder('/api/sample/crud-code')
      .add('criteria', {
        paginationRequest: pagination,
        grpCd,
      })
      .build()

    return (
      await this.$ustra.api.call<ApiResponse<SampleCrudModel[]>>({
        url,
        method: HttpMethod.GET,
      })
    ).data
  }

  /**
   * 코드 상세 정보 조회
   * @param grpCd 그룹 코드
   * @param dtlCd 상세 코드
   */
  async getCode(grpCd: string, dtlCd: string) {
    return (
      await this.$ustra.api.call<ApiResponse<SampleCrudModel>>({
        url: `/api/sample/crud-code/${grpCd}/${dtlCd}`,
        method: HttpMethod.GET,
      })
    ).data.body
  }

  /**
   * 코드 추가
   * @param data 입력 데이터
   */
  async add(data: SampleCrudModel) {
    return (
      await this.$ustra.api.call<ApiResponse<SampleCrudModel>>({
        url: '/api/sample/crud-code',
        method: HttpMethod.POST,
        data,
      })
    ).data.body
  }

  /**
   * 코드 수정
   * @param data 수정 데이터
   */
  async edit(data: SampleCrudModel) {
    return (
      await this.$ustra.api.call<ApiResponse<SampleCrudModel>>({
        url: '/api/sample/crud-code',
        method: HttpMethod.PUT,
        data,
      })
    ).data.body
  }

  /**
   * 코드 삭제
   * @param grpCd 그룹 코드
   * @param dtlCd 상세 코드
   */
  async remove(grpCd: string, dtlCd: string) {
    return (
      await this.$ustra.api.call<ApiResponse<SampleCrudModel>>({
        url: '/api/sample/crud-code',
        method: HttpMethod.DELETE,
        data: {
          grpCd,
          dtlCd,
        },
      })
    ).data.body
  }
}

export const sampleCrudService = new SampleCrudService()
export default sampleCrudService
