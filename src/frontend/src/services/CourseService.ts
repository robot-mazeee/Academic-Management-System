import type CourseDto from "../models/course/CourseDto"
import httpClient from "./ApiClient"

export default {
  getCourses(): Promise<CourseDto[]> {
    return httpClient.get('/courses')
  },

  createCourse(courseDto: CourseDto): Promise<CourseDto> {
    return httpClient.post(`/courses`, courseDto)
  },

  deleteCourse(courseId: number) {
    return httpClient.delete(`/courses/${courseId}`)
  },

  editCourse(courseDto: CourseDto) {
    return httpClient.put(`/courses/${courseDto.id}`, courseDto)
  }
}