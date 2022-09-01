import { ApiResponse } from '@ustra/data/src/models/api-model'
import { HttpMethod } from '@ustra/core/src/server/http/const'
import { UtraService } from '@ustra/nuxt/src/services/ustra-service'

export interface FileMetaData {
  /**
   * 파일 아아디
   */
  fileId: string

  /**
   * 파일 번호
   */
  fileNo: number

  /**
   * 파일 명
   */
  fileName: string

  /**
   * 파일 경로
   */
  filePath: string

  /**
   * 파일 확장자
   */
  fileExt: string

  /**
   * 파일 사이즈
   */
  fileSize: number

  /**
   * 원본 파일명
   */
  orgName: string
}

export interface FileGroupPolicy {
  /**
   * 파일 그룹 아이디
   */
  fileGrpId: string

  /**
   * 파일 그룹 명
   */
  fileGrpNm: string

  /**
   * 최대 사이즈
   */
  maxSz: number

  /**
   * 확장자 제한
   */
  extenLmt: string
}

export class FileService extends UtraService {
  /**
   * 파일 그룹 정책 목록 조회
   */
  async getFileGroupPolicies() {
    return (
      await this.$ustra.api.call<ApiResponse<FileGroupPolicy[]>>({
        url: '/api/file/groups',
        method: HttpMethod.GET,
      })
    )?.data?.body
  }

  /**
   * 파일 목록 조회
   * @param fileGrpId
   * @param fileId
   */
  async getFiles(fileGrpId: string, fileId: string) {
    return (
      await this.$ustra.api.call<ApiResponse<FileMetaData[]>>({
        url: '/api/file',
        method: HttpMethod.GET,
        params: {
          fileGrpId,
          fileId,
        },
      })
    )?.data?.body
  }

  /**
   * 파일 업로드
   * @param formData
   * @returns
   */
  async uploadFile(formData: FormData) {
    return (
      await this.$ustra.api
        .call<ApiResponse<string>>({
          url: '/api/file',
          method: HttpMethod.POST,
          headers: { contentType: false, processData: false, enctype: 'multipart/form-data' },
          data: formData,
          timeout: 6000000,
          passOnResponseError: false,
        })
        .catch(e => {
          alert('파일 업로드 중 오류가 발생하였습니다.')
          throw e
        })
    )?.data?.body
  }

  /**
   * 파일 다운로드
   * @param fileGrpId 파일 그룹 아이디
   * @param fileId 파일 아이디
   * @param fileNo 파일 No
   * @param attachmentFileName 다운로드받을 파일 명
   */
  async downloadFile(fileGrpId: string, fileId: string, fileNo: number, attachmentFileName: string) {
    this.$ustra.api
      .downloadFile({
        url: '/api/file/attach',
        fileName: attachmentFileName,
        method: HttpMethod.GET,
        params: {
          fileGrpId,
          fileId,
          fileNo,
          attachmentFileName,
          attach: 1,
        },
        showLoading: true,
      })
      .catch(e => {
        alert('파일 다운로드 중 오류가 발생하였습니다.')
      })
  }
}

export const fileService = new FileService()
