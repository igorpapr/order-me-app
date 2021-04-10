import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoodsAvailabilityConfigurationComponent } from './goods-availability-configuration.component';

describe('GoodsAvailabilityConfigurationComponent', () => {
  let component: GoodsAvailabilityConfigurationComponent;
  let fixture: ComponentFixture<GoodsAvailabilityConfigurationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GoodsAvailabilityConfigurationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GoodsAvailabilityConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
