import { Component } from '@angular/core';
import { UserStorageService } from './auth/services/user-storage.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'HotelWeb';

  isCustomerLoggedIn:boolean=UserStorageService.isCustomerLoggedIn();
  isAdminLoggedIn:boolean=UserStorageService.isAdminLoggedIn();

  constructor(private router:Router){}

  ngOnInit(){
    this.router.events.subscribe(event=>{
      if(event.constructor.name === "NavigationEnd"){
        this.isCustomerLoggedIn=UserStorageService.isCustomerLoggedIn();
        this.isAdminLoggedIn=UserStorageService.isAdminLoggedIn();

      }
    })
  }
  logout(){
    UserStorageService.signOut();
    this.router.navigateByUrl('/')
  }

}
