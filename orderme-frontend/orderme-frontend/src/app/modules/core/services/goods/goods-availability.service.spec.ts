import { TestBed } from '@angular/core/testing';

import { GoodsAvailabilityService } from './goods-availability.service';

describe('GoodsAvailabilityService', () => {
  let service: GoodsAvailabilityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GoodsAvailabilityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
