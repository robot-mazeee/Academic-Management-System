import type TestDto from "./evaluations/TestDto";
import type PersonDto from "./PersonDto";

export default class RevisionDto {
  id?: number
  status?: string
  test?: TestDto
  student?: PersonDto
  reason?: string

  constructor(obj?: Partial<RevisionDto>) {
    Object.assign(this, obj)
  }
}