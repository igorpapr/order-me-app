import {Sort} from "./sort";

export class Pageable {
  sort: Sort | undefined;
  pageNumber: number | undefined;
  pageSize: number | undefined;
  offset: number | undefined;
  unpaged: boolean | undefined;
  paged: boolean | undefined;
}
