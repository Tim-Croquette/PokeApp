import { Directive } from '@angular/core';

@Directive({
  selector: '[appSamePasswordValidator]',
  standalone: true
})
export class SamePasswordValidatorDirective {

  constructor() { }

}
