import { TestBed } from '@angular/core/testing';

import { UserService } from './user-service.service';
import { Component } from '@angular/core';

describe('UserServiceService', () => {
  let service: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should sign up do something', ()=> {
    service.signUp({});
    expect(service.used).toBeTrue();
  })
});
