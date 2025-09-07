import httpClient from "./ApiClient";
import type PersonDto from "../models/person/PersonDto";

export default {
  getPeople(): Promise<PersonDto[]> {
    return httpClient.get("/people");
  },

  getTeachers(): Promise<PersonDto[]> {
    return httpClient.get("/teachers");
  },

  getTeachingAssistants(): Promise<PersonDto[]> {
    return httpClient.get("/teaching-assistants");
  },

  getStudents(): Promise<PersonDto[]> {
    return httpClient.get("/students");
  },

  createPerson(person: PersonDto): Promise<PersonDto> {
    return httpClient.post("/people", person);
  },

  deletePerson(person: PersonDto) {
    return httpClient.delete(`/people/${person.id}`);
  },

  editPerson(person: PersonDto) {
    return httpClient.put(`/people/${person.id}`, person);
  },
};
