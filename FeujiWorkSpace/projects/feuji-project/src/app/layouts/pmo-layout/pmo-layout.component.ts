import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-pmo-layout',
  templateUrl: './pmo-layout.component.html',
  styleUrl: './pmo-layout.component.css'
})
export class PmoLayoutComponent {

  ngOnInit() {
    console.log(this.pmoMenuOptions);
    throw new Error('Method not implemented.');
  }
  pmoMenuOptions = [
    { mainOption: 'User', subOptions: [
      { label: 'PMO Sub 1.1', link: '/PMO/sub1.1' },
      { label: 'PMO Sub 1.2', link: '/PMO/sub1.2' }
    ] },
    { mainOption: 'Account', subOptions: [
      { label: 'PMO Sub 2.1', link: '/PMO/sub2.1' },
      { label: 'PMO Sub 2.2', link: '/PMO/sub2.2' }
    ] },
    { mainOption: 'Projects', subOptions: [
      { label: 'PMO Sub 2.1', link: '/PMO/sub2.1' },
      { label: 'PMO Sub 2.2', link: '/PMO/sub2.2' }
    ] },
    { mainOption: 'Timesheet', subOptions: [
      { label: 'PMO Sub 2.1', link: '/PMO/sub2.1' },
      { label: 'PMO Sub 2.2', link: '/PMO/sub2.2' }
    ] },
    { mainOption: 'Reports', subOptions: [
      { label: 'PMO Sub 2.1', link: '/PMO/sub2.1' },
      { label: 'PMO Sub 2.2', link: '/PMO/sub2.2' }
    ] },
  ];


}
