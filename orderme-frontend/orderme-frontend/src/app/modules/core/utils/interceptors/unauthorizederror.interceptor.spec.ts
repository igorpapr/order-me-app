import { TestBed } from '@angular/core/testing';

import { UnauthorizederrorInterceptor } from './unauthorizederror.interceptor';

describe('UnauthorizederrorInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      UnauthorizederrorInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: UnauthorizederrorInterceptor = TestBed.inject(UnauthorizederrorInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
