import {Pageable} from "./pageable";
import {Sort} from "./sort";

export class Page<Type> {
  content: Type[] | undefined;
  pageable: Pageable | undefined;
  last: boolean | undefined;
  totalPages: number | undefined;
  totalElemets: number | undefined;
  number: number | undefined;
  size: number | undefined;
  sort: Sort | undefined;
  numberOfElements: number | undefined;
  first: boolean | undefined;
  empty: boolean | undefined;
}
