export default class TestDto {
  id?: number;
  title?: string;
  weight?: number;
  testDate?: string;
  curricularUnitId?: number;

  constructor(obj?: Partial<TestDto>) {
    Object.assign(this, obj)
  }
}