import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-sidemenu',
  templateUrl: './sidemenu.component.html',
  styleUrl: './sidemenu.component.css'
})
export class SidemenuComponent {

@Input() menuOptions: any[] = [];
  activeMenu: string = '';
  // isArrowRotated1: boolean = false;
  isArrowRotated: { [key: string]: boolean } = {};

  toggleSubmenu(mainOption: string): void {
    this.activeMenu = this.activeMenu === mainOption ? '' : mainOption;
  }

  isActive(mainOption: string): boolean {
    return this.activeMenu === mainOption;
  }

  rotateArrow(mainOption: string): void {
    this.isArrowRotated[mainOption] = !this.isArrowRotated[mainOption];
  }

  public empName: string | null = '';
  public user: any;

  ngOnInit(): void {
    const user = localStorage.getItem("user");
    if (user) {
      this.user = JSON.parse(user);
      this.empName = this.user.userName;
    }
  }
}




