export default class ProjectDto {
    id?: number
    title?: string
    weight?: number
    deadline?: string
    curricularUnitId?: number
    maxGroupSize?: number
    projectSheet?: string

  constructor(obj?: Partial<ProjectDto>) {
    Object.assign(this, obj)
  }
}