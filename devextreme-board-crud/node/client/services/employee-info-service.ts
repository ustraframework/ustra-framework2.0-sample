import { BaseModel } from '@ustra/data/src/models/base-models'
import { ApiResponse } from '@ustra/data/src/models/api-model'
import { UtraService } from '@ustra/nuxt/src/services/ustra-service'
import { HttpMethod } from '@ustra/core/src/server/http/const'

/**
 * 직원 정보
 */
export interface EmployeeModel extends BaseModel {
  coId?: string
  empId?: string
  empName?: string
  deptId?: string
  deptName?: string
  retireDate?: string
  titleCode?: string
  titleName?: string
  positionCode?: string
  positionName?: string
  gradeCode?: string
  gradeName?: string
  useYn?: string
  capInd?: string
  shiftInd?: string
  email?: string
  officeTelno?: string
  shiftPt?: string
  curInd?: string
  curName?: string
}

/**
 * 검색 조건
 */
export interface EmployeeCriteria extends BaseModel {
  empName?: string
  curInd?: string
}

export class EmployeeInfoService extends UtraService {
  /**
   * 직원 목록 조회
   */
  async getEmployees(criteria: EmployeeCriteria) {
    return (
      await this.$ustra.api.call<ApiResponse<EmployeeModel[]>>({
        url: '/api/employee',
        method: HttpMethod.GET,
        params: criteria,
      })
    ).data.body
  }
}

export const employeeInfoService = new EmployeeInfoService()
export default employeeInfoService
