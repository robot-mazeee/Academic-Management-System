import type PersonDto from "./PersonDto";
import type TestDto from "./evaluations/TestDto";

export default class TestGradeDto {
  id?: number
  test?: TestDto
  student?: PersonDto
  grade?: number
  correction?: string

  constructor(obj?: Partial<TestGradeDto>) {
    Object.assign(this, obj)
  }
}