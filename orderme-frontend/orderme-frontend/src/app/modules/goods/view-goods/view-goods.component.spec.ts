import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGoodsComponent } from './view-goods.component';

describe('ViewGoodsComponent', () => {
  let component: ViewGoodsComponent;
  let fixture: ComponentFixture<ViewGoodsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewGoodsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewGoodsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
