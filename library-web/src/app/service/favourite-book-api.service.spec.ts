import { TestBed } from '@angular/core/testing';

import { FavouriteBookApiService } from './favourite-book-api.service';

describe('FavouriteBookApiService', () => {
  let service: FavouriteBookApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FavouriteBookApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
