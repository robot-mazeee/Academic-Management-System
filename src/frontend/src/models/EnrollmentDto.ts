import type PersonDto from "./PersonDto"

export default class EnrollmentDto {
  id?: number
  student?: PersonDto
  curricularUnitId?: number
  finalGrade?: number

  constructor(obj?: Partial<EnrollmentDto>) {
    Object.assign(this, obj)
  }
}