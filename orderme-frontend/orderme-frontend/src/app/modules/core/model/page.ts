import {Pageable} from "./pageable";
import {Sort} from "./sort";

export class Page<Type> {
  // @ts-ignore
  content: Type[];
  pageable: Pageable | undefined;
  last: boolean | undefined;
  totalPages: number | undefined;
  // @ts-ignore
  totalElements: number;
  number: number | undefined;
  size: number | undefined;
  sort: Sort | undefined;
  numberOfElements: number | undefined;
  first: boolean | undefined;
  empty: boolean | undefined;
}
