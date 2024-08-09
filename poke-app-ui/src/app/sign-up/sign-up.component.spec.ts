import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpComponent } from './sign-up.component';
import { FormControl, FormGroup } from '@angular/forms';
import { UserService } from '../shared/services/user-service.service';

describe('SignUpComponent', () => {
  let component: SignUpComponent;
  let fixture: ComponentFixture<SignUpComponent>;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignUpComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignUpComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  
  it('should render title', () => {
    const fixture = TestBed.createComponent(SignUpComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Sign up');
  });

  it('should return error on sign up if datas are not valid', () => {
    // Given
    component.signUpForm = {} as FormGroup;
    // When
    component.signUp();
    // Then   
  })

  it('should return error on sign up if datas are not valid', () => {
    // Given
    component.signUpForm = {valid: true} as FormGroup;
    const spyOnSignUp = spyOn(userService, 'signUp');
    // When
    component.signUp();
    // Then
    expect(spyOnSignUp).toHaveBeenCalled();
  })



   
  const unvalidEmail = {
    name : "Unvalid email",
    email: "toto[a]gmail.com",
    password: "Garfield2022!",
    confirmPassword: "Garfield2022!"
  }
  const nullEmail = {
    name : "Null email",
    email: null,
    password: "Garfield2022!",
    confirmPassword: "Garfield2022!"
  }
  const unvalidPassword = {
    name : "Unvalid password",
    email: "toto@gmail.com",
    password: "Garfield2022",
    confirmPassword: "Garfield2022"
  }
  const tooLongPassword = {
    name : "Password too long",
    email: "toto@gmail.com",
    password: "Garfield2022!Garfield2022!Garfield2022!",
    confirmPassword: "Garfield2022!Garfield2022!Garfield2022!"
  }
  const notSamePassword = {
    name : "Password and confirmPassword are not equals",
    email: "toto@gmail.com",
    password: "Garfield2022!",
    confirmPassword: "Garfield2022?"
  }
  const nullPassword = {
    name : "Password is null",
    email: "toto@gmail.com",
    password: null,
    confirmPassword: "Garfield2022?"
  }
  const nullConfirmPassword = {
    name : "ConfirmPassword is null",
    email: "toto@gmail.com",
    password: "Garfield2022?",
    confirmPassword: null
  }
  const credentials = [unvalidEmail, nullEmail, unvalidPassword, tooLongPassword, notSamePassword, nullPassword, nullConfirmPassword]
  credentials.forEach((testCase) => {
    it(`should ${testCase.name} not go in sign-up service`, () => {
      // Given
      component.signUpForm.get("email")?.setValue(testCase.email);
      component.signUpForm.get("password")?.setValue(testCase.password);
      component.signUpForm.get("confirmPassword")?.setValue(testCase.confirmPassword);
      const spyOnSignUp = spyOn(userService, 'signUp');
      // When
      component.signUpForm.markAsTouched();
      component.signUp();
      // Then
      expect(spyOnSignUp).toHaveBeenCalledTimes(0);
    })
  })

  it('should go in the sign-up service', () => {
    // Given
    const credentials = {
      email: "toto@gmail.com",
      password: "Garfield2022?",
      confirmPassword: "Garfield2022?"
    }
    component.signUpForm.get("email")?.setValue(credentials.email);
    component.signUpForm.get("password")?.setValue(credentials.password);
    component.signUpForm.get("confirmPassword")?.setValue(credentials.confirmPassword);
    const spyOnSignUp = spyOn(userService, 'signUp');
    // When
    component.signUpForm.markAsTouched();
    component.signUp();
    // Then
    expect(spyOnSignUp).toHaveBeenCalled();
  })
});
