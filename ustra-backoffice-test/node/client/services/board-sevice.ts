import { BaseModel } from '@ustra/data/src/models/base-models'
import { ApiResponse } from '@ustra/data/src/models/api-model'
import { PaginationRequest } from '@ustra/data/src/models/pagination-model'
import { UstraService } from '@ustra/nuxt/src/services/ustra-service'
import { HttpMethod } from '@ustra/core/src/server/http/const'

/**
 * 검색 조건
 */
export interface Criteria {
  /**
   * 키워드
   */
  keyword?: string

  /**
   * 검색 시작일
   */
  srtDt?: Date

  /**
   * 검색 종료일
   */
  endDt?: Date
}

/**
 * 게시판
 */
export interface Board extends BaseModel {
  /**
   * 게시 아이디
   */
  postId?: number

  /**
   * 저자 명
   */
  autNm?: string

  /**
   * 제목
   */
  title?: string

  /**
   * 내용
   */
  cont?: string

  /**
   * 게시 구분 코드
   */
  postDvCd?: string

  /**
   * 조회 수
   */
  inqNum?: number

  /**
   * 파일 아이디
   */
  fileId?: string
}

export class SampleCrudService extends UstraService {
  /**
   * 게시 목록 조회
   * @param keyword 조회 키워드
   * @param srtDate 조회 시작일
   * @param endDate 조회 종료일
   * @returns 게시 목록
   */
  async getBoards(pagination: PaginationRequest, keyword?: string, srtDate?: Date, endDate?: Date) {
    return (
      await this.$ustra.api.call<ApiResponse<Board[]>>({
        url: '/api/sample/board',
        method: HttpMethod.GET,
        headers: {
          'rest-api-header': JSON.stringify(pagination),
        },
        params: {
          keyword,
          srtDate,
          endDate,
        },
      })
    )?.data
  }

  /**
   * 게시 상세 조회
   * @param postId 게시 아이디
   * @returns 상세 정보
   */
  async get(postId: number) {
    return (
      await this.$ustra.api.call<ApiResponse<Board>>({
        url: `/api/sample/board/${postId}`,
        method: HttpMethod.GET,
      })
    )?.data?.body
  }

  /**
   * 게시물 추가
   * @param data 입력 데이터
   */
  async add(data: Board) {
    return (
      await this.$ustra.api.call<ApiResponse<Board>>({
        url: '/api/sample/board',
        method: HttpMethod.POST,
        data,
      })
    )?.data?.body
  }

  /**
   * 게시물 수정
   * @param data 수정 데이터
   */
  async edit(data: Board) {
    return (
      await this.$ustra.api.call<ApiResponse<Board>>({
        url: '/api/sample/board',
        method: HttpMethod.PUT,
        data,
      })
    )?.data?.body
  }

  /**
   * 게시물 삭제
   * @param postId 게시 아이디
   */
  async remove(postId: number) {
    await this.$ustra.api.call<ApiResponse<Board>>({
      url: `/api/sample/board/${postId}`,
      method: HttpMethod.DELETE,
    })
  }
}

export const boardService = new SampleCrudService()
export default boardService
