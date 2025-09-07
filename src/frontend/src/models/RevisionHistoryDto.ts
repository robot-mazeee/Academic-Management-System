import type TestDto from "./TestDto";
import type PersonDto from "./PersonDto";

export default class RevisionHistoryDto {
  id?: number
  student?: PersonDto
  test?: TestDto
  status?: string
  dateTime?: string

  constructor(obj?: Partial<RevisionHistoryDto>) {
    Object.assign(this, obj)
  }
}