import type PersonDto from "./PersonDto";
import type TestDto from "./TestDto";

export default class TestGradeDto {
  id?: number
  test?: TestDto
  student?: PersonDto
  grade?: number

  constructor(obj?: Partial<TestGradeDto>) {
    Object.assign(this, obj)
  }
}