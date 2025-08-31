import type PersonDto from "./PersonDto"

export default class CurricularUnitDto {
  id?: number
  code?: string
  name?: string
  semester?: number
  course?: string
  mainTeacher?: PersonDto

  constructor(obj?: Partial<CurricularUnitDto>) {
    Object.assign(this, obj)
  }
}