import type GroupDto from "./GroupDto";
import type ProjectDto from "./ProjectDto";

export default class ProjectSubmissionDto {
  id?: number
  project?: ProjectDto
  group?: GroupDto
  subDateTime?: string
  grade?: number
  submission?: string

  constructor(obj?: Partial<ProjectSubmissionDto>) {
    Object.assign(this, obj)
  }
}