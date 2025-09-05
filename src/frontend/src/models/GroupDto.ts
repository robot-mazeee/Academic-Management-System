import type PersonDto from "./PersonDto";
import type ProjectDto from "./ProjectDto";

export default class GroupDto {
  id?: number
  project?: ProjectDto
  members?: PersonDto[]

  constructor(obj?: Partial<GroupDto>) {
    Object.assign(this, obj)
  }
}