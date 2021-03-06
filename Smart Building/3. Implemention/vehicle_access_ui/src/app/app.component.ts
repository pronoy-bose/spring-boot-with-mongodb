import { Component } from '@angular/core';
import { Router,NavigationEnd} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  navHeaderText = "Vehicle Monitoring";
  constructor(private router: Router) {
    router.events.subscribe((val) => {
      if (val instanceof NavigationEnd) {
        if(val.url == "/analysis"){
          this.navHeaderText="Traffic Analysis"
        }else{
          this.navHeaderText="Vehicle Monitoring"
        }
      }
    });
  }
}
